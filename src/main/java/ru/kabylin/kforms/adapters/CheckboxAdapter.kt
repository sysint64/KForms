package ru.kabylin.kforms.adapters

import android.widget.CheckBox

class CheckboxAdapter(val checkBox: CheckBox): DefaultAbstractAdapter(checkBox) {
    override fun getValue(): Boolean {
        return checkBox.isChecked
    }
}
