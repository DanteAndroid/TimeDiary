# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yons/Documents/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interfaces
# class:
#-keepclassmembers class fqcn.of.javascript.interfaces.for.webview {
#   public *;
#}
  -dontwarn com.dante.diary.main.**
  -keep class com.dante.diary.main.MainDiaryFragment { *; }
  -keep class android.support.v7.widget.ShareActionProvider { *; }
  -keep class com.dante.diary.net.** { *; }
  -keep class com.dante.diary.model.** { *; }
  -dontwarn com.roughike.bottombar.**

## bugtags
  -keepattributes LineNumberTable,SourceFile
  -keep class com.bugtags.library.** {*;}
  -dontwarn com.bugtags.library.**
  -keep class io.bugtags.** {*;}
  -dontwarn io.bugtags.**
  -dontwarn org.apache.http.**
  -dontwarn android.net.http.AndroidHttpClient
  # End Bugtags
