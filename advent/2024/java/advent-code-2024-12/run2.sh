#!/bin/sh

dir=`pwd`
jar=`basename ${dir}`

echo ${jar}

#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle2 test1.txt
java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle2 test2.txt
#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle2 test3.txt
#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle2 test4.txt
#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle2 test5.txt
#java -cp target/${jar}-1.0-SNAPSHOT.jar org.csgeeks.Puzzle2 input.txt
