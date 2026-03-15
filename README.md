# Concurrency Testing of Thread-Safe Data Structures: Design Challenges and JCStress in Practice 

This repository contains the implementation and experiments conducted as part of my Master’s thesis in Software Design at the IT University of Copenhagen.

The project investigates how concurrency bugs can be detected using stress testing and oracle-based verification.
Experiments are implemented using JCStress, a framework designed to explore different thread interleavings in Java programs.

The goal is to identify correctness violations such as race conditions, lost updates, and non-linearizable executions.

# Key Contributions

* Designed a systematic evaluation pipeline for testing thread-safety of concurrent classes

* Implemented oracle-based verification for concurrent program testing

* Developed JCStress experiments with controlled read/write operations

* Analyzed correctness of concurrent behavior under different thread interleavings

* Applied the approach to both simple structures and real-world framework classes

* Encoded correctness properties using JCStress outcome classification

# Technologies

* Java

* JCStress

* JUnit

* Gradle

* Concurrent testing methodologies

# About Me

### Filip Beben

MSc Software Design – IT University of Copenhagen

### Interested in:

Concurrent systems

Low-level software

Reliability and correctness of software systems
