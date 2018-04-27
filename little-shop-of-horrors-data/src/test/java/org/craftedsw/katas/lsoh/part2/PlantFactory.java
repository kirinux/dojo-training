package org.craftedsw.katas.lsoh.part2;

import org.craftedsw.katas.lsoh.part2.model.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class PlantFactory {

    private static final Plant DEFAULT_PLANT = new Plant("name", "longName", "family", 10);

    private PlantFactory() {
    }


    public static Plant createDefaultPlant() {
        return new Plant("name", "longName", "family", 10);
    }

    public static List<Plant> createPlantList(int size) {
        final ArrayList<Plant> list = new ArrayList<>();
        Stream.iterate(0, i -> i + 1).limit(size).forEach((i) -> {
            try {
                list.add(new Plant("name" + i, "longName" + i, "family"+i, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return list;
    }

}
