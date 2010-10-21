# README #

Converts a csv file to a [MultiMarkdown](http://www.practicallyefficient.com/tag/multimarkdown/) [formatted table](http://fletcherpenney.net/multimarkdown/users_guide/multimarkdown_syntax_guide/#tables).

All headers and fields will be left-aligned, no headers are grouped, every fields has default format.

## Building ##

	mvn clean package

## Usage ##

	./csv2mmdtable path/to/file.csv
	
You can also set the charset of the file (defaults to `UTF-8`)
	
	./csv2mmdtable -c ISO-8859-1 path/to/file.csv
	./csv2mmdtable --charset ISO-8859-1 path/to/file.csv
