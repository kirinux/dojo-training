package org.craftedsw.katas.lsoh.part2;

import org.craftedsw.katas.lsoh.part2.model.Plant;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by 995388 on 24/04/2018.
 *
 * @author 995388
 */
public interface PlantDAO {
    boolean exists(Plant plant);

    Collection<Plant> listAll();

    Plant getPlant(Plant plant);

    void save(Plant plant) throws Exception;

    int getSize();

    void delete(Plant plant);

    Optional<Plant> findByName(String searched);

    List<Plant> findByFamily(String searchedFamily);
}
