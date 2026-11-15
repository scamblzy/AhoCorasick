import java.util.*;

public class Testing {

    public static void main(String[] args) {

        String text = "ushershehishe";

        List<String> patterns = Arrays.asList(
                "he",
                "she",
                "his",
                "hers"
        );

        // Aho-Corasick
        AhoCorasick aho = new AhoCorasick();

        for (int i = 0; i < patterns.size(); i++) {
            aho.addPattern(patterns.get(i), i);
        }

        aho.buildFailureLinks();

        List<String> ahoResults = aho.search(text, patterns);

        // KMP
        List<String> kmpResults = KMP.searchAll(text, patterns);

        // Sort results for comparison
        Collections.sort(ahoResults);
        Collections.sort(kmpResults);

        System.out.println("Aho-Corasick results:");
        for (String result : ahoResults) {
            System.out.println(result);
        }

        System.out.println("\nKMP results:");
        for (String result : kmpResults) {
            System.out.println(result);
        }

        System.out.println("\nResults are equal: "
                + ahoResults.equals(kmpResults));
    }
}
