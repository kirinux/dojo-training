package org.craftedsw.katas.lsoh.part2;

import org.craftedsw.katas.lsoh.part2.model.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class PlantFileDAO implements PlantDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlantFileDAO.class);
    private final Map<String, Plant> plantMapCache;
    private static final String PLANT_FIELD_LINE_SEPARATOR = "/";
    private final String PLANT_FILE_COMMENT = "#";


    private File plantsFile;


    private Plant createPlant(String str) {
        String[] split = str.split(PLANT_FIELD_LINE_SEPARATOR);
        return new Plant(split[0], split[1], split[2], Double.parseDouble(split[3]));
    }


    @Override
    public boolean exists(Plant plant) {
        return plantMapCache.containsKey(plant.getName());
    }

    public PlantFileDAO(File plantsFile) throws URISyntaxException, IOException {
        if (plantsFile == null || !plantsFile.exists() || !plantsFile.canRead()) {
            throw new IllegalArgumentException(String.format("Resource file %s not found or not readable", plantsFile));
        }
        this.plantsFile = plantsFile;
        Path path = Paths.get(plantsFile.toURI());

        plantMapCache = Files.lines(path)
                .filter(str -> !str.startsWith(PLANT_FILE_COMMENT))
                .filter(str -> !str.isEmpty())
                .filter(str -> str.split(PLANT_FIELD_LINE_SEPARATOR).length == 4)
                .collect(Collectors.toMap((k) -> k.split(PLANT_FIELD_LINE_SEPARATOR)[0], (str) -> createPlant(str)));

        LOGGER.debug("Loaded file " + plantMapCache);
    }

    /**
     *
     * @return an unmodifiable list of the plants
     */
    @Override
    public Collection<Plant> listAll() {
        return Collections.unmodifiableCollection(plantMapCache.values());
    }

    @Override
    public Plant getPlant(Plant plant) {
        return plantMapCache.get(plant.getName());
    }

    @Override
    public void save(Plant plant) throws Exception {
        if (exists(plant)) {
            throw new IllegalArgumentException(String.format("Plant %s already exists", plant));
        }
        plantMapCache.put(plant.getName(), plant);
        saveToFile();
    }

    private void saveToFile() throws URISyntaxException, IOException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(plantsFile.toURI())));) {
            plantMapCache.values().stream().forEach(plant -> writer.write(String.format("%s/%s/%s/%s\n", plant.getName(), plant.getLongName(), plant.getFamily(), plant.getPrice())));
        }

    }

    @Override
    public int getSize() {
        return plantMapCache.size();
    }

    @Override
    public void delete(Plant plant) {
        if (!exists(plant)) {
            throw new IllegalArgumentException(String.format("Plan %s does not exists", plant));
        }
        plantMapCache.remove(plant.getName());
    }

    @Override
    public Optional<Plant> findByName(String searched) {
        return Optional.ofNullable(plantMapCache.get(searched));
    }

    @Override
    public List<Plant> findByFamily(String searchedFamily) {
        return plantMapCache.values().stream().filter(plant -> plant.getFamily().equals(searchedFamily)).collect(Collectors.toList());
    }
}
