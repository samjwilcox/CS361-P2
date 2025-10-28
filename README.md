# Project 2: Nondeterministic Finite Automata

* Author: Tyler Fernandez; Sam Wilcox
* Class: CS361 Section 1
* Semester: Fall 2025

## Overview

This program models an instance of a nondeterministic finite automata
and its behavior.

## Reflection

This project mostly went smoothly, the only issue being the eClosure method. This
was a massive problem, as to even begin the other methods, eClosure first needed to be
implemented. The problem that I was having a compile time error in testing. Eventually, 
I realized that I needed to cast the getState as a NFAState, which fixed the error.
After this project, I have a good understanding of DFAs after this project. 
Some techniques that I used to make our code easy to debug and modify include using 
even spacing and comments. Specifically for the NFA implementation, I had to open JFlap 
and to see what the automata looked like, as well as to see how many max copies there were.

If I could change something about my design process, I would give more time to identify
edge cases and testing. My parent and I got caught up in a lot of other work, which
made it hard to find time to thoroughly test the program. If I could go back in time,
I would tell myself to start earlier on this project, as it would've allowed more time
for testing. 

## Compiling and Using

To compile this code, upload the files to onyx and run the following commands:
```
javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java
```
Then to run the tests:
```
java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.nfa.NFATest
```

## Sources used

Used class files.