1st run permisson?

/usr/local/android-sdk/emulator/emulator @api18 -no-window -no-audio 2>1 >/dev/null &
export PATH=$PATH:/usr/local/android-sdk/platform-tools/
adb install app/build/outputs/apk/debug/app-debug.apk
adb install app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb shell input keyevent KEYCODE_HOME
adb shell am instrument --no-window-animation -w com.testmaid.test/android.support.test.runner.AndroidJUnitRunner

https://github.com/googlesamples/android-testing/
PKG=com.android.calculator2
adb shell am start $PKG/$(adb shell cmd package resolve-activity -c android.intent.category.LAUNCHER $PKG | sed -n '/name=/s/^.*name=//p')

# Testmaid-android

```
curl -O https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip
unzip sdk-tools-linux-4333796.zip
```

https://stackoverflow.com/questions/46402772/failed-to-install-android-sdk-java-lang-noclassdeffounderror-javax-xml-bind-a

```
Exception in thread "main" java.lang.NoClassDefFoundError: javax/xml/bind/annotation/XmlSchema
export JAVA_OPTS='-XX:+IgnoreUnrecognizedVMOptions --add-modules java.se.ee'
./sdkmanager "platform-tools" "platforms;android-18"
```
say Yes

local.properties
sdk.dir=/mnt/c/Users/kdoubner/android-sdk/

```
./sdkmanager --install "system-images;android-18;google_apis;x86" "emulator"
./avdmanager create avd --force --name avdname -k "system-images;android-18;google_apis;x86"
```
Say No
sudo apt-get install qemu-kvm

./emulator @avdname -no-window

&& export JAVA_OPTS='-XX:+IgnoreUnrecognizedVMOptions --add-modules java.se.ee' \

# https://stackoverflow.com/questions/46402772/failed-to-install-android-sdk-java-lang-noclassdeffounderror-javax-xml-bind-a
# Exception in thread "main" java.lang.NoClassDefFoundError: javax/xml/bind/annotation/XmlSchema


#export PATH=/usr/local/android-sdk/tools/bin:${PATH}   && echo "export PATH=/usr/local/android-sdk/tools/bin:${PATH}" >> /etc/profile

#sudo apt-get -y install qemu-kvm
#    qemu-kvm \
#    libglu1-mesa
#https://github.com/thyrlian/AndroidSDK/blob/master/android-sdk/Dockerfile
# set the environment variables
#ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
#ENV GRADLE_HOME /opt/gradle
#ENV KOTLIN_HOME /opt/kotlinc
#ENV ANDROID_HOME /opt/android-sdk
#ENV PATH ${PATH}:${GRADLE_HOME}/bin:${KOTLIN_HOME}/bin:${ANDROID_HOME}/emulator:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/tools/bin
#ENV _JAVA_OPTIONS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap

#echo "export PATH=/usr/local/android-sdk/tools/bin:${PATH}" >> /etc/profile \
# export ANDROID_HOME=/usr/local/android-sdk/

 
# echo "export ANDROID_HOME=/usr/local/android-sdk" >> /etc/profile
#echo "export PATH=$PATH:$ANDROID_HOME/tools" >> /etc/profile
#echo "export PATH=$PATH:$ANDROID_HOME/platforms-tools" >> /etc/profile
#echo 'export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:/bin/java::")' >> /etc/profile
