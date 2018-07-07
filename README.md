# Searcher
Creation of a simple searcher to work with an indexer.

## How To Run
Execute the application with the following command:
```
indexer queries first_score second_score
```
Parameters:
- **indexer** - file with indexer;
- **queries** - file with queries;
- **first_score** - file to write the first score (number of different terms in indexer);
- **second_score** - file to write the second score (frequency of terms in indexer);

Example:
```
indexerT1.txt cranfield.queries.txt numberofTerms.txt termsFrequency.txt
```
