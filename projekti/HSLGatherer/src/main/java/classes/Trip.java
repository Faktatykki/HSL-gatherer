package classes;

public class Trip {

    private String sign;
    private String route;

    public Trip(String sign, String route) {
        this.sign = sign;
        this.route = route;
    }

    public String getSign() {
        return this.sign;
    }

    public String getRoute() {
        return this.route;
    }
}
