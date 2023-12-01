package data;

public class Resource {
    public ResourceData data;
    public Support support;

    public Resource(int id, String name, int year, String color, String pantone_value) {
        this.data = new ResourceData(id, name, year, color, pantone_value);
        this.support = new Support();
    }

    @Override
    public String toString() {
        return "Resource: " +
                data +
                ",\n  " + support;
    }
}
