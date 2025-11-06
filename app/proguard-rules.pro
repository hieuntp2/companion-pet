# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to the flags specified
# in /usr/local/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.kts.

# Keep ML Kit dependencies
-keep class com.google.mlkit.** { *; }
-keep interface com.google.mlkit.** { *; }
