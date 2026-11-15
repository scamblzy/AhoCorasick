import java.util.*;

public class AhoCorasick {

    private final TrieNode root;

    public AhoCorasick() {
        root = new TrieNode();
    }

    // 1. Add pattern to Trie
    public void addPattern(String pattern, int patternId) {

        TrieNode current = root;

        for (char c : pattern.toCharArray()) {

            if (!current.children.containsKey(c)) {
                current.children.put(c, new TrieNode());
            }

            current = current.children.get(c);
        }

        current.patternIds.add(patternId);
    }

    // 2. Build Failure Links and Output Links using BFS
    public void buildFailureLinks() {

        Queue<TrieNode> queue = new LinkedList<>();

        root.failure = root;

        // Initialize root children
        for (TrieNode child : root.children.values()) {
            child.failure = root;
            child.outputLink = null;
            queue.add(child);
        }

        while (!queue.isEmpty()) {

            TrieNode current = queue.poll();

            for (Map.Entry<Character, TrieNode> entry
                    : current.children.entrySet()) {

                char c = entry.getKey();
                TrieNode child = entry.getValue();

                TrieNode failureNode = current.failure;

                while (failureNode != root
                        && !failureNode.children.containsKey(c)) {

                    failureNode = failureNode.failure;
                }

                if (failureNode.children.containsKey(c)
                        && failureNode.children.get(c) != child) {

                    child.failure = failureNode.children.get(c);

                } else {
                    child.failure = root;
                }

                // Set Output Link
                if (!child.failure.patternIds.isEmpty()) {

                    child.outputLink = child.failure;

                } else {

                    child.outputLink = child.failure.outputLink;
                }

                queue.add(child);
            }
        }
    }

    // 3. Search for all pattern occurrences
    public List<String> search(String text, List<String> patterns) {

        List<String> results = new ArrayList<>();

        TrieNode current = root;

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            // Follow Failure Links if transition does not exist
            while (current != root
                    && !current.children.containsKey(c)) {

                current = current.failure;
            }

            // Move to next Trie node if possible
            if (current.children.containsKey(c)) {

                current = current.children.get(c);
            }

            // Check patterns ending at current node
            reportMatches(current, i, results, patterns);

        }

        return results;
    }

    // 4. Report all matched patterns
    private void reportMatches(
            TrieNode node,
            int endIndex,
            List<String> results,
            List<String> patterns) {

        TrieNode temp = node;

        while (temp != null && temp != root) {

            for (int patternId : temp.patternIds) {

                String pattern = patterns.get(patternId);

                int startIndex = endIndex - pattern.length() + 1;

                results.add(
                        pattern + " found at index " + startIndex
                );
            }

            temp = temp.outputLink;
        }
    }
}
