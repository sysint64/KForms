package ru.kabylin.kforms.validators

open class Validator {
    open fun isValid(value: String): Pair<Boolean, Int> {
        throw UnsupportedOperationException("Not implemented")
    }

    open fun isValid(value: Boolean): Pair<Boolean, Int> {
        throw UnsupportedOperationException("Not implemented")
    }

    open fun getErrorStringRes(code: Int): Int {
        throw UnsupportedOperationException("Not implemented")
    }
}
