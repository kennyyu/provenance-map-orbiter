Provenance Map Orbiter
======================

This is a fork of the [mercurial repo](https://github.com/kennyyu/provenance-map-orbiter).

See the [orbiter paper](https://www.usenix.org/legacy/event/tapp11/tech/final_files/MackoSeltzer.pdf).

## How to Build

To build:

    ant

This should create `dist/Orbiter.jar`. To run:

    ./orbiter

## Using `.twigxml` files

I (Kenny) have made a patch to orbiter to allow support of xml PASS dumps (output of `convert2xml`: one of the passtools utilities). These xml files must be saved with the `.twigxml` extension.
