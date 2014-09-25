#!/bin/sh

FILE=$1

mvn -U clean install -Pvalidate -Dfile=$FILE -Dscope=cv -Dlevel=WARN -Dmaven.test.skip
