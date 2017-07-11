package ru.kabylin.kforms.adapters

import android.view.View

open class Adapter(view: View) {
    open fun getValue(): Any {
        throw UnsupportedOperationException("Not implemented")
    }

    open fun setError(errorText: String) {
        throw UnsupportedOperationException("Not implemented")
    }

    open fun clearError() {
        throw UnsupportedOperationException("Not implemented")
    }
}
