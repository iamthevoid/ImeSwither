@file:JvmName("ImeUtil")

package thevoid.iam.imeswitcher

import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import xyz.truenight.utils.Utils

fun isValidEmailAddress(email: CharSequence): Boolean {
    if (Utils.isEmpty(email)) return false
    val ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = java.util.regex.Pattern.compile(ePattern)
    val m = p.matcher(email)
    return m.matches()
}

fun email(editText: EditText): ImeSwitcher.Field {
    return ImeSwitcher.EmailField(editText)
}

fun simple(editText: EditText): ImeSwitcher.Field {
    return ImeSwitcher.SimpleField(editText)
}

fun allowEmpty(editText: EditText): ImeSwitcher.Field {
    return ImeSwitcher.AllowEmptyField(editText)
}

fun custom(editText: EditText, validator: (CharSequence) -> Boolean): ImeSwitcher.Field {
    return object : ImeSwitcher.Field(editText) {
        override fun check(charSequence: CharSequence): Boolean {
            return validator(charSequence)
        }
    }
}

fun hideKeyboard(view: View?) {
    val inputManager = view?.
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.isPasswordInput() : Boolean {
    return when {
        InputType.TYPE_NUMBER_VARIATION_PASSWORD and inputType > 0 -> true
        InputType.TYPE_TEXT_VARIATION_PASSWORD and inputType > 0 -> true
        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD and inputType > 0 -> true
        InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD and inputType > 0 -> true
        else -> false
    }
}
