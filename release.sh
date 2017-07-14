#!/usr/bin/env bash

set -e

if [ $# != "1" ]
then
    echo "Usage: release.sh <version>"
    exit -1
fi

VERSION=$1
SRC_DIST=jsmiparser-src-$VERSION

rm -rf $SRC_DIST
git tag $VERSION

git archive --format=tar --prefix=$SRC_DIST/ $VERSION | tar xf -

cd $SRC_DIST
./gradlew -Pversion=$VERSION clean install stest uploadArchives

echo "Successfully released $VERSION to the staging repo."
echo "After verification you still need to:"
echo "1. Release the staging repo at http://oss.sonatype.org"
echo "2. git push --tags origin $VERSION"
