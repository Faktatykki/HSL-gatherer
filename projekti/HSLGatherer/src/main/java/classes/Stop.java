package classes;

public class Stop implements Comparable<Stop> {

    private String name;

    public Stop(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;

        if(o == null) return false;

        if(o.getClass() != this.getClass()) return false;

        Stop stop = (Stop) o;

        if(this.name.equals(stop.name)) return true;

        return false;
    }

    @Override
    public int compareTo(Stop o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        int result = 17 * name.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return this.name;
    }
}