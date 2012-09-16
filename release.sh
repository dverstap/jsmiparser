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
gradle -Pversion=$VERSION clean install stest uploadArchives

git push --tags origin $VERSION

echo "Successfully released $VERSION to origin and the staging repo."
echo "After verification you still need to:"
echo "1. git push --tags github $VERSION"
echo "2. Release the staging repo at http://oss.sonatype.org"
