package com.ciuc.andrii.myapplication.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.security.AccessController

//todo View Extensions

fun View.setupUIForHideKeyboard(context: Context) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (this !is EditText) {
                this.setOnTouchListener { _, _ ->
                        if (AccessController.getContext() != null)
                                context.hideKeyboard()
                        false
                }
        }
        //If a layout container, iterate over children and seed recursion.
        if (this is ViewGroup) {
                for (i in 0 until this.childCount) {
                        val innerView = this.getChildAt(i)
                        innerView.setupUIForHideKeyboard(context)
                }
        }
}
fun View.gone() = run { this.visibility = View.GONE }

fun View.show() = run { this.visibility = View.VISIBLE }

fun View.hide() = run { this.visibility = View.INVISIBLE }


//todo Context Extensions
fun Context.hideKeyboard() {
        val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if ((this as Activity).currentFocus != null) {
                try {
                        inputManager.hideSoftInputFromWindow(
                                (this as AppCompatActivity).currentFocus!!.windowToken,
                                0
                        )
                } catch (ex: NullPointerException) {
                        ex.printStackTrace()
                }
        }
}

fun Fragment.toast(message: CharSequence) =
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()

