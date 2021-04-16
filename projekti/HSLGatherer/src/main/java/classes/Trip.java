package classes;

import java.util.Comparator;

public class Trip implements Comparable<Trip> {

    private String sign;
    private String departure;
    private String delay;
    private String updated;
    private String route;

    public Trip(String sign, String route) {
        this.sign = sign;
        this.route = route;
    }

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

    public String getDeparture() {
        return this.departure; 
    }

    public String getDelay() {
        return this.delay;
    }

    public String getUpdated() {
        return this.updated;
    }

    public String getRoute() {
        return this.route;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true;
		}

        if (!(o instanceof Trip)) { 
            return false;
		}

        Trip comp = (Trip) o;

        if (this.sign.equals(comp.sign) && this.route.equals(comp.route)) { 
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17 * sign.hashCode();
        result = result + 17 * route.hashCode();

        return result;
    }

    @Override
    public int compareTo(Trip o) {
        return Comparator.comparing(Trip::getSign)
                         .thenComparing(Trip::getRoute)
                         .compare(this, o);
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
