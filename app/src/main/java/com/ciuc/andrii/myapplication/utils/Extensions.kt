package com.ciuc.andrii.myapplication.utils

import android.content.Context
import android.view.View
import android.widget.Toast


fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.gone() = run { this.visibility = View.GONE }

fun View.show() = run { this.visibility = View.VISIBLE }

fun View.hide() = run { this.visibility = View.INVISIBLE }

