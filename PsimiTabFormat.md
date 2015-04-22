# PSI-MI TAB Format #

## Introduction ##

The MITAB25 format is part of the PSI-MI 2.5 standard (1). It has been derived from the tabular format provided by BioGrid. MITAB25 only describes binary interactions, one pair of interactors per row. Columns are separated by tabulations. Tools allowing to manipulate this data format are available (2).

(1) http://www.pubmedcentral.nih.gov/articlerender.fcgi?tool=pubmed&pubmedid=17925023

(2) http://www.psidev.info/index.php?q=node/60#tools


## Column definitions ##

The column contents should be as follows:

  1. **Unique identifier for interactor A**, represented as databaseName:ac, where     databaseName is the name of the corresponding database as defined in the [PSI-MI controlled vocabulary](http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI:0444&termName=database%20citation), and ac is the unique primary identifier of the molecule in the database. Identifiers from multiple databases can be separated by "|". It is recommended that proteins be identified by stable identifiers such as their UniProtKB or RefSeq accession number.
  1. **Unique identifier for interactor B**.
  1. **Alternative identifier for interactor A**, for example the official gene symbol as defined by a recognised nomenclature committee. Representation as databaseName:identifier. Multiple identifiers separated by "|".
  1. **Alternative identifier for interactor B**.
  1. **Aliases for A**, separated by "|". Representation as databaseName:identifier. Multiple identifiers separated by "|".
  1. **Aliases for B**.
  1. **Interaction detection methods**, taken from the corresponding [PSI-MI controlled Vocabulary](http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI:0001&termName=interaction%20detection%20method), and represented as darabaseName:identifier(methodName), separated by "|".
  1. **First author** surname(s) of the publication(s) in which this interaction has been shown, optionally followed by additional indicators, e.g. "Doe-2005-a". Separated by "|".
  1. **Identifier of the publication** in which this interaction has been shown. Database name taken from the [PSI-MI controlled vocabulary](http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI:0445&termName=literature%20database), represented as databaseName:identifier. Multiple identifiers separated by "|".
  1. **NCBI Taxonomy identifier for interactor A**. Database name for NCBI taxid taken from the [PSI-MI controlled vocabulary](http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI:0444&termName=database%20citation), represented as databaseName:identifier (typicaly databaseName is set to 'taxid'). Multiple identifiers separated by "|". Note: In this column, the databaseName:identifier(speciesName) notation is only there for consistency. Currently no taxonomy identifiers other than NCBI taxid are anticipated, apart from the use of -1 to indicate "in vitro", -2 to indicate "chemical synthesis", -3 indicates "unknown", -4 indicates "in vivo" and -5 indicates "in silico".
  1. **NCBI Taxonomy identifier for interactor B**.
  1. **Interaction types**, taken from the corresponding [PSI-MI controlled vocabulary](http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI:0190&termName=interaction%20type), and represented as dataBaseName:identifier(interactionType), separated by "|".
  1. **Source databases** and identifiers, taken from the corresponding [PSI-MI controlled vocabulary](http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI&termId=MI:0444&termName=database%20citation), and represented as databaseName:identifier(sourceName). Multiple source databases can be separated by "|".
  1. **Interaction identifier(s)** in the corresponding source database, represented by databaseName:identifier
  1. **Confidence score**. Denoted as scoreType:value. There are many different types of confidence score, but so far no controlled vocabulary. Thus the only current recommendation is to use score types consistently within one source. Multiple scores separated by "|".

All columns are mandatory.

## Syntax ##

Columns are normally formed by fields delimited by "|", with a structure like this one:

```
<XREF>:<VALUE>(<DESCRIPTION>)
```

Due to the unsafe use of reserved characters in the values, we have recently added the possibility to surround `<XREF>`, `<VALUE>` or `<DESCRIPTION>` with quotes if they contain a special symbol.

In MI-TAB, the reserved characters are:

```
|
(
)
:
\t (tabulation)
```

Whenever this happen in your data, surround the value with double quotes:

```
"<XREF_WITH_RESERVED_CHARS>":"<VALUE_WITH_RESERVED_CHARS>"("<DESCRIPTION>")
```

Note that the quotes are before and after each part. The escaped data should look like in the following examples:

```
psi-mi:"MI:0000"(a cv term)
psi-mi:"MI:0000"("I can now use braces ()()() or pipes ||| here and ::colons::")
```
If you want to use a quote within a quote, escape it:

```
uniprotkb:P12345("a \"nice\" protein")
```