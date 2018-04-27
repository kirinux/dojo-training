package org.craftedsw.katas.lsoh.part1.model;

/**
 * Created by 995388 on 21/04/2018.
 *
 * @author 995388
 */
public class Plant {


    private final String name;
    private final String longName;
    private final String family;
    private final double price;

    public Plant(String name, String longName, String family, double price) {
        this.name = name;
        this.longName = longName;
        this.family = family;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getLongName() {
        return longName;
    }

    public String getFamily() {
        return family;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plant plant = (Plant) o;

        return name.equals(plant.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", longName='" + longName + '\'' +
                ", family='" + family + '\'' +
                ", price=" + price +
                '}';
    }
}
