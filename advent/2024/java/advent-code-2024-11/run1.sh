#!/bin/sh

dir=`pwd`
jar=`basename ${dir}`

echo ${jar}

#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle1 test.txt 25
java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle1 input.txt 25
