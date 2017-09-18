package com.jskierbi.bundle_helper_sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jskierbi.bundle_helper.lazyArg

/**
 * Created by q on 04/05/16.
 */
class FragmentWithArgs : Fragment() {

  companion object {
    val ARG_STRING = "ARG_STRING"
    val ARG_OPTIONAL = "ARG_OPTIONAL"
    val ARG_COMPLEX = "ARG_COMPLEX"
  }

  val argString by  lazyArg<String>(ARG_STRING)
  val argOptional by lazyArg<String?>(ARG_OPTIONAL)
  val argComplex by lazyArg<ComplexObj>(ARG_COMPLEX)

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater?.inflate(R.layout.fragment_with_arg, container, false)
    Log.d("FragmentWithArgs", "argString: $argString}")
    Log.d("FragmentWithArgs", "argOptional: $argOptional}")
    Log.d("FragmentWithArgs", "argComplex: ${argComplex.a}, ${argComplex.b}")
    return view
  }
}