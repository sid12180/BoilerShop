public class Preferences {
    private double budget;
    private double minimumRating;

    public Preferences(double budget, double minimumRating) {
        this.budget = budget;
        this.minimumRating = minimumRating;
    }

    public Preferences(double minimumRating) {
        this.budget = -1;
        this.minimumRating = minimumRating;
    }

    public double getBudget() {
        return budget;
    }

    public double getMinimumRating() {
        return minimumRating;
    }

}