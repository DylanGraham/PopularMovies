# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/dylan/Development/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# Debugger available for fragments (Remove for now)
#-keep public class * extends android.support.v4.** {*;}
#-keep public class * extends android.app.Fragment

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
