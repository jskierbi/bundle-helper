package com.jskierbi.bundle_helper_sample

import android.os.Bundle

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jskierbi.bundle_helper.lazyExtra

/**
 * Created by q on 04/05/16.
 */
class OtherActivity : AppCompatActivity() {

  companion object {
    val EXTRA_STRING = "EXTRA_STRING"
    val EXTRA_BOOLEAN = "EXTRA_BOOLEAN"
    val EXTRA_FLOAT = "EXTRA_FLOAT"
    val EXTRA_COMPLEX_PARAMETER = "EXTRA_COMPLEX_PARAMETER"
    val EXTRA_OPTIONAL = "EXTRA_OPTIONAL"
  }

  val extraString by lazyExtra<String>(EXTRA_STRING)
  val extraFloat by lazyExtra<Float>(EXTRA_FLOAT)
  val extraBoolean by lazyExtra<Boolean>(EXTRA_BOOLEAN)
  val extraComplex by lazyExtra<ComplexObj>(EXTRA_COMPLEX_PARAMETER)

  val extraOptional by lazyExtra<String?>(EXTRA_OPTIONAL) // Optional by nullable type
  val extraComplexOptional by lazyExtra<ComplexObj?>(EXTRA_OPTIONAL) // Complex optional by nullable type

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_other)
    Log.d("OtherActivity", "extraString: $extraString")
    Log.d("OtherActivity", "extraBoolean: $extraBoolean")
    Log.d("OtherActivity", "extraFloat: $extraFloat")
    Log.d("OtherActivity", "extraComplex: ${extraComplex.a}, ${extraComplex.b}")
    Log.d("OtherActivity", "extraOptional: $extraOptional")
    Log.d("OtherActivity", "extraComplexOptional: $extraComplexOptional")
  }
}