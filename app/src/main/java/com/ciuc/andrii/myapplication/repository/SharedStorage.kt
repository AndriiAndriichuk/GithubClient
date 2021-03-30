package com.ciuc.andrii.myapplication.repository

import android.content.Context
import android.content.SharedPreferences
import com.ciuc.andrii.myapplication.utils.SHARED_PREFERENCES_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedStorage(context: Context) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private var gson: Gson? = null

    init {
        gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    var token: String by PreferencesDelegate(this, "")

    @Suppress("UNCHECKED_CAST")
    class PreferencesDelegate<TValue>(
        private val storage: SharedStorage,
        private val defValue: TValue
    ) : ReadWriteProperty<Any?, TValue> {
        private val gson = storage.gson
        private val preferences = storage.sharedPreferences
        private val editor = preferences.edit()

        override fun getValue(thisRef: Any?, property: KProperty<*>): TValue {
            with(preferences) {
                return when (defValue) {
                    is Boolean -> getBoolean(property.name, defValue)
                    is Int -> getInt(property.name, defValue)
                    is Float -> getFloat(property.name, defValue)
                    is Long -> getLong(property.name, defValue)
                    is String -> getString(property.name, defValue)
                    is Serializable -> gson?.fromJson(
                        getString(property.name, ""),
                        defValue.javaClass
                    )
                    else -> throw NotFoundImplementation(defValue)
                } as TValue ?: defValue
            }
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: TValue) {
            with(editor) {
                when (value) {
                    is Boolean -> putBoolean(property.name, value)
                    is Int -> putInt(property.name, value)
                    is Float -> putFloat(property.name, value)
                    is Long -> putLong(property.name, value)
                    is String -> putString(property.name, value)
                    is Serializable -> putString(
                        property.name,
                        gson?.toJson(value, value.javaClass)
                    )
                    else -> throw NotFoundImplementation(value)
                }
                apply()
            }
        }

        class NotFoundImplementation(defValue: Any?) :
            Exception("not found implementation for $defValue")
    }
}