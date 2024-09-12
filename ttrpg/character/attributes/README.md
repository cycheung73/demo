Initial implementation to explore requirements.

Next refinement will separate Attributes from the application, so all the methods do not need to be static.  In addition, expand the Attributes class to hold name, modifier, and maybe even check for minimum and maxmium allowable values.

Possible additional refinements, move the values for PointBuyChoices into a JSON file that is read in automatically.
====================
compile:
  mvn clean dependency:copy-dependencies package

run:
  java -cp target/attributes-<version>.jar org.csgeeks.Attributes
====================
DESIGN

attributes: a group of attributes

attribue: contains

name: a string
abbreviation: 3 character representation of the name
value: a number between 1 and 20, but cannot start off higher than 18
modifier: derived number based on value [round((value - 10)/2)]
description: a detailed description of the attribute

mininum_value
maximum_value
creation_minimum
creation_maximum

name		: string
abbrev		: 3 character string
value		: positive integer
mod		: positive or negative integer
desc		: string

min		: positive integer
max		: positive integer greater than min

create_min	: positive integer greater than or equal to min, less than create_max
create_max	: positive integer greater than create_min, less than or equal to max

attribute:
	attribute type:	name, abbreviation, description
	set/get

	attribute value: base, value, modifier, minimum_value, maximum_value, creation_minimum, creation_maximum
	set/get, generate (different ways to generate)

	attribute value: D&D 5e (2024) point buy costs
	attribute value: PF2e boosts and flaws



D&D 5e (2024):
Strength	Physical might
Dexterity	Agility, reflexes, and balance
Constitution	Health and stamina
Intelligence	Reasoning and memory
Wisdom		Perceptiveness and mental fortitude
Charisma	Confidence, poise, and charm

D&D 5e (2014)
Strength	measuring physical power		natural athleticism, bodily power
Dexterity	measuring agility			physical agility, reflexes, balance, poise
Constitution	measuring endurance			health, stamina, vital force
Intelligence	measuring reasoning and memory		mental acuity, information recall, analytical skill
Wisdom		measuring perception and insight	awareness, intuition, insight
Charisma	measuring force of personality		confidence, eloquence, leadership

abiilty score
0		special
1		lowest normally
2-9		weak capability
10-11		human average
12-19		strong capability
20		highest for adventurers, unless a feature says otherwise
21-29		extraordinary capability
30		highest

ability modifier
modifier = round_down(score - 10)/2


D&D 5e (2014)
random rolls: 4d6 drop lowest
standard array:	15, 14, 13, 12, 10, 8
variant: point buy - 27 points
	 8	0
	 9	1
	10	2
	11	3
	12	4
	13	5		8 to 13: score - 8 = cost
	14	7		14 & 15: (score - 8) + (score - 13) = cost
	15	9

15, 15, 15, 8, 8, 8
13, 13, 13, 12, 12, 12
