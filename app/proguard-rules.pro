# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-ignorewarnings
 -keepattributes Signature
 -keepattributes Exceptions
  # For using GSON @Expose annotation
  -keepattributes *Annotation*
  # Gson specific classes

  -repackageclasses ''
  -allowaccessmodification
  -keep class sun.misc.Unsafe { *; }
  -keep class com.google.gson.** { *; }
  -keep class com.shockwave.**

-obfuscationdictionary method-dictionary.txt
-packageobfuscationdictionary package-dictionary.txt
-classobfuscationdictionary class-dictionary.txt

-adaptresourcefilecontents **.xml
-adaptresourcefilenames **.xsd,**.wsdl,**.xml,**.properties,**.gif,**.jpg,**.png
-keepnames class * implements java.io.Serializable
#-keep class com.tngcl.consumer.model.** { <fields>; }
-keepattributes !LocalVariableTable,!LocalVariableTypeTable
-keepclasseswithmembers,allowobfuscation  class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
-keepnames class androidx.navigation.fragment.NavHostFragment
 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
  ##---------------End: proguard configuration for Gson  ----------

      # Platform calls Class.forName on types which do not exist on Android to determine platform.
      -dontnote retrofit2.Platform
      # Platform used when running on Java 8 VMs. Will not be used at runtime.
      -dontwarn retrofit2.Platform$Java8
      # Retain generic type information for use by reflection by converters and adapters.
      # Retain declared checked exceptions for use by a Proxy instance.