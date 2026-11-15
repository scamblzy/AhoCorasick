# Aho-Corasick Algorithm in Java

## 1. Project Overview

This project implements the Aho-Corasick string matching algorithm in Java.

The algorithm searches for multiple patterns simultaneously within a given text. It uses a Trie data structure, failure links, and output links to find all occurrences of the patterns efficiently.

The project also implements the Knuth-Morris-Pratt (KMP) algorithm to compare their performance.

## 2. Project Structure

```text
AhoCorasick/
│
├── src/
│   ├── TrieNode.java
│   ├── AhoCorasick.java
│   ├── KMP.java
│   ├── Experiment.java
│   ├── Testing.java
│   └── Main.java
│
├── results.csv
├── README.md
└── .gitignore
```

## 3. Implementation

### Trie Construction

The Trie stores all patterns in a shared prefix tree. Each node contains a HashMap of child nodes, allowing transitions between characters.

### Failure Links

Failure links are constructed using Breadth-First Search (BFS).

When a character does not match the expected transition, the algorithm follows failure links to find the longest available suffix that is also a prefix of another pattern.

### Output Links

Output links connect nodes to other terminal nodes representing patterns that also end at the current position.

This allows the algorithm to report multiple matching patterns, including overlapping occurrences.

### Searching

The algorithm processes the text from left to right. It follows Trie transitions and failure links and reports every matched pattern with its starting index.

## 4. Time Complexity

Let:

- n = length of the text
- m = total length of all patterns
- z = number of reported matches
- k = number of patterns

**Aho-Corasick:**

- Trie construction: O(m)
- Failure link construction: depends on the implementation and transition representation; the standard optimized sparse-trie implementation is linear in the total pattern length.
- Text searching: O(n + z) expected with constant-time hash map transitions.
- Total expected complexity: O(m + n + z), assuming efficient failure-link construction.

**KMP:**

- LPS construction for one pattern: O(m)
- Searching for one pattern: O(n + m)
- Searching for k patterns separately: O(k × n + m)

## 5. Space Complexity

**Aho-Corasick:**

O(m + k + z) including the Trie, links, stored pattern IDs, and returned matches.

The automaton itself requires O(m + k) space.

**KMP:**

O(m) auxiliary space for one pattern's LPS array. When patterns are processed sequentially, the auxiliary space is O(max pattern length), excluding the returned results.

## 6. Experimental Results

Each experiment was repeated 5 times, and the average execution time was calculated using System.nanoTime().

| Patterns | Text Length | Aho-Corasick (ms) | KMP (ms) |
|---:|---:|---:|---:|
| 10 | 1,000 | 0.5240 | 2.3649 |
| 10 | 10,000 | 2.1360 | 2.8641 |
| 10 | 100,000 | 3.3119 | 8.9569 |
| 100 | 1,000 | 0.8376 | 3.8685 |
| 100 | 10,000 | 1.0139 | 9.2015 |
| 100 | 100,000 | 4.6927 | 95.1221 |
| 1,000 | 1,000 | 3.9031 | 9.9558 |
| 1,000 | 10,000 | 2.2692 | 121.6317 |
| 1,000 | 100,000 | 5.6421 | 970.7482 |

## 7. Performance Analysis

The experimental results show that Aho-Corasick performs better than running KMP separately for every pattern, especially when the number of patterns and text length increase.

For 1,000 patterns and a text length of 100,000 characters, Aho-Corasick took 5.6421 ms, while KMP took 970.7482 ms.

This means KMP took approximately 172 times longer in this experiment.

The reason is that Aho-Corasick processes the text using one shared Trie, while KMP scans the text separately for each pattern.

Small variations in execution time are expected because of JVM optimization, garbage collection, and system load.

## 8. Advantages and Limitations

### Aho-Corasick

Advantages:
- Searches multiple patterns in one text traversal.
- Efficient when searching many patterns.
- Finds overlapping occurrences.
- Reuses common prefixes through Trie construction.

Limitations:
- Requires additional memory for the Trie and links.
- Building the automaton takes preprocessing time.
- Less beneficial when searching only one or very few patterns.

### KMP

Advantages:
- Simple implementation.
- Requires less auxiliary memory.
- Efficient for searching a single pattern.

Limitations:
- Searches patterns separately.
- Repeatedly processes the same text for different patterns.
- Becomes expensive when the number of patterns increases.

## 9. Conclusion

Aho-Corasick is a suitable algorithm for searching multiple patterns simultaneously.

The experiments demonstrate its performance advantage over running KMP separately for a large number of patterns.

KMP remains a good choice for individual pattern matching, while Aho-Corasick is more appropriate for applications such as keyword detection, text filtering, and multiple string searches.