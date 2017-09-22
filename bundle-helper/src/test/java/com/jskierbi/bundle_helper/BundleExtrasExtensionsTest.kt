package com.jskierbi.bundle_helper

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.location.Location
import android.os.Build
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

/**
 * Created by jakub on 18.09.17.
 */
@RunWith(RobolectricTestRunner::class)
@Config(
  constants = BuildConfig::class,
  sdk = intArrayOf(
    Build.VERSION_CODES.JELLY_BEAN,
    Build.VERSION_CODES.N
  ))
class BundleExtrasExtensionsTest {

  lateinit var activityCtrl: ActivityController<TestActivity>

  @Test fun ` start activity with extras`() {
    // Assemble
    val str = "asdf"
    val bool = true
    val int = 10233
    val double = 573.33
    val parcelable = Location("").apply { // Location implements parcelable
      latitude = 10.0
      longitude = 3.0
    }
    val intent = Intent(RuntimeEnvironment.application, TestActivity::class.java).withExtras(
      TestActivity.EXTRA_STRING to str,
      TestActivity.EXTRA_BOOLEAN to bool,
      TestActivity.EXTRA_INT to int,
      TestActivity.EXTRA_DOUBLE to double,
      TestActivity.EXTRA_PARCELABLE to parcelable
    )

    // Act
    val activity = Robolectric.buildActivity(TestActivity::class.java, intent).create().visible().get()

    // Assert
    assertEquals(str, activity.stringExtra)
    assertEquals(bool, activity.boolExtra)
    assertEquals(int, activity.intExtra)
    assertEquals(double, activity.doubleExtra, 0.01)
    assertEquals(parcelable.latitude, activity.locationExtra.latitude, 0.01)
    assertEquals(parcelable.longitude, activity.locationExtra.longitude, 0.01)
  }

  @Test fun ` start activity with optional extras - non-null`() {
    // Assemble
    val str = "asdf"
    val parcelable = Location("").apply { // Location implements parcelable
      latitude = 10.0
      longitude = 3.0
    }
    val intent = Intent(RuntimeEnvironment.application, TestActivity::class.java).withExtras(
      TestActivity.EXTRA_OPTIONAL_STRING to str,
      TestActivity.EXTRA_OPTIONAL_PARCELABLE to parcelable
    )

    // Act
    val activity = Robolectric.buildActivity(TestActivity::class.java, intent).create().visible().get()

    // Assert
    assertEquals(str, activity.stringExtraOptional)
    assertNotNull(activity.locationExtraOptional)
    assertEquals(parcelable.latitude, activity.locationExtraOptional?.latitude!! , 0.01)
    assertEquals(parcelable.longitude, activity.locationExtraOptional?.longitude!! , 0.01)
  }

  @Test fun ` start activity with optional extras - null`() {
    // Assemble
    val intent = Intent(RuntimeEnvironment.application, TestActivity::class.java).withExtras(
      TestActivity.EXTRA_OPTIONAL_STRING to null,
      TestActivity.EXTRA_OPTIONAL_PARCELABLE to null
    )

    // Act
    val activity = Robolectric.buildActivity(TestActivity::class.java, intent).create().visible().get()

    // Assert
    assertNull(activity.stringExtraOptional)
    assertNull(activity.locationExtraOptional)
  }

  class TestActivity : Activity() {

    companion object {
      val EXTRA_STRING = "EXTRA_STRING"
      val EXTRA_BOOLEAN = "EXTRA_BOOLEAN"
      val EXTRA_INT = "EXTRA_INT"
      val EXTRA_DOUBLE = "EXTRA_DOUBLE"
      val EXTRA_PARCELABLE = "EXTRA_PARCELABLE"
      val EXTRA_OPTIONAL_STRING = "EXTRA_OPTIONAL_STRING"
      val EXTRA_OPTIONAL_PARCELABLE = "EXTRA_OPTIONAL_PARCELABLE"
    }

    val stringExtra by lazyExtra<String>(EXTRA_STRING)
    val boolExtra by lazyExtra<Boolean>(EXTRA_BOOLEAN)
    val intExtra by lazyExtra<Int>(EXTRA_INT)
    val doubleExtra by lazyExtra<Double>(EXTRA_DOUBLE)
    val locationExtra by lazyExtra<Location>(EXTRA_PARCELABLE)

    val stringExtraOptional by lazyExtra<String?>(EXTRA_OPTIONAL_STRING)
    val locationExtraOptional by lazyExtra<Location?>(EXTRA_OPTIONAL_PARCELABLE)
  }
}