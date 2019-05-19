## FST 

using for indexing fields of text/keyword type, relevant lucene's files:
- .tip(term index): resided **OnHeap**, contains fst's prefix to determine whether a term exists on disk
- .tim(term dictionary): contains fst's suffix、term's freq and the file pointer to posting lists(.doc)

## [BKD-Tree](https://users.cs.duke.edu/~pankaj/publications/papers/bkd-sstd.pdf)

using for indexing fields of number type, relevant lucene's files:
- .dii: resided **OnHeap**, contains field count, and the file pointer in the .dim file
- .dim: all fields' point index, each contains: 
    - leaf block: value's common prefix, docs and values (same ordinal with docs)
    - packed index: index for seeking leaf block

## Field
contains all fields' metadata, and using for retrieving doc's stored fields. The relevant lucene's files:
- .fdx: resided **OnHeap**, each field's file pointer to field data(.fdt)
- .fnm (fields' metadata): number of fields, and each field contains name, ordinal, and some index/docvalue options
- .fdt: store fields' data of document by chunks

## Posting lists (i.e, together with .tim of FST composed as inverted index)
- .doc

## DocValues (i.e, forward index)
- .dvd: data
- .dvm: metadata

## [RoaringBitmaps](https://arxiv.org/pdf/1603.06549.pdf)
using for docIds cache, and the .dvd file of DocValues

## Compound File
consisting of all the other index files to reduce file handles required during searching. The relevant lucene's files:
- .cfs
- .cfe