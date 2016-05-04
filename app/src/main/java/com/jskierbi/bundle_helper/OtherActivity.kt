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
    val EXTRA_BOOLEAN_OPTIONAL = "EXTRA_BOOLEAN"
    val EXTRA_FLOAT = "EXTRA_FLOAT"
    val EXTRA_COMPLEX_PARAMETER = "EXTRA_COMPLEX_PARAMETER"
  }

  val extraString by lazyExtra<String>(EXTRA_STRING)
  val extraFloat by lazyExtra<Float>(EXTRA_FLOAT)
  val extraBoolean by lazyExtra<Boolean?>(EXTRA_BOOLEAN_OPTIONAL) // Optional by nullable type
  val extraComplex by lazyExtra<ComplexParameter>(EXTRA_COMPLEX_PARAMETER) { it.unwrap() }
  val extraComplexOptional by lazyExtra<ComplexParameter?>(EXTRA_COMPLEX_PARAMETER) { it.unwrap() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_other)
    Log.d("OtherActivity", "extraString: $extraString")
    Log.d("OtherActivity", "extraBoolean: $extraBoolean")
    Log.d("OtherActivity", "extraFloat: $extraFloat")
    Log.d("OtherAcitvity", "extraComplex: ${extraComplex.a}, ${extraComplex.b}")
  }
}