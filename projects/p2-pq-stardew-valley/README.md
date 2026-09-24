# Project 2: Stardew Valley Priority Queue

* Author: Riley Cowan
* Class: CS321 Section 001
* Semester: Fall 2026

## Overview

This project implements a priority queue using a MaxHeap and uses it to simulate task scheduling in a simplified Stardew Valley game. Tasks are prioritized by their priority level and, when priorities are equal, by how early they were created. The simulation generates tasks, updates waiting priorities, manages energy, and reports the results of the simulated days.

## Reflection

This project helped me understand how a priority queue can be built on top of a heap and then used in a larger program. The MaxHeap was the part that required the most attention because inserting, removing, and rebuilding the heap all have to keep the heap property correct. Testing the heap with different priorities and tied priorities helped me make sure the ordering worked as expected.

The simulation was also useful because it showed how a data structure can be used as part of a larger system. One issue I had to work through was making sure waiting tasks increased in priority at the correct time without changing the provided simulation code. Comparing the simulation output with the provided test cases was helpful for finding and fixing those details.

## Compiling and Using

Compile the project with:

```bash
javac *.java
```

Run the simulation with:

```bash
java MyLifeInStardew <max-priority> <time-to-increment-priority> <total-simulation-days> <task-generation-probability> [seed]
```

For example:

```bash
java MyLifeInStardew 5 1 1 0.5 123
```

The optional seed makes the simulation repeatable for testing.

To run the provided automated test cases while the `test-cases` directory is present, use:

```bash
./run-tests.sh
```

## Results

The implementation was tested against all six provided simulation inputs. All six outputs matched the provided expected outputs when compared while ignoring whitespace differences, which is how the supplied test script performs its comparisons.

The MaxHeap implementation was also tested for insertion, extraction, maximum selection, priority ties, heap construction from an array, automatic array growth, and increase-key behavior.

## Sources used

The primary sources used for this project were the CS321 Project 2 specification, the provided CS321-resources starter files, the course interfaces, and the provided test cases.

AI assistance was used to help understand the project requirements, implement and check the heap and priority queue logic, create unit tests, and troubleshoot the simulation output. The prompt used included the CS321 Project 2 specification and the request: "complete what needs to be done." The completed code was tested against the provided simulation test cases.
