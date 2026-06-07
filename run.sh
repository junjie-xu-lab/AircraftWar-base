#!/usr/bin/env sh
set -e

BUILD_DIR="build/classes"

mkdir -p "$BUILD_DIR"
find src/edu -name "*.java" > build/sources.txt

javac -encoding UTF-8 -d "$BUILD_DIR" @build/sources.txt
java -cp "$BUILD_DIR:src" edu.hitsz.application.Main
