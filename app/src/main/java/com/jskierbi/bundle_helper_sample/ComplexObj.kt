package com.jskierbi.bundle_helper_sample

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

/**
 * Created by jakub on 18.09.17.
 */
@PaperParcel
data class ComplexObj(val a: String,
                      val b: Boolean) : Parcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelComplexObj.CREATOR
  }

  override fun writeToParcel(dest: Parcel, flags: Int) {
    PaperParcelComplexObj.writeToParcel(this, dest, flags)
  }

  override fun describeContents() = 0
}