#!/bin/sh

FILE=$1

mvn clean install -Pvalidate -Dfile=$FILE -Dscope=imex -Dlevel=WARN -Dmaven.test.skip
