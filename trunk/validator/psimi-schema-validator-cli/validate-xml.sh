#!/bin/sh

FILE=$1

mvn clean install -Pvalidate -Dfile=$FILE -Dscope=xml -Dlevel=WARN -Dmaven.test.skip
