package org.craftedsw.katas.lsoh.part2.model;

import org.craftedsw.katas.lsoh.part2.PlantDAO;
import org.craftedsw.katas.lsoh.part2.PlantFileDAO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Step1: </b> PlantCatalog should use only a {@link PlantFileDAO} so the catalog could use <br>
 * a File in argument to reference the plants.list file<br>
 * <p>
 * <b>Step2</b>: We start to think about DI and abstraction, so the PlantFileDAO had a new interface PlantDAO<br>
 * and the Catalog could use a PlantDAO (constructor injection)
 * <p>
 * <b>Step3:</b> Use Spring DI, and the DAO is autowired
 */
public class PlantCatalog {


    private PlantDAO dao;

    public PlantCatalog(PlantDAO dao) {
        this.dao = dao;
    }

    private boolean isComplete(Plant plant) {
        if (plant.getName() == null || plant.getName().isEmpty()
                || plant.getFamily() == null || plant.getFamily().isEmpty() ||
                plant.getPrice() < 0) {
            return false;
        }
        return true;
    }

    private boolean exists(Plant plant) {
        return dao.exists(plant);
    }


    public int getSize() {
        return dao.getSize();
    }

    public void addPlant(Plant plant) throws Exception {
        if (exists(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s already exist", plant));
        }

        if (!isComplete(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s is not correctly defined: incomplete", plant));
        }
        dao.save(plant);
    }

    public Plant getPlant(Plant plant) {
        return dao.getPlant(plant);
    }

    public void removePlant(Plant plant) {
        if (!dao.exists(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s does not exists", plant));
        }
        dao.delete(plant);
    }

    //version < JDK8
//    public Plant searchPlantByName(String searched) {
//        return plantMap.get(searched);
//    }

    public Optional<Plant> searchPlantByName(String searched) {
        return dao.findByName(searched);
    }

    public List<Plant> searchPlantByFamily(String searchedFamily) {
        return dao.findByFamily(searchedFamily);
    }

    public List<Plant> getBestValues(int maxPrice) {
        return dao.listAll().stream()
                .filter(plant -> plant.getPrice() < maxPrice)
                .sorted(Comparator.comparing(Plant::getPrice))
                .collect(Collectors.toList());
    }
}
