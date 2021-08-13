#!/bin/bash
LOGRESULT=$(docker ps -a --format {{.Names}} --filter name=_testing_ | head -1 | xargs docker logs $1 2>&1 | awk '/Tests run:/{b=$0; split($0,a,", "); split(a[1],total,": "); split(a[2],failed,": "); split(a[3],skipped,": ")}END{print "{total:"total[2]",failed:"failed[2]",skipped:"skipped[2]"}"}')
echo "RESULT=$LOGRESULT"

CHECK="failed:0,"
RESULT=$(echo $LOGRESULT | grep ${CHECK})

if [[  "${RESULT}" != "" ]]; then
  echo "SUCCESS"
  exit 0 ## SUCCESS
else
  echo "FAILED"
  exit 1 ## FAILED
fi

