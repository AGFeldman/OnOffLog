Keep a log of when Android display turns on/off. I am interested in using this information to deduce my sleeping patterns.

Log is saved on external storage at `documents/com.agfeldman.onofflog/OnOffLog.txt`. I manually export this file to Dropbox.

Tested with Android Studio 2.0, Galaxy S3, and Nexus 5x.

This repo doesn't include any build files (because I'm new to Android and I don't want to version control a ton of automatically generated files that I don't understand). To build with Android Studio, do the following:

- Start a new Android Studio project
    - Application name: OnOffLog
    - Company Domain: agfeldman.com
    - Package name: com.agfeldman.onofflog
    - Project location: YOUR_PATH/OnOffLog
- Target Phone and Tablet with Minimum SDK: API 10
- Select "Basic Activity" (this may have been called "Blank Activity" in earlier versions of Android Studio)
    - Activity Name: MyActivity
    - Layout Name: activity_my
    - Title: MyActivity
    - Menu Resource Name: menu_my
- (Click "Finish" to finish starting a new project with Android Studio)
- Exit Android Studio
- Execute the following (only works with *NIX and GNU cp):
```
cd YOUR_PATH
git clone https://github.com/AGFeldman/OnOffLog.git OnOffLogTEMP
cd OnOffLogTEMP
for item in $(git ls-tree -r master --name-only); do cp --parents $item ../OnOffLog; done
cp -r .git ../OnOffLog
cd ..
rm -rf OnOffLogTEMP
```

This repo includes plenty of code from https://github.com/ViliusKraujutis/AndroidBroadcastsMonitor.
