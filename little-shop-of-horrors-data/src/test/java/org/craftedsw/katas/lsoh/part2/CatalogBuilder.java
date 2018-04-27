package org.craftedsw.katas.lsoh.part2;

import org.craftedsw.katas.lsoh.part2.model.Plant;
import org.craftedsw.katas.lsoh.part2.model.PlantCatalog;

import java.util.stream.Stream;

/**
 * Created by 995388 on 21/04/2018.
 *
 * @author 995388
 */
public class CatalogBuilder {

    private PlantCatalog catalog;
    private int currentIndex = 0;


    private CatalogBuilder(PlantDAO dao) {
        catalog = new PlantCatalog(dao);
    }


    public static final CatalogBuilder givenPlantCatalog(PlantDAO dao) {
        return new CatalogBuilder(dao);
    }


    public CatalogBuilder withPlant(String name, String longName, String family, double price) {
        try {
            catalog.addPlant(new Plant(name, longName, family, price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public CatalogBuilder withNRandomPlant(int count) {
        Stream.iterate(currentIndex, i -> i + 1 + (currentIndex++)).limit(count).forEach((i) -> {
            try {
                catalog.addPlant(new Plant("name" + i, "longName" + i, "family" + i, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return this;
    }
    public CatalogBuilder withNFamilyPlant(int count, String family) {
        Stream.iterate(currentIndex, i -> i + 1 + (currentIndex++)).limit(count).forEach((i) -> {
            try {
                catalog.addPlant(new Plant("name" + i, "longName" + i, family, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return this;
    }

    public CatalogBuilder withNRangedPRicePlant(int start, int end) {
        for (int i = start; i < end ; i++) {
            try {
                catalog.addPlant(new Plant("name"+currentIndex, "longName"+currentIndex, "family"+currentIndex, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentIndex++;
        }
        return this;
    }


    public PlantCatalog build() {
        return catalog;
    }


}
