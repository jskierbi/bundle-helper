package com.jskierbi.bundle_helper_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jskierbi.bundle_helper.createSupportFragment
import com.jskierbi.bundle_helper.startActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById(R.id.btn_start_other)?.setOnClickListener {
      startActivity<OtherActivity>(
          OtherActivity.EXTRA_BOOLEAN to true,
          OtherActivity.EXTRA_STRING to "Oh, hello world!",
          OtherActivity.EXTRA_FLOAT to 1.12f,
          OtherActivity.EXTRA_COMPLEX_PARAMETER to ComplexObj("complex obj", true),
          "other" to null
      )
    }

    if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
      supportFragmentManager
          .beginTransaction()
          .add(R.id.fragment_container, createSupportFragment<FragmentWithArgs>(
              FragmentWithArgs.ARG_STRING to "Oh, hello there Fragment!",
              FragmentWithArgs.ARG_COMPLEX to ComplexObj("complex obj", false)
          ))
          .commit()
    }
  }
}