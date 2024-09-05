Generate all possible point buy arrays for D&D 5e.  Implemented in java.

build:
	mvn clean package

run:
	java -cp target/point-buy-<version>,jar org.csgeeks.PointBuy

output:
	pointBuyArrays.list



Three different implementations:

bruteForceOne:
initial implementation that goes through very possible value for all stats

bruteForceTwo:
improvement by reducing iterations to skip duplicates (order does not matter)
reverse the iteration order, so we go from largest to the smallest

bruteForceThree:
slight improvement by skipping cases we know is not possible