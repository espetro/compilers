This repo contains all the compilers I've done in the course *Procesadores de Lenguajes* from the *University of Málaga*.
Moreover, there are a bunch of them that we're not required in the course (I did them by my own).

## Requirements
   + Java SDK (at least version 8.0, but I'm using 13.0).
   + JFlex
   + CUP

JFlex and CUP are intended to work together as a meta-compiler tool.

## Compilers

As I have no intention of having a loooot of repos holding each compiler, yet I want it to be "organized",
I've put each version in a different branch (actually, they all come from a base version)

  + In `master`, there's PL, the base language
  + In `PLX{year}` there's the PL-eXtended version for each year
  + In `{language}PL` there's a PL version for the target language (e.g. Python)
  + Finally, in `extras/{language}` there are my own versions of other compilers (e.g. Java Bytecode to WASM Bytecode)

Beware that all compilers are using their own list of commits - they're not intended to be merged as done with usual branches.
Moreover, most of them are using a IntellijIdea workspace configuration.

## PL language

  + `src` contains all the files needed by the specification
  + The specification is available at `README.pdf`
  + The test input can be found at `in/`
  + The test output can be found at `out/`
  + Successful releases done previously are at `releases/`
  + Executables used to check the source quality are at `scripts`. As of now, there are `ctd-win64`, which allows to test all the code in `out/`, and `plxc`, which allows to check the output code produced by the compiler.