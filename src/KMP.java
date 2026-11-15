import java.util.*;

public class KMP {

    // Build LPS (Longest Prefix Suffix) array
    private static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {

                    length = lps[length - 1];

                } else {

                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // Search for all occurrences of one pattern
    public static List<Integer> search(String text, String pattern) {

        List<Integer> occurrences = new ArrayList<>();

        if (pattern.isEmpty()) {
            return occurrences;
        }

        int[] lps = buildLPS(pattern);

        int i = 0; // Text index
        int j = 0; // Pattern index

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;
            }

            if (j == pattern.length()) {

                occurrences.add(i - j);

                j = lps[j - 1];

            } else if (i < text.length()
                    && text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {

                    j = lps[j - 1];

                } else {

                    i++;
                }
            }
        }

        return occurrences;
    }

    // Run KMP separately for every pattern
    public static List<String> searchAll(
            String text,
            List<String> patterns) {

        List<String> results = new ArrayList<>();

        for (String pattern : patterns) {

            List<Integer> positions = search(text, pattern);

            for (int position : positions) {

                results.add(
                        pattern + " found at index " + position
                );
            }
        }

        return results;
    }
}
