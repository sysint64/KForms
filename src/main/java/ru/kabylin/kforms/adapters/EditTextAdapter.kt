package ru.kabylin.kforms.adapters

import android.widget.EditText

class EditTextAdapter(val editText: EditText): DefaultAbstractAdapter(editText) {
    override fun getValue(): String {
        return editText.text.toString()
    }
}
