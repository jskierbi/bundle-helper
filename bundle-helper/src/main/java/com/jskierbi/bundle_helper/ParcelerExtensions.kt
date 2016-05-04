package com.jskierbi.bundle_helper

import android.os.Parcelable
import org.parceler.Parcels


fun Any.wrapParcel() = Parcels.wrap(this)
fun <T> Parcelable?.unwrap() = Parcels.unwrap<T>(this)
