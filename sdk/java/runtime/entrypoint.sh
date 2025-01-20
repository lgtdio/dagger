#!/bin/sh

if [ -z "$1" ]; then
  echo "Usage: $0 <path_to_module.jar>"
  exit 1
fi

JAR_PATH="$1"

if [ ! -f "$JAR_PATH" ]; then
  echo "error: file '$JAR_PATH' does not exist."
  exit 1
fi

ARCH=$(uname -m)

JVM_OPTIONS=""
if [[ "$ARCH" == "aarch64" ]]; then
  JVM_OPTIONS="-XX:UseSVE=0"
fi

exec java $JVM_OPTIONS -jar "$JAR_PATH"
