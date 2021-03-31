package classes;

public class Trip {

    private String sign;
    private String departure;
    private String delay;
    private String updated;
    private String route;

    public Trip(String sign, String departure, String delay, String updated, String route) {
        this.sign = sign;
        this.departure = departure;
        this.delay = delay;
        this.updated = updated;
        this.route = route;
    }

    public String getSign() {
        return this.sign;
    }

    public String getDeparture() { return this.departure; }

    public String getDelay() { return this.delay; }

    public String getUpdated() { return this.updated; }

    public String getRoute() {
        return this.route;
    }

    @Override
    public String toString() {
        return "Sign: " + this.sign + " | " +
                "Route: " + this.route + " | " +
                "Departure: " + this.departure + " | " +
                "Delay: " + this.delay + " | " +
                "Updated: " + this.updated;

    }
}
