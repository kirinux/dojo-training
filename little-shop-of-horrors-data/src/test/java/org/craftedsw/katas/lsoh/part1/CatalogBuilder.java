package org.craftedsw.katas.lsoh.part1;

import org.craftedsw.katas.lsoh.part1.model.Plant;
import org.craftedsw.katas.lsoh.part1.model.PlantCatalog;

import java.util.stream.Stream;

/**
 * Created by 995388 on 21/04/2018.
 *
 * @author 995388
 */
public class CatalogBuilder {

    private CatalogBuilder builder;
    private PlantCatalog catalog;
    private int currentIndex = 0;

    private CatalogBuilder() {
        catalog = new PlantCatalog();
    }


    public static final CatalogBuilder givenPlantCatalog() {
        return new CatalogBuilder();
    }


    public CatalogBuilder withPlant(String name, String longName, String family, double price) {
        catalog.addPlant(new Plant(name, longName, family, price));
        return this;
    }

    public CatalogBuilder withNRandomPlant(int count) {
        Stream.iterate(currentIndex, i -> i + 1 + (currentIndex++)).limit(count).forEach((i) -> catalog.addPlant(new Plant("name"+i, "longName"+i, "family"+i, i)));
        return this;
    }
    public CatalogBuilder withNFamilyPlant(int count, String family) {
        Stream.iterate(currentIndex, i -> i + 1 + (currentIndex++)).limit(count).forEach((i) -> catalog.addPlant(new Plant("name"+i, "longName"+i, family, i)));
        return this;
    }

    public CatalogBuilder withNRangedPRicePlant(int start, int end) {
        for (int i = start; i < end ; i++) {
            catalog.addPlant(new Plant("name"+currentIndex, "longName"+currentIndex, "family"+currentIndex, i));
            currentIndex++;
        }
        return this;
    }


    public PlantCatalog build() {
        return catalog;
    }


}
