# Issue 1138160 test case

Minimal Android project demonstrating this bug: https://bugs.chromium.org/p/chromium/issues/detail?id=1138160

## Getting started

1. Clone the repo and open it in Android Studio
2. Hit "Play" to launch the test app on a connected physical device
3. Click on "Crash test case" button and behold the app crashing, if the device has one of the affected Chrome version.

## The issue

Starting from Chrome for Android version **86.0.4240.75**, this innocent looking piece of code crashes, every time, on any Android version. 

    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.parse(url), "text/html")
    intent.addCategory(Intent.CATEGORY_BROWSABLE)
    startActivity(intent)

The exception: 

    android.content.ActivityNotFoundException: No Activity found to handle Intent
    { act=android.intent.action.VIEW cat=[android.intent.category.BROWSABLE]
    dat=https://www.google.fi/... typ=text/html }
    
Even if you catch that exception instead of letting the app crash, in an Android app that depends on WebViews, 
you'll find that your app gets into a very strange state: existing WebViews stop handling any clicks on any links.

### Workaround

I also found that with slightly altered "open url" code, no exception is thrown.

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addCategory(Intent.CATEGORY_BROWSABLE)
    startActivity(intent)
    
### Affected Chrome / Android System WebView versions

I've tested and seen the described behaviour in these versions:

* 86.0.4240.75
* 86.0.4240.99
