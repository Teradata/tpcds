# TPCDS [![BuildStatus](https://travis-ci.org/Teradata/tpcds.svg?branch=master)](https://travis-ci.org/Teradata/tpcds)

The Java version of the TPC-DS data generator is based on the C dsdgen distributed
by the TPC organization. Here is a brief outline of the components and structure of our
version of TPC-DS dsdgen and the C version that it is based on.

## Getting Started
This project uses maven.  To build it run `mvn install`.

## Using the Data Generator

Tables can be generated on the command line, or within a Java program.

### Generating Tables From the Command Line
If the program is run with no arguments, it will generate all of the TPC-DS tables at
a scale factor of 1GB.  The results will be written out to files named `<table_name>.dat`

```java -jar tpcds-1.2-SNAPSHOT-jar-with-dependencies.jar```

If you wanted to generate just the call_center table at a scale of 10GB, you would run

```java -jar tpcds-1.2-SNAPSHOT-jar-with-dependencies.jar --table call_center --scale 10```

For a list of all options, run `--help`

```java -jar tpcds-1.2-SNAPSHOT-jar-with-dependencies.jar --help```

### Generating Tables Within a Java Program
To generate a section of a table, call
`Results.constructResults(table, startingRowNumber, endingRowNumber, session)`.  This
will generate a `Results` object. `Results` is an iterable, that lazily generates the
next row of the table as it is called.  The startingRowNumber and endingRowNumber are
the boundaries of the table you would like to generate.  To determine the boundaries for
a particular chunk of a parallel build, call `Parallel.splitWork()`. The session contains
the relevant options for this build including the scaling information, how many pieces
you want to generate this table in, and which chunk we are up to.  Look at `Session.java`
to see the full set of items to specify.

If all you want to do with the results is write them out to a file, you could instead
call `TableGenerator.generateTable()`.

## Understanding the Code
### General Methodology
The goal of this project is to rewrite the generator distributed by the TPC organization
from C to Java. This means we need our implementation to have the exact  same behavior as
the C implementation.  Therefore, when the C implementation has bugs (and it has many),
we copy those bugs. Generally when copying over bugs, we include a comment for clarity.

### Table Definitions
In the C code, table definitions and other information associated with each table, such
as the column streams (a stream is a random number generator. You have a different stream
for each column) are found in the text file `column_list.txt`. This file gets used by 
`mkheaders.c` to create three different `.h` files: `streams.h`, `columns.h`, and
`tables.h`. They are generated programmatically because they need to be kept in sync.
There are many fields defined in these files that never get used. In our implementation,
we try to limit the noise and only include the fields that are useful.

There are two types of tables in the C implementation: source tables and the actual
tables that you are trying to generate. The files for generating source tables are
prefixed with an `s_`. The files for generating the end-goal tables are prefixed with a
`w_`. For example, there is s `s_call_center.c` and `w_call_center.c.`  It seems that source
tables are only used when the dsdgen is run with the update flag.  We have not yet
investigated what the update flag means or what the purpose of the source tables are. Right
now we only have implementations for the `w_` tables.
 
The Java implementation is structured as follows: for each TPC-DS table the following are
defined

* RowGenerator
    * This generates the rows for the table and returns a result containing a TableRow.
      It is based on the `mk_w_xxx()` function from the `.c` files for each table
      (`w_xxx.c`).
* TableRow
    * A TableRow has as all the fields for printing a table
    * A TableRow has a getValues() method for returning String representations (or nulls
      for null values) of each of the columns in the table. It is based on `pr_w_xxxx()`
      from the `.c` files for each table (`w_xxx.c`).
* Column - an enum of columns, that implement the Column interface.  Each column contains:
    * globalId number
        * The globalId number comes from  `columns.h`. It is used to set the seed for the
          RandomNumberStream, and to determine whether a value in a particular row should
          be null.
    * RandomNumberStream
        * The random number stream generates the random numbers used to generate random
          data for the tables.
        * The seed is set by computation based off of the global id of the column.
        * The seedsPerRow field is taken from the value in column_list.txt. At the end of
          the generation of a single row, if fewer numbers have been generated than
          seedsPerRow, then the remaining seeds are used up.
* Table - each table contains the following:
    * tableFlags
        * This field comes from the flags defined in `w_tdefs.h`. Some flags defined in
          `w_tdefs.h` are never used, so we omit them.
    * nullBasisPoints
        * The field comes from `w_tdefs.h` and defines the proportion of rows that can be
          null. One basis point is equal to 1/100 of a percent.
    * notNullBitMap
        * This field comes from `w_tdefs.h`. It is a bitmap that defines columns that
          cannot be null. It is used when creating the nullBitMaps for a row that define
          which columns in the row should be null.
    * RowGenerator class
        * This is the class of the RowGenerator used to generate rows for the table. We
          use ThreadLocal instances of the row generators, because for some tables, state
          is shared across multiple calls.
    * A list of the columns in a table
    * ScalingInfo
        * This comes from row_counts.dst. It contains the information required to compute
         the row counts for the table at different scales.


### Random Value Generation
Random value generation for table data can be found in `genrand.h` and `genrand.c` in the
C implementation. In our implementation equivalent functionality can be found in
`RandomNumberStreams.java` and`RandomValueGenerator.java`

### Distributions
For many columns, values are chosen with randomness from a distribution deined in a `.dst`
file. The `.dst` files contain the values to be chosen from (e.g. a list of girls' names),
and sets of weights to determine the probability that any particular value is chosen (so
more common names are more likely to be generated).
 
In the C implementation, the distribution files are compiled into a binary using `dcomp.c`
and `dcomp.h`. The resulting binary file is `tpcds.idx`, and it goes with `tpcds.idx.h`.

In the Java implementation we do not compile the `.dst` files, instead we read them in at
runtime. We put each distribution into its own file and did some slight preprocessing to
make them easier to process when we read them.  In particular, we removed the 'create'
and  'set' statements, and from the data we remove `add (`  from the beginning of the
lines, `);` from the end of the lines, and the quotation marks from around strings. We
have a utility class called `DistributionUtils` that contains methods that are common
to many distributions, including those for reading in the distribution files.  We then
created separate classes for each distribution. There were many distributions whose
values were all strings, or all integers. Those distributions are all instances of the
same class (`StringValuesDistribution` and `IntValuesDistribution`respectively).

### Testing
We unit test the tables by taking md5 hashes of the tables that we generate and comparing
them to the md5sums of the tables generated by the tables generated by the dsdgen binary.
We test the tables at all defined scales as well as at a scale that is in between the
defined scales, to ensure that the row computation is correct for all scenarios.  For
very large tables, we test the md5sums of several samples of the data.  To generate the
dsdgen results, run

```./dsdgen -table <table-name> -scale <scale-factor> [-parallel <parallelism> -child <child-number>]```
