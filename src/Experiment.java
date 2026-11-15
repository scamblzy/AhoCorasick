
import java.util.*;
import java.io.*;

public class Experiment {

    private static final int REPETITIONS = 5;

    private static final int[] PATTERN_COUNTS = {
            10, 100, 1000
    };

    private static final int[] TEXT_LENGTHS = {
            1000, 10000, 100000
    };

    private static final Random random = new Random(42);

    // Generate random text
    private static String generateText(int length) {

        StringBuilder text = new StringBuilder();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < length; i++) {
            text.append(alphabet.charAt(
                    random.nextInt(alphabet.length())
            ));
        }

        return text.toString();
    }

    // Generate random patterns
    private static List<String> generatePatterns(int count) {

        List<String> patterns = new ArrayList<>();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < count; i++) {

            int length = 3 + random.nextInt(8);

            StringBuilder pattern = new StringBuilder();

            for (int j = 0; j < length; j++) {
                pattern.append(alphabet.charAt(
                        random.nextInt(alphabet.length())
                ));
            }

            patterns.add(pattern.toString());
        }

        return patterns;
    }

    // Measure Aho-Corasick execution time
    private static long measureAhoCorasick(
            String text,
            List<String> patterns) {

        long start = System.nanoTime();

        AhoCorasick aho = new AhoCorasick();

        for (int i = 0; i < patterns.size(); i++) {
            aho.addPattern(patterns.get(i), i);
        }

        aho.buildFailureLinks();
        aho.search(text, patterns);

        return System.nanoTime() - start;
    }

    // Measure KMP execution time
    private static long measureKMP(
            String text,
            List<String> patterns) {

        long start = System.nanoTime();

        KMP.searchAll(text, patterns);

        return System.nanoTime() - start;
    }

    // Run all experiments and save results to CSV
    public static void runExperiments() {

        String fileName = "results.csv";

        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(fileName)))) {

            writer.println(
                    "Patterns,Text Length,Aho-Corasick (ms),KMP (ms)"
            );

            System.out.printf(
                    "%-12s %-15s %-22s %-15s%n",
                    "Patterns",
                    "Text Length",
                    "Aho-Corasick (ms)",
                    "KMP (ms)"
            );

            for (int patternCount : PATTERN_COUNTS) {

                for (int textLength : TEXT_LENGTHS) {

                    String text = generateText(textLength);

                    List<String> patterns =
                            generatePatterns(patternCount);

                    // Warm-up
                    measureAhoCorasick(text, patterns);
                    measureKMP(text, patterns);

                    long ahoTotal = 0;
                    long kmpTotal = 0;

                    for (int i = 0; i < REPETITIONS; i++) {

                        ahoTotal += measureAhoCorasick(
                                text, patterns
                        );

                        kmpTotal += measureKMP(
                                text, patterns
                        );
                    }

                    double ahoAverage =
                            ahoTotal / (double) REPETITIONS
                                    / 1_000_000.0;

                    double kmpAverage =
                            kmpTotal / (double) REPETITIONS
                                    / 1_000_000.0;

                    System.out.printf(
                            "%-12d %-15d %-22.4f %-15.4f%n",
                            patternCount,
                            textLength,
                            ahoAverage,
                            kmpAverage
                    );

                    writer.printf(
                            Locale.US,
                            "%d,%d,%.4f,%.4f%n",
                            patternCount,
                            textLength,
                            ahoAverage,
                            kmpAverage
                    );
                }
            }

            System.out.println(
                    "\nResults saved to " + fileName
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving results: " + e.getMessage()
            );
        }
    }
}
