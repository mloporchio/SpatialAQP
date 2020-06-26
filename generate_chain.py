"""
    File:       generate_chain.py
    Author:     Matteo Loporchio, 491283

    This Python script generates a blockchain containing
    random 2D records and writes the output to a file.
"""

import random
import sys

def generate(filename, nblocks, minrec, maxrec):
	# Create a new file with the given name.
	f = open(filename, 'w')
	# The first line of the file contains the number of blocks.
	lines = [str(nblocks) + '\n']
	i = 0
	# For each block...
	while (i < nblocks):
		# Decide how many records it will contain.
		nrec = random.randint(minrec, maxrec)
		lines.append(str(nrec)+'\n')
		# Then generate the random records.
		for j in range(0, nrec):
			x = random.random()
			y = random.random()
			lines.append('{:f},{:f}\n'.format(x,y))
		i += 1
	f.writelines(lines)
	f.close()

def main(argv):
    if len(argv) < 5:
        print("Error: too few arguments!")
        return
    filename = argv[1]
    nblocks = int(argv[2])
    minrec = int(argv[3])
    maxrec = int(argv[4])
    generate(filename, nblocks, minrec, maxrec)

if __name__ == '__main__':
    main(sys.argv)
