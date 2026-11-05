#!/bin/sh
set -eu
cd "$(dirname "$0")"
export JAVA_HOME="${TVQUIZ_JAVA_HOME:-/opt/homebrew/opt/openjdk@21}"
export PATH="$JAVA_HOME/bin:$PATH"
BT="$PWD/.tools/build-tools35/android-15"
PLATFORM="$PWD/.tools/platform/android-11/android.jar"
mkdir -p out/classes out/dex
"$BT/aapt" package -f -M app/src/main/AndroidManifest.xml -S app/src/main/res -I "$PLATFORM" -F out/resources.apk
javac -encoding UTF-8 --release 8 -classpath "$PLATFORM" -d out/classes app/src/main/java/vn/tvquiz/*.java
jar cf out/classes.jar -C out/classes .
"$BT/d8" --lib "$PLATFORM" --min-api 26 --output out/dex out/classes.jar
cp out/resources.apk out/unsigned.apk
(cd out/dex && zip -q -u ../unsigned.apk classes.dex)
"$BT/zipalign" -f 4 out/unsigned.apk out/aligned.apk
if [ ! -f .tools/test-signing.jks ]; then
 keytool -genkeypair -keystore .tools/test-signing.jks -storepass android -keypass android -alias tvquiz -keyalg RSA -keysize 2048 -validity 10000 -dname 'CN=TV Quiz Test'
fi
"$BT/apksigner" sign --ks .tools/test-signing.jks --ks-key-alias tvquiz --ks-pass pass:android --key-pass pass:android --out out/Hoc-Roi-Xem-TV-v1.2.0.apk out/aligned.apk
"$BT/apksigner" verify --verbose out/Hoc-Roi-Xem-TV-v1.2.0.apk
"$BT/aapt" dump badging out/Hoc-Roi-Xem-TV-v1.2.0.apk
shasum -a 256 out/Hoc-Roi-Xem-TV-v1.2.0.apk
