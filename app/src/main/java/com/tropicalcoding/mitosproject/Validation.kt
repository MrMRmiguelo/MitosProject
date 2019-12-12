package com.tropicalcoding.mitosproject

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class Validation (private val context: Context)
{

        //Funcion para verificar si ingreso texto, si no muestra error
        fun isInputEditTextFilled(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {
            val value = textInputEditText.text.toString().trim()
            if (value.isEmpty()) {
                textInputLayout.error = message
                hideKeyboardFrom(textInputEditText)
                return false
            } else {
                textInputLayout.isErrorEnabled = false
            }

            return true
        }



     //funcion para verificar si el correo es correcto
        fun isInputEditTextEmail(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {
            val value = textInputEditText.text.toString().trim()
            if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                textInputLayout.error = message
                hideKeyboardFrom(textInputEditText)
                return false
            } else {
                textInputLayout.isErrorEnabled = false
            }
            return true
        }

   //Funcion por si los textos son iguales
        fun isInputEditTextMatches(textInputEditText1: TextInputEditText, textInputEditText2: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {

            val value1 = textInputEditText1.text.toString().trim()
            val value2 = textInputEditText2.text.toString().trim()
            if (!value1.contentEquals(value2)) {
                textInputLayout.error = message
                hideKeyboardFrom(textInputEditText2)
                return false
            } else {
                textInputLayout.isErrorEnabled = false
            }
            return true
        }

    //Funcion para esconder el teclado
        private fun hideKeyboardFrom(view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }
