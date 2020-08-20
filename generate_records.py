"""
    File:       generate_records.py
    Author:     Matteo Loporchio, 491283

    This Python script generates a file containing random 2D records.
    Points are generated according to a uniform distribution.

    Usage:
    python generate_records.py <filename> <nrec> <minval> <maxval>
"""

import random
import struct
import sys

def generate_records_bin(filename, nrec, minval, maxval):
    f = open(filename, 'wb')
    for i in range(0, nrec):
        x = random.uniform(minval, maxval)
        y = random.uniform(minval, maxval)
        f.write(struct.pack('>dd', x, y))
    f.close()

def main(argv):
    if len(argv) < 5:
        print("Error: too few arguments!")
        return
    filename = argv[1]
    nrec = int(argv[2])
    minval = int(argv[3])
    maxval = int(argv[4])
    generate_records_bin(filename, nrec, minval, maxval)

if __name__ == '__main__':
    main(sys.argv)
