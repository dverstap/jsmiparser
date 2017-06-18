if [ -z $TOOLS_DIR ]
then
    echo "env var TOOLS_DIR not set"
    return
fi

JAVA_HOME=$TOOLS_DIR/jdk6
export JAVA_HOME

PATH=$JAVA_HOME/bin:$PATH
export PATH

# Gradle 2 is the last one to work with Java 6 (and does not work with 5):
GRADLE_HOME=$TOOLS_DIR/gradle2
export GRADLE_HOME

PATH=$GRADLE_HOME/bin:$PATH
export PATH
