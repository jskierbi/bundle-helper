package com.jskierbi.bundle_helper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by q on 04/05/16.
 */
class OtherActivity : AppCompatActivity() {

  companion object {
    val EXTRA_STRING = "EXTRA_STRING"
    val EXTRA_BOOLEAN = "EXTRA_BOOLEAN"
    val EXTRA_FLOAT = "EXTRA_FLOAT"
  }

  val extraString by lazyExtra<String>(EXTRA_STRING)
  val extraBoolean by lazyExtra<Boolean>(EXTRA_BOOLEAN)
  val extraFloat by lazyExtra<Float>(EXTRA_FLOAT)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_other)
    Log.d("OtherActivity", "extraString: $extraString")
    Log.d("OtherActivity", "extraBoolean: $extraBoolean")
    Log.d("OtherActivity", "extraFloat: $extraFloat")
  }
}