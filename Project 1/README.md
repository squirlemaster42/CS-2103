# Project 1

Created a program for generating, solving, and validating 3-dimensional word searches.


Our approach for finding words in the 3d space was to first locate each time the first or last letter
of the word appears. We then use the principle that, for the word to be valid, the distance between
the first and last letter of the word in each direction will either be 0 or the length of the word.
If this check passes we then check that the word actually exists between these two points.


For generating word searches, we first check that the word does not already exist within the 3d space
using the method above. We then choose a word to place and randomly choose a starting location. We then
randomly generate a vector to place the world along. To ensure that words do not override other words
that have already been placed we begin with a blank grid and only place words in locations that have
the desired letter already in place or are blank.
