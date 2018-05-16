package thevoid.iam.imeswitcher

import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.widget.EditText
import android.widget.TextView
import xyz.truenight.utils.Utils

class ImeSwitcher {

    fun onCreate(vararg fields: Field, callback: (() -> Unit)? = null) {
        fields.forEach {
            it.mEditText.setSingleLine()
            it.mEditText.setOnEditorActionListener(onEditorActionListener(fields, callback))
        }
    }

    private fun onEditorActionListener(fields: Array<out Field>, callback: (() -> Unit)?): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { v, actionId, _ ->
            // All fields correct
            if (actionId == IME_ACTION_NEXT && fields.all(Field::valid)) {
                hideKeyboard(v)
                callback?.let { it() }
                return@OnEditorActionListener true
            }

            // Finding the focused field.
            // If it is correct then go to next field, else do nothing
            for (field in fields) {
                if (v.id == field.mEditText.id) {
                    return@OnEditorActionListener field.invalid()
                }
            }

            // If there are invalid fields then go to next field
            false
        }
    }

    abstract class Field(internal var mEditText: EditText) {

        fun valid(): Boolean {
            return check(mEditText.text)
        }

        fun invalid(): Boolean {
            return !check(mEditText.text)
        }

        protected abstract fun check(charSequence: CharSequence): Boolean
    }

    class EmailField(editText: EditText) : Field(editText) {

        override fun check(charSequence: CharSequence): Boolean {
            return isValidEmailAddress(charSequence)
        }
    }

    class SimpleField(editText: EditText) : Field(editText) {

        override fun check(charSequence: CharSequence): Boolean {
            return !Utils.isEmpty(charSequence)
        }
    }
}