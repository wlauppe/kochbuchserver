#if [ $USER==wolf ]
#then
#    export JAVA_HOME=/usr/lib/jvm/jdk-13.0.1
#fi
cd ~/kochbuchserver/exzellenzkoch
./gradlew build -x test
