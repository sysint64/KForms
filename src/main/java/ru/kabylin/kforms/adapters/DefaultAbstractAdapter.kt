package ru.kabylin.kforms.adapters

import android.widget.TextView

abstract class DefaultAbstractAdapter(val textView: TextView): Adapter(textView) {
    override fun setError(errorText: String) {
        textView.error = errorText
    }

    override fun clearError() {
        textView.error = null
    }
}
