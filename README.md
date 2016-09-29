# vibrate_when_ringing Android System Setting

- Works previous to Android M (23)

Crashes on Android M (23) with:
```
java.lang.IllegalArgumentException: You cannot change private secure settings
```
- Works on Android N (24)

Follow the issue https://code.google.com/p/android/issues/detail?id=194376

# Version

```
compileSdkVersion 24
buildToolsVersion "24.0.3"
compile 'com.android.support:appcompat-v7:24.2.1'
compile 'com.android.support:design:24.2.1'
```