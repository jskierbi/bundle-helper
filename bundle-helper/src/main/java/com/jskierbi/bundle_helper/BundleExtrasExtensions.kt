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

//// ACTIVITY HELPERS /////
/** Start activity with given extras */
inline fun <reified T : Any> Activity.startActivity(vararg extras: Pair<String, Any?> = emptyArray()) {
  val intent = Intent(this, T::class.java)
  bundleFrom(extras)?.apply { intent.putExtras(this) }
  startActivity(intent)
}

/** Lazy initialize with intent extra [key] */
inline fun <reified T : Any?> Activity.lazyExtra(key: String) = lazy {
  intent.extras?.get(key) as T
}


//// FRAGMENT HELPERS /////
/** Start activity from support fragment with given extras */
inline fun <reified T : Any> android.support.v4.app.Fragment.startActivity(vararg extras: Pair<String, Any?> = emptyArray()) {
  activity.startActivity<T>(*extras)
}

/** Start activity from fragment with given extras */
inline fun <reified T : Any> android.app.Fragment.startActivity(vararg extras: Pair<String, Any?> = emptyArray()) {
  activity.startActivity<T>(*extras)
}

/** Create support fragment with given args */
inline fun <reified T : android.support.v4.app.Fragment> createSupportFragment(vararg args: Pair<String, Any?> = emptyArray()): T {
  val instance = try {
    T::class.java.newInstance()
  } catch (e: InstantiationException) {
    throw InstantiationException("Fragment ${T::class.simpleName} does not ")
  }
  bundleFrom(args)?.apply { instance.arguments = this }
  return instance
}

/** Create fragment with given args */
inline fun <reified T : android.app.Fragment> createFragment(vararg args: Pair<String, Any?> = emptyArray()): T {
  val instance = try {
    T::class.java.newInstance()
  } catch (e: InstantiationException) {
    throw InstantiationException("Fragment ${T::class.simpleName} does not ")
  }
  bundleFrom(args)?.apply { instance.arguments = this }
  return instance
}

/** Lazy initialize with intent extra [key] */
inline fun <reified T : Any?> android.support.v4.app.Fragment.lazyArg(key: String) = lazy {
  arguments?.get(key) as T
}

/** Lazy initialize with intent extra [key] */
inline fun <reified T : Any?> android.app.Fragment.lazyArg(key: String) = lazy {
  arguments?.get(key) as T
}

/** Create bundle from [extras] */
fun bundleFrom(extras: Array<out Pair<String, Any?>> = emptyArray()): Bundle? {
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
        null -> null // poor mans null safety
        else -> throw IllegalArgumentException("Cannot put to bundle, unsupported type: ${value.javaClass}")
      }
    }
    return bundle
  }
  return null
}
