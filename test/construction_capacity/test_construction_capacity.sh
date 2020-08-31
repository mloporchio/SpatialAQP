#!/bin/bash
#
# File:   test_construction_capacity.sh
# Author: Matteo Loporchio
#
# This script can be used to test the MR-tree index construction time
# for different page capacities.

CLASS="TestIndex"
TEST_PATH="test/construction_capacity"
TESTCASE="${TEST_PATH}/records_10000_0_1000.bin"
OUTFILE="${TEST_PATH}/test_construction_capacity.csv"
CAPACITY_LIST=( 10 25 50 75 100 250 500 750 1000 2500 5000 7500)
REPEAT=5

# This step is required since it looks like Java does not execute classes
# that are not in the current directory.
cd "../.."

# Open the output file.
exec 3>$OUTFILE
# Process each test case.
for CAPACITY in ${CAPACITY_LIST[@]}; do
    echo "Processing C=${CAPACITY}..."
    COUNT=0
    AVG_TIME=0
    # Test for REPEAT times.
    while [  $COUNT -lt $REPEAT ]; do
      # Run Java program.
      OUTPUT=$(java ${CLASS} "${TESTCASE}" ${CAPACITY})
      TIME=$(echo $OUTPUT | cut -d ':' -f 4)
      TIME=${TIME%"ms"}
      AVG_TIME=$(echo "${AVG_TIME} + ${TIME}" | bc -l)
      # Increment counter.
      COUNT=$((COUNT + 1))
    done
    # Compute the average.
    AVG_TIME=$(echo "scale=6; ${AVG_TIME} / ${REPEAT}" | bc -l)
    # Write the results to the output file.
    echo "${CAPACITY},${AVG_TIME}" 1>&3
done
