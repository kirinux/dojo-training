package org.craftedsw.katas.logging;

/**
 *
 */
public class Item {

    public String name;

    public int sellIn;

    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public void computeDiscount(double percent) {
        if (sellIn < 0) {
            throw new IllegalStateException("Discount are not allowed on <0 price");
        }
        if (percent > 1 || percent < 0) {
            throw new IllegalArgumentException();
        }
        this.sellIn = (int) (sellIn - (sellIn*percent));
    }

}