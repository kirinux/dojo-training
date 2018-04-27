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
public class PlantH2DbDAO implements PlantDAO {

    @Override
    public boolean exists(Plant plant) {
        return false;
    }

    @Override
    public Collection<Plant> listAll() {
        return null;
    }

    @Override
    public Plant getPlant(Plant plant) {
        return null;
    }

    @Override
    public void save(Plant plant) throws Exception {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void delete(Plant plant) {

    }

    @Override
    public Optional<Plant> findByName(String searched) {
        return null;
    }

    @Override
    public List<Plant> findByFamily(String searchedFamily) {
        return null;
    }
}
