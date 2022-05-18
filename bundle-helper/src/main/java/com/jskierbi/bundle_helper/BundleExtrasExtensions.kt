package com.jskierbi.bundle_helper

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.*
import android.util.Log
import android.util.Size
import android.util.SizeF
import java.io.Serializable

/** Lazy initialize with intent extra [key] */
inline fun <reified T : Any?> Activity.lazyExtra(key: String)
  = lazy { intent.extras?.get(key) as T }

/** Lazy initialize with intent extra [key] */
inline fun <reified T : Any?> androidx.fragment.app.Fragment.lazyArg(key: String)
  = lazy { arguments?.get(key) as T }

/** Lazy initialize with intent extra [key] */
inline fun <reified T : Any?> android.app.Fragment.lazyArg(key: String)
  = lazy { arguments?.get(key) as T }

/** Start activity from fragment with given extras */
inline fun <reified T : Any> androidx.fragment.app.Fragment.startActivity(vararg extras: Pair<String, Any?> = emptyArray()) {
  activity?.startActivity<T>(*extras)
}

/** Start activity from fragment with given extras */
inline fun <reified T : Any> android.app.Fragment.startActivity(vararg extras: Pair<String, Any?> = emptyArray()) {
  activity.startActivity<T>(*extras)
}

/** Start activity with given extras */
inline fun <reified T : Any> Activity.startActivity(vararg extras: Pair<String, Any?> = emptyArray()) {
  val intent = Intent(this, T::class.java)
  intent.putExtras(Bundle().putExtras(extras))
  startActivity(intent)
}

/** Put extras inside intent */
fun Intent.withExtras(vararg extras: Pair<String, Any?>): Intent {
  val bundle = Bundle()
  bundle.putExtras(extras)
  putExtras(bundle)
  return this
}

/** Put args inside fragment. Reuses existing fragments arguments if set. */
fun <T : Fragment> T.withArgs(vararg args: Pair<String, Any?> = emptyArray()): T {
  val bundle = arguments ?: Bundle()
  arguments = bundle
  bundle.putExtras(args)
  return this
}

/** Put args inside fragment. Reuses existing fragments arguments if set. */
fun <T: androidx.fragment.app.Fragment> T.withArgs(vararg args: Pair<String, Any?> = emptyArray()): T {
  val bundle = arguments ?: Bundle()
  arguments = bundle
  bundle.putExtras(args)
  return this
}

/** Create bundle from [extras] */
fun Bundle.putExtras(extras: Array<out Pair<String, Any?>> = emptyArray()) = apply {
  for ((key, value) in extras) {
    when {
      value == null -> Unit // poor mans null safety
      value is IBinder -> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
          putBinder(key, value)
        } else {
          throw RuntimeException("IBinder is not supported on api level < 18")
        }
      }
      value is Boolean -> putBoolean(key, value)
      value is Bundle -> putBundle(key, value)
      value is Byte -> putByte(key, value)
      value is Char -> putChar(key, value)
      value is CharSequence -> putCharSequence(key, value)
      value is Double -> putDouble(key, value)
      value is Float -> putFloat(key, value)
      value is Int -> putInt(key, value)
      value is Long -> putLong(key, value)
      value is Parcelable -> putParcelable(key, value)
      value is Short -> putShort(key, value)
      Build.VERSION.SDK_INT >= 21 && value is Size -> putSize(key, value)
      Build.VERSION.SDK_INT >= 21 && value is SizeF -> putSizeF(key, value)
      value is String -> putString(key, value)
      value is Serializable -> {
        Log.w("BundleHelperExtensions", "Warning: using Serializable for bundling value of class ${value.javaClass}")
        putSerializable(key, value)
      }
      value is PersistableBundle -> putAll(value)
      else -> throw IllegalArgumentException("Cannot put to bundle, unsupported type: ${value.javaClass}")
    }
  }
}

/** Create support fragment with given args */
@Deprecated("Please use Fragment#withArgs() func instead")
inline fun <reified T : androidx.fragment.app.Fragment> createSupportFragment(vararg args: Pair<String, Any?> = emptyArray()): T {
  val instance = try {
    T::class.java.newInstance()
  } catch (e: InstantiationException) {
    throw InstantiationException("Fragment ${T::class.simpleName} does not ")
  }
  instance.arguments = Bundle().putExtras(args)
  return instance
}

/** Create fragment with given args */
@Deprecated("Please use Fragment#withArgs() func instead")
inline fun <reified T : android.app.Fragment> createFragment(vararg args: Pair<String, Any?> = emptyArray()): T {
  val instance = try {
    T::class.java.newInstance()
  } catch (e: InstantiationException) {
    throw InstantiationException("Fragment ${T::class.simpleName} does not ")
  }
  instance.arguments = Bundle().putExtras(args)
  return instance
}


