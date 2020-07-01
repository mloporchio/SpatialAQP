"""
    File:       generate_chain.py
    Author:     Matteo Loporchio, 491283

    This Python script generates a blockchain containing
    random 2D records and writes the output to a binary file.
"""

import random
import struct
import sys

def generate_chain(filename, nblocks, minrec, maxrec):
	# Create a new file with the given name.
    f = open(filename, 'wb')
    # The first we write is the total number of blocks.
    f.write(struct.pack('>i', nblocks))
    # Then, for each block...
    for i in range(0, nblocks):
        # Decide how many records it will contain.
        nrec = random.randint(minrec, maxrec)
        # Write the number of records to file.
        f.write(struct.pack('>i', nrec))
        # Generate the random records and write them.
        for j in range(0, nrec):
            x = random.random()
            y = random.random()
            f.write(struct.pack('>dd', x, y))
    # Close the file.
    f.close()

def main(argv):
    if len(argv) < 5:
        print("Error: too few arguments!")
        return
    filename = argv[1]
    nblocks = int(argv[2])
    minrec = int(argv[3])
    maxrec = int(argv[4])
    generate_chain(filename, nblocks, minrec, maxrec)

if __name__ == '__main__':
    main(sys.argv)
