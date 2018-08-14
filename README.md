
# Android Bundle Extras Kotlin Wrapper
[ ![Download](https://api.bintray.com/packages/jskierbi/maven/bundle-helper/images/download.svg) ](https://bintray.com/jskierbi/maven/bundle-helper/_latestVersion)[![Build Status](https://travis-ci.org/jskierbi/bundle-helper.svg?branch=dependencies_update)](https://travis-ci.org/jskierbi/bundle-helper)

Convenient Kotlin extension functions for handling Bundle extras in Activities and arguments in Fragments.

## Features
* Starting activity with convenient map of intent extras
* Creating instance of Fragment with convenient map of arguments
* Lazy initialization of Activity fields with intent extras
* Lazy initialization of Fragment fields with arguments

## Installation (build.gradle)
```gradle
dependencies {
  implementation "com.jskierbi:bundle-helper:0.9.1"
  
  // bundle-helper requires kotln-stdlib
  implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
  ...
}
```

## Usage: Activity

### Lazy initialization of extras
```kotlin
class OtherActivity : Activity() {

  val extraString by lazyExtra<String>(EXTRA_STRING)
  val extraFloat by lazyExtra<Float>(EXTRA_FLOAT)
  val extraBoolean by lazyExtra<Boolean>(EXTRA_BOOLEAN)
  val extraOptional by lazyExtra<String?>(EXTRA_OPTIONAL) // Optional by nullable type
  
  (...)
}
```

### Starting activity
```kotlin
// Somwhere inside Activity or Fragment, starts activity with passed map as extras
startActivity<MyActivity>(
    EXTRA_BOOLEAN to true,
    EXTRA_STRING to "Oh, hello world!",
    EXTRA_FLOAT to 1.12f)
```

## Usage: Fragment

### Lazy initialization of args
```kotlin
class CustomFragment : Fragment() {

  val argString by lazyArg<String>(ARG_STRING)
  val argOptional by lazyArg<String?>(ARG_OPTIONAL)
  
  (...)
}
```

### Creating fragment with args
```kotlin
// Attach arguments to Fragment
CustomFragment().withArgs(
    ARG_STRING to "string argument value",
    ARG_LONG to 1000L
)
```

## Optional extras/args
Nullable types are used to mark optional extras or args:
```kotlin
// Activity
val extraOptional by lazyExtra<String?>(EXTRA_OPTIONAL) // Optional by nullable type
```
```kotlin
// Fragment
val extraOptional by lazyArg<String?>(EXTRA_OPTIONAL) // Optional by nullable type
```

## Complex objects support
To put a copmplex object into bundle, it needs to implement Parcelable interface. It can be done either manually [link](https://developer.android.com/reference/android/os/Parcelable.html) or using library such as [Paper Parcel](https://github.com/grandstaish/paperparcel)

License
--------

    Copyright 2016 Jakub Skierbiszewski.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
