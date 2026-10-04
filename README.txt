# Glorpo PDF Builder

A lightweight Java library for generating PDF files, built directly from ISO-32000-2 (PDF 2.0) specification. 


## Why

Many PDF libraries generate the whole document in memory and serialize the file throughout or at the end. This one is designed to stream the file as objects are generated, so memory use stays flat.

## How it works
Currently each PDF object type is represented by a set of Models that share common interfaces, so objects can be composed and written polymorphically.


## Status
- The current implementation uses ByteArrayOutputStream rather than an Abstract OutputStream and will require refactoring to meet the requirement of true streaming. 
- You can see the tests which create valid PDF files. I am currently using this library for mass generating barcodes with text. 


## Roadmap

- [ ] Modify interfaces to allow for consistent handling of Dictionary Keys, Dictionary Brackets, and Indentations.
- [ ] Migrate from `ByteArrayOutputStream` to `OutputStream` for true streaming capabilities.
- [ ] Introduce an API
