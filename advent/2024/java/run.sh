#!/bin/sh

dir=`pwd`
jar=`basename ${dir}`

echo ${jar}

java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.App test.txt
#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.App input.txt
