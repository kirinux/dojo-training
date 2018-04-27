package org.craftedsw.katas.lsoh.part1.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class PlantCatalog {


    private final Map<String, Plant> plantMap = new HashMap<>();

    private boolean isComplete(Plant plant) {
        if (plant.getName() == null || plant.getName().isEmpty()
                || plant.getFamily() == null || plant.getFamily().isEmpty() ||
                plant.getPrice() < 0) {
            return false;
        }
        return true;
    }

    private boolean exists(Plant plant) {
        return plantMap.containsKey(plant.getName());
    }


    public int getSize() {
        return plantMap.size();
    }

    public void addPlant(Plant plant) {
        if (exists(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s already exist", plant));
        }

        if (!isComplete(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s is not correctly defined: incomplete", plant));
        }
        plantMap.put(plant.getName(), plant);
    }

    public Plant getPlant(Plant plant) {
        return plantMap.get(plant.getName());
    }

    public void removePlant(Plant plant) {
        if (!exists(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s does not exists", plant));
        }
        plantMap.remove(plant.getName());
    }

    //version < JDK8
//    public Plant searchPlantByName(String searched) {
//        return plantMap.get(searched);
//    }

    public Optional<Plant> searchPlantByName(String searched) {
        return Optional.ofNullable(plantMap.get(searched));
    }

    public List<Plant> searchPlantByFamily(String searchedFamily) {
        return plantMap.values().stream().filter(plant -> plant.getFamily().equals(searchedFamily)).collect(Collectors.toList());
    }

    public List<Plant> getBestValues(int maxPrice) {
        return plantMap.values().stream().filter(plant -> plant.getPrice() < maxPrice).sorted(Comparator.comparing(Plant::getPrice)).collect(Collectors.toList());
    }
}
