package ru.kabylin.kforms.validators

import pro.theinvaders.kforms.R
import java.util.regex.Pattern

class EmailValidator : Validator() {
    enum class ErrorCode(val value: Int) {
        BAD_FORMAT(0),
    }

    override fun isValid(value: String): Pair<Boolean, Int> {
        val regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val pattern = Pattern.compile(regex)
        return Pair(pattern.matcher(value).matches(), ErrorCode.BAD_FORMAT.value)
    }

    override fun getErrorStringRes(code: Int): Int {
        return when (code) {
            ErrorCode.BAD_FORMAT.value -> R.string.forms_not_valid_email
            else -> throw UnsupportedOperationException("Unknown error code")
        }
    }
}
