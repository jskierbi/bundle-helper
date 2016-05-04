
# Android Bundle Extras Kotlin Wrapper
[ ![Download](https://api.bintray.com/packages/jskierbi/maven/bundle-helper/images/download.svg) ](https://bintray.com/jskierbi/maven/bundle-helper/_latestVersion)

Convenient Kotlin extension functions for handling Bundle extras in Activities and arguments in Fragments.

## Features
* Starting activity with convenient map of intent extras
* Creating instance of Fragment with convenient map of arguments
* Lazy initialization of Activity fields with intent extras
* Lazy initialization of Fragment fields with arguments

## Installation (build.gradle)
```gradle
dependencies {
  compile "com.jskierbi:bundle-helpers:0.9.0"
  
  // bundle-helper requires kotln-stdlib
  compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
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
class FragmentWithArgs : Fragment() {

  val argString by lazyArg<String>(ARG_STRING)
  val argOptional by lazyArg<String?>(ARG_OPTIONAL)
  
  (...)
}
```

### Creating fragment with args
```kotlin
// Creates fragment with passed map as arguments
createFragment<FragmentWithArgs>(
    ARG_STRING to "string argument value",
    ARG_LONG to 1000L
)

// support.v4 Fragment verion
createSupportFragment<FragmentWithArgs>(
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

## Parecler integration
In its basic form this library supports only simple types, as supported by Android's Bundle class putXXX methods. However, complex objects are supported through [parceler library](https://github.com/johncarl81/parceler). Additional dependency is required:
```gradle
  compile "org.parceler:parceler-api:$parcelerVersion"
  kapt "org.parceler:parceler:$parcelerVersion"
```

### Passing complex objects as extras/args
There is ```wrapParcel()``` extension function, underneath it calls Parcels.wrap()
```kotlin
val complexObj = createSomeComplexObjext()
startActivity<OtherActivity>(
    EXTRA_BOOLEAN to true
    EXTRA_COMPLEX_PARAMETER to complexObj.wrapParcel()
)
```
```kotlin
val complexObj = creatSomeComplexObject()
createSupportFragment<FragmentWithArgs>(
    ARG_STRING to "string argument value",
    ARG_COMPLEX to complexObj.wrapParcel()
)
```

### Lazy initialization complex objects from Parcelable
Both ```lazyExtra()``` and ```lazyArg()``` funcitons optinally accept function, that takes Parcelable as input and returns unwrapped instance. ```.unwrap()``` is elegant way of calling Parcels.unwrap()
```kotlin
// Activity
val extraComplex by lazyExtra<ComplexObj>(EXTRA_COMPLEX_PARAMETER) { it.unwrap() }
```
```kotlin
// Fragment
val extraComplex by lazyArg<ComplexObj>(EXTRA_COMPLEX_PARAMETER) { it.unwrap() }
```

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
