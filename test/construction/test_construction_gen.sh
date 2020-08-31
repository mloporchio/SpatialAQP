#!/bin/bash
#
# File:   test_construction_gen.sh
# Author: Matteo Loporchio
#
# This script can be used to generate the test cases for
# the MR-tree index construction time.

SCRIPT="generate_records.py"
TEST_PATH="test/construction"
OUTFILE="records"
TEST_LIST=( 100 250 500 750 1000 2500 5000 7500 10000 25000 50000 75000 100000 )
MINVAL=0
MAXVAL=1000

cd "../.."

for N in "${TEST_LIST[@]}"; do
  echo "Generating N=${N}..."
  FILENAME="${TEST_PATH}/${OUTFILE}_${N}_${MINVAL}_${MAXVAL}.bin"
  python3 $SCRIPT $FILENAME $N $MINVAL $MAXVAL
done

echo "Done!"
