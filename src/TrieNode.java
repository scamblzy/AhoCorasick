import java.util.*;

public class TrieNode {

    Map<Character, TrieNode> children;

    TrieNode failure;

    TrieNode outputLink;

    List<Integer> patternIds;

    public TrieNode() {
        children = new HashMap<>();
        patternIds = new ArrayList<>();
        failure = null;
        outputLink = null;
    }
}
