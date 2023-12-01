package data;

public class ResourceData {
    public int id;
    public String name;
    public int year;
    public String color;
    public String pantone_value;

    public ResourceData(int id, String name, int year, String color, String pantone_value) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value = pantone_value;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + "', year=" + year +
                ", color='" + color + "', pantone_value='" + pantone_value + "'";
    }
}
