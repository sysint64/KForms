package ru.kabylin.kforms.validators

import pro.theinvaders.kforms.R

class RequiredValidator : Validator() {
    override fun isValid(value: String): Pair<Boolean, Int> {
        return Pair(value.trim() != "", R.string.forms_field_is_required)
    }

    override fun isValid(value: Boolean): Pair<Boolean, Int> {
        return Pair(value, R.string.forms_field_is_required)
    }
}
