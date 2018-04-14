package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {


    @Test
    public void updateQuality_not_famous_band_quality_and_selin_price_decrease_together() {
        run(new Item("plop", 2, 10), 9, 1);
    }

    @Test
    public void updateQuality_Aged_brie_quailty_increase_as_sellin_price_decrease() {
        run(new Item("Aged Brie", 2, 10), 11, 1);
    }


    @Test
    public void updateQuality_not_famous_band_quality_decrease_twice_as_sellin_price_decrease() {
        run(new Item("plop", 0, 10), 8, -1);
    }


    @Test
    public void updateQuality_backstage_pass_price_decrease_and_quality_stay() {
        run(new Item("Backstage passes to a TAFKAL80ETC concert", 2, 60), 60, 1);
    }

    @Test
    public void updateQuality_sulfuras_should_do_nothing() {
        run(new Item("Sulfuras, Hand of Ragnaros", 2, 60), 60, 2);
    }

    @Test
    public void updateQuality_medium_quality_backstage_pass_quality_increase_as_sellin_price_decrease() {
        run(new Item("Backstage passes to a TAFKAL80ETC concert", 12, 40),
                41, 11);
    }

    @Test
    public void updateQuality_low_quality_backstage_pass_quality_increse_twice_as_sellin_price_decrease() {
        run(new Item("Backstage passes to a TAFKAL80ETC concert", 9, 35),
                37, 8);
    }

    @Test
    public void updateQuality_quality_backstage_pass_quality_increse_thrid_as_sellin_price_decrease() {
        run(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 35),
                38, 4);
    }

    @Test
    public void updateQuality_quality_become_zero_if_sellin_price_is_zero() {
        run(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 35),
                0, -1);
    }

    @Test
    public void updateQuality_aged_brie_quality_increase_as_sellin_price_decrease() {
        run(new Item("Aged Brie", 0, 35),
                37, -1);
    }


    private void run(Item item, int expectedQuality, int expectedSellin) {
        Item[] items = new Item[]{item};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Expected quality value is false", expectedQuality, app.items[0].quality);
        assertEquals("Expected sellin value is false", expectedSellin, app.items[0].sellIn);
    }


}
