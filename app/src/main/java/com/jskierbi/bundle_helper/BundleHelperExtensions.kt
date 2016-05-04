package com.jskierbi.bundle_helper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.util.Size
import android.util.SizeF
import java.io.Serializable

/**
 * Created by q on 04/05/16.
 */
inline fun <reified T : Any> Activity.startActivity(vararg extras: Pair<String, Any> = arrayOf()) {
  val intent = Intent(this, T::class.java)
  if (extras.isNotEmpty()) {
    val bundle = Bundle()
    for ((key, value) in extras) {
      when (value) {
        is IBinder -> bundle.putBinder(key, value)
        is Boolean -> bundle.putBoolean(key, value)
        is Bundle -> bundle.putBundle(key, value)
        is Byte -> bundle.putByte(key, value)
        is Char -> bundle.putChar(key, value)
        is CharSequence -> bundle.putCharSequence(key, value)
        is Double -> bundle.putDouble(key, value)
        is Float -> bundle.putFloat(key, value)
        is Int -> bundle.putInt(key, value)
        is Long -> bundle.putLong(key, value)
        is Parcelable -> bundle.putParcelable(key, value)
        is PersistableBundle -> bundle.putAll(value)
        is Short -> bundle.putShort(key, value)
        is Size -> bundle.putSize(key, value)
        is SizeF -> bundle.putSizeF(key, value)
        is String -> bundle.putString(key, value)
        is Serializable -> {
          Log.w("BundleHelperExtensionss.kt", "Warning: using Serializable for bundling value of class ${value.javaClass}")
          bundle.putSerializable(key, value)
        }
        else -> throw IllegalArgumentException("Cannot put to bundle, unsupported type: ${value.javaClass}")
      }
    }
    intent.putExtras(bundle)
  }
  startActivity(intent)
}

inline fun <reified T : Any> Activity.lazyExtra(key: String) = lazy {
  val extra = intent.extras.get(key)
  extra as T
}


