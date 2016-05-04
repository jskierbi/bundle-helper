package com.jskierbi.bundle_helper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById(R.id.btn_start_other)?.setOnClickListener {
      startActivity<OtherActivity>(
          OtherActivity.EXTRA_BOOLEAN_OPTIONAL to true,
          OtherActivity.EXTRA_STRING to "Oh, hello world!",
          OtherActivity.EXTRA_FLOAT to 1.12f,
          OtherActivity.EXTRA_COMPLEX_PARAMETER to ComplexParameter().apply { a = "hello"; b = true }.wrapParcel(),
          "other" to null
      )
    }
  }
}
