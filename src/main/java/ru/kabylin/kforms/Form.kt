package ru.kabylin.kforms

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import ru.kabylin.kforms.adapters.Adapter
import ru.kabylin.kforms.adapters.CheckboxAdapter
import ru.kabylin.kforms.adapters.EditTextAdapter
import ru.kabylin.kforms.validators.Validator
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance

annotation class Field(
    @IdRes val id: Int,
    val required: Boolean = false,
    val validators: Array<KClass<*>> = emptyArray(),
    val adapter: KClass<*> = Void::class
)

open class Form(val context: Context) {
    init {
        for (member in this::class.members) {
            member.annotations
                .filterIsInstance<Field>()
                .forEach {
                    initFields(it, member as KMutableProperty<View>)
                    clearErrors(it, member)
                }
        }
    }

    fun validate() {
        for (member in this::class.members) {
            member.annotations
                .filterIsInstance<Field>()
                .forEach { handleFieldAnnotation(it, member as KMutableProperty<View>) }
        }
    }

    private fun initFields(annotation: Field, member: KMutableProperty<View>) {
        val activity = context as Activity
        member.setter.call(this, activity.findViewById(annotation.id))
    }

    fun clearErrors(annotation: Field, member: KMutableProperty<View>) {
        val view: View = member.getter.call(this)
        val adapter = getFieldAdapter(annotation, view)
        adapter.clearError()
    }

    private fun fieldIsValid(validator: Validator, adapter: Adapter): Pair<Boolean, Int> {
        val value = adapter.getValue()

        if (value is String) {
            return validator.isValid(value)
        } else if (value is Boolean) {
            return validator.isValid(value)
        } else {
            throw UnsupportedOperationException("Unknown type of value");
        }
    }

    private fun autoDetectAdapter(view: View): Adapter {
        if (view is CheckBox) {
            return CheckboxAdapter(view)
        } else if (view is EditText) {
            return EditTextAdapter(view)
        } else {
            throw UnsupportedOperationException("Can't automatically detect adapter");
        }
    }

    private fun getFieldAdapter(annotation: Field, view: View): Adapter {
        if (annotation.adapter == Void::class)
            return autoDetectAdapter(view)

        val adapterConstructor = annotation.adapter.constructors.first()
        return adapterConstructor.call(view) as Adapter
    }

    private fun handleFieldAnnotation(annotation: Field, member: KMutableProperty<View>) {
        val view: View = member.getter.call(this)

        for (validatorClass in annotation.validators) {
            val validator = validatorClass.createInstance() as Validator
            val adapter = getFieldAdapter(annotation, view)
            val (isValid, errorCode) = fieldIsValid(validator, adapter)

            if (!isValid) {
                val stringResCode = getErrorStringRes(validator, view.id, errorCode)
                adapter.setError(context.getString(stringResCode))
                return
            } else {
                adapter.clearError()
            }
        }
    }

    open fun getErrorStringRes(validator: Validator, @IdRes id: Int, errorCode: Int): Int {
        return validator.getErrorStringRes(errorCode)
    }
}
