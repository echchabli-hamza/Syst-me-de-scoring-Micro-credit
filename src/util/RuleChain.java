package util;
import java.util.*;


public class RuleChain {
    private Map<List<String>, Integer> discreteRules = new HashMap<>();
    private List<RangeRule> rangeRules = new ArrayList<>();


    public RuleChain addRule(int points, String... path) {
        discreteRules.put(Arrays.asList(path), points);
        return this;
    }


    public RuleChain addRangeRule(int points, double min, Double max) {
        rangeRules.add(new RangeRule(min, max, points));
        return this;
    }


    public int evaluate(String... path) {
        return discreteRules.getOrDefault(Arrays.asList(path), 0);
    }


    public int evaluate(double value) {
        for (RangeRule r : rangeRules) {
            if (r.matches(value)) return r.points;
        }
        return 0;
    }


    public static class RangeRule {
        double min;
        Double max;
        int points;

        RangeRule(double min, Double max, int points) {
            this.min = min;
            this.max = max;
            this.points = points;
        }

        boolean matches(double value) {
            if (value < min) return false;
            if (max != null && value >= max) return false;
            return true;
        }
    }



    public void printRules() {
        System.out.println("=== Discrete Rules ===");
        for (Map.Entry<List<String>, Integer> entry : discreteRules.entrySet()) {
            System.out.println("Path: " + entry.getKey() + " -> Points: " + entry.getValue());
        }

        System.out.println("\n=== Range Rules ===");
        for (RangeRule r : rangeRules) {
            System.out.println("Min: " + r.min + ", Max: " + r.max + " -> Points: " + r.points);
        }
    }

}
