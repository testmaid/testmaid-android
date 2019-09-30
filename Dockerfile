#sudo rm -rf /home/lojza/testmaid-android/app/build/
#docker build . -t testmaid
#docker run -ti --privileged -v /home/lojza/testmaid-android:/root/testmaid-android testmaid

FROM ubuntu:16.04
LABEL maintainer "t@testmaid.com"
ENV _JAVA_OPTIONS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap
RUN apt-get update \
&& apt-get install -y \
    curl \
    unzip \
    git \
&& apt-get install -y --no-install-recommends default-jdk-headless\
&& mkdir -p /usr/local/android-sdk \
&& cd /usr/local/android-sdk/ \ 
&& curl -O https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip \
&& unzip sdk-tools-linux-4333796.zip \
&& echo y | /usr/local/android-sdk/tools/bin/sdkmanager "platform-tools" "platforms;android-28" \
#&&
# cd $HOME \
#git clone
#&& cd testmaid-android \
#&& echo 'sdk.dir=/usr/local/android-sdk/' >> local.properties \
#&& chmod 775 gradlew \
#&& ./gradlew assembleDebug
#&& ./gradlew assembleAndroidTest
# \
#
# Emulator
&& apt-get install -y --no-install-recommends \
    libgl1-mesa-dev \
    libpulse0 \
&& ln -sf /usr/share/zoneinfo/US/Eastern /etc/localtime \
&& echo y | /usr/local/android-sdk/tools/bin/sdkmanager --install "system-images;android-18;google_apis;x86" "emulator" \
&& echo no | /usr/local/android-sdk/tools/bin/avdmanager create avd --force --name api18 -k "system-images;android-18;google_apis;x86" 
