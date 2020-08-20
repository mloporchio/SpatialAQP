#!/bin/bash
#
# File:   test_construction.sh
# Author: Matteo Loporchio
#
# This script can be used to test the MR-tree index construction time
# for data sets of variable size.

CLASS="TestIndex"
TEST_PATH="test/construction"
SUFFIX="_0_1000.bin"
OUTFILE="${TEST_PATH}/test_construction.csv"
TEST_LIST=( "${TEST_PATH}/*${SUFFIX}" )
CAPACITY=10
REPEAT=5

# This step is required since it looks like Java does not execute classes
# that are not in the current directory.
cd "../.."

# Open the output file.
exec 3>$OUTFILE
# Process each test case.
for TESTCASE_P in $TEST_LIST; do
    # Obtain the name of the data set.
    TESTCASE=${TESTCASE_P%"$SUFFIX"}
    # Obtain the values of the parameter.
    N=$(echo $TESTCASE | cut -d "_" -f 2)
    echo "Processing N=${N}..."
    COUNT=0
    AVG_TIME=0
    # Test for REPEAT times.
    while [  $COUNT -lt $REPEAT ]; do
      # Run Java program.
      OUTPUT=$(java ${CLASS} "${TESTCASE_P}" ${CAPACITY})
      TIME=$(echo $OUTPUT | cut -d ':' -f 4)
      TIME=${TIME%"ms"}
      AVG_TIME=$(echo "${AVG_TIME} + ${TIME}" | bc -l)
      # Increment counter.
      COUNT=$((COUNT + 1))
    done
    # Compute the average.
    AVG_TIME=$(echo "scale=6; ${AVG_TIME} / ${REPEAT}" | bc -l)
    # Write the results to the output file.
    echo "${N},${AVG_TIME}" 1>&3
done
