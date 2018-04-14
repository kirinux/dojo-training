package org.craftedsw.katas.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class LoggingRose {

    private Logger logger = LoggerFactory.getLogger("plop");
    Item[] items;

    public LoggingRose(Item[] items) {
        this.items = items;
        logger.trace(items.toString());
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].quality > 50) {
                throw new IllegalArgumentException();
            }


            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                        logger.error("Item name " + items[i].name+  " sellin increase to " + items[i].quality);
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    logger.info("plop!");
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        logger.info(String.format("Backstage passe info sellin: %s, quality:%s", items[i].sellIn, items[i].sellIn));
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                                logger.warn("decrease quality of Sulfuras to {}");
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        logger.info("improve quality");
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
            logger.warn("Item after compute: " + items[i]);
            if (items[i].quality > 20 && items[i].sellIn < 10) {
                try {
                    items[i].computeDiscount(1.1);
                } catch (Exception e) {
                    items[i].sellIn=0;
                }
            }

        }
    }

}
