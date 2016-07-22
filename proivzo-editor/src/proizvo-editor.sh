#! /bin/sh

MY_ARGS=""
JAVA_ARGS=""

if [ "$JAVA_HOME" != "" ] ; then
    JAVA=$JAVA_HOME/bin/java
else
    JAVACMD=`which java 2> /dev/null `
    if [ -z "$JAVACMD" ] ; then
        echo "Error: cannot find java VM."
        exit 1
    else
        JAVA=java
    fi
fi

if [ -h $0 ]
then
    MY_SH=`ls -l "$0"`
    MY_SH=${MY_SH#*-> }
else
    MY_SH=$0
fi

CURRENT_DIR=`dirname "$MY_SH"`
MY_JAR=$CURRENT_DIR/../lib/proizvo-editor.jar

if [ ! -f $MY_JAR ]
then
    echo "Error: cannot find executable file in directory $CURRENT_DIR"
    exit 1
fi

$JAVA $JAVA_ARGS -DGNOME_DESKTOP_SESSION_ID=$GNOME_DESKTOP_SESSION_ID -DKDE_FULL_SESSION=$KDE_FULL_SESSION -DKDE_SESSION_VERSION=$KDE_SESSION_VERSION -jar $MY_JAR $MY_ARGS $@
