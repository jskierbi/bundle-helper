package com.jskierbi.bundle_helper

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import java.io.Serializable

/**
 * Created by maciek on 18.09.17.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class BundleExtrasExtensionsAndroidTest {

    @get:Rule
    var rule = ActivityTestRule<TestActivity>(
            TestActivity::class.java, true, false)

    @Test
    fun startActivityWithExtras() {

        val str = "asdf"
        val bool = true
        val int = 10233
        val double = 573.33
        val parcelable = Location("").apply {
            // Location implements parcelable
            latitude = 10.0
            longitude = 3.0
        }
        val serializable = SerializableObj()

        Intents.init()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, TestActivity::class.java).withExtras(
                TestActivity.EXTRA_STRING to str,
                TestActivity.EXTRA_BOOLEAN to bool,
                TestActivity.EXTRA_INT to int,
                TestActivity.EXTRA_DOUBLE to double,
                TestActivity.EXTRA_PARCELABLE to parcelable,
                TestActivity.EXTRA_SERIALIZABLE to serializable
        )

        // Act
        val activity = rule.launchActivity(intent)

        // Assert
        Assert.assertEquals(str, activity.stringExtra)
        Assert.assertEquals(bool, activity.boolExtra)
        Assert.assertEquals(int, activity.intExtra)
        Assert.assertEquals(double, activity.doubleExtra, 0.01)
        Assert.assertEquals(parcelable.latitude, activity.locationExtra.latitude, 0.01)
        Assert.assertEquals(parcelable.longitude, activity.locationExtra.longitude, 0.01)
        Assert.assertEquals(serializable, activity.serializableExtra)
        intended(hasComponent(TestActivity::class.java.name))
        Intents.release()


    }

    @Test
    fun startActivityWithOptionalExtrasNonNull() {
        // Assemble
        val str = "asdf"
        val parcelable = Location("").apply {
            // Location implements parcelable
            latitude = 10.0
            longitude = 3.0
        }

        Intents.init()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, TestActivity::class.java).withExtras(
                TestActivity.EXTRA_OPTIONAL_STRING to str,
                TestActivity.EXTRA_OPTIONAL_PARCELABLE to parcelable
        )

        // Act
        val activity = rule.launchActivity(intent)

        // Assert
        Assert.assertEquals(str, activity.stringExtraOptional)
        Assert.assertNotNull(activity.locationExtraOptional)
        Assert.assertEquals(parcelable.latitude, activity.locationExtraOptional?.latitude!!, 0.01)
        Assert.assertEquals(parcelable.longitude, activity.locationExtraOptional?.longitude!!, 0.01)
        intended(hasComponent(TestActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun startActivityWithOptionalExtrasNull() {
        // Assemble
        Intents.init()
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, TestActivity::class.java).withExtras(
                TestActivity.EXTRA_OPTIONAL_STRING to null,
                TestActivity.EXTRA_OPTIONAL_PARCELABLE to null
        )

        // Act
        val activity = rule.launchActivity(intent)

        // Assert
        Assert.assertNull(activity.stringExtraOptional)
        Assert.assertNull(activity.locationExtraOptional)
        intended(hasComponent(TestActivity::class.java.name))
        Intents.release()
    }


}

class TestActivity : Activity() {

    companion object {
        val EXTRA_STRING = "EXTRA_STRING"
        val EXTRA_BOOLEAN = "EXTRA_BOOLEAN"
        val EXTRA_INT = "EXTRA_INT"
        val EXTRA_DOUBLE = "EXTRA_DOUBLE"
        val EXTRA_PARCELABLE = "EXTRA_PARCELABLE"
        val EXTRA_SERIALIZABLE = "EXTRA_SERIALIZABLE"
        val EXTRA_OPTIONAL_STRING = "EXTRA_OPTIONAL_STRING"
        val EXTRA_OPTIONAL_PARCELABLE = "EXTRA_OPTIONAL_PARCELABLE"
    }

    val stringExtra by lazyExtra<String>(EXTRA_STRING)
    val boolExtra by lazyExtra<Boolean>(EXTRA_BOOLEAN)
    val intExtra by lazyExtra<Int>(EXTRA_INT)
    val doubleExtra by lazyExtra<Double>(EXTRA_DOUBLE)
    val locationExtra by lazyExtra<Location>(EXTRA_PARCELABLE)
    val serializableExtra by lazyExtra<SerializableObj>(EXTRA_SERIALIZABLE)

    val stringExtraOptional by lazyExtra<String?>(EXTRA_OPTIONAL_STRING)
    val locationExtraOptional by lazyExtra<Location?>(EXTRA_OPTIONAL_PARCELABLE)
}

data class SerializableObj(val value: Int = 1) : Serializable