package org.craftedsw.katas.lsoh.part2;

import org.assertj.core.api.SoftAssertions;
import org.craftedsw.katas.lsoh.part2.model.Plant;
import org.craftedsw.katas.lsoh.part2.model.PlantCatalog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PlantCatalogTest {


    @Mock
    private PlantDAO dao;

    //http://www.vogella.com/tutorials/Mockito/article.html
    @Test
    public void verify_catalog_add_use_dao_layer() throws Exception {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog(dao).build();
        Plant plant = PlantFactory.createDefaultPlant();

        Mockito.when(dao.getPlant(plant)).thenReturn(plant);
        catalog.addPlant(plant);
        Plant retrieved = catalog.getPlant(plant);

        assertThat(retrieved).isEqualTo(plant);
        Mockito.verify(dao, Mockito.times(1)).save(plant);
        Mockito.verify(dao, Mockito.times(1)).getPlant(plant);
    }


    //http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#exception-assertion
    @Test
    public void add_incomplete_data_for_plant_should_throw_exception() {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog(dao).build();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> catalog.addPlant(new Plant("", "longName", "family", 10))).as("check plant name").isInstanceOf(IllegalArgumentException.class).hasMessageContaining("incomplete");
            softly.assertThatThrownBy(() -> catalog.addPlant(new Plant("name", "longName", "", 10))).as("check plant family").isInstanceOf(IllegalArgumentException.class).hasMessageContaining("incomplete");
            softly.assertThatThrownBy(() -> catalog.addPlant(new Plant("name", "longName", "family", -10))).as("check plant price").isInstanceOf(IllegalArgumentException.class).hasMessageContaining("incomplete");
        });
    }

    @Test
    public void verify_catalog_delete_use_dao_layer() throws Exception {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog(dao).build();
        Plant plant = PlantFactory.createDefaultPlant();
        Mockito.when(dao.exists(Mockito.any(Plant.class))).thenReturn(true);
        catalog.removePlant(plant);
        Mockito.verify(dao, Mockito.times(1)).delete(plant);
    }


    @Test
    public void get_best_values_plant_should_return_non_empty_list() {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog(dao).build();
        final List<Plant> list = PlantFactory.createPlantList(100);

        Mockito.when(dao.listAll()).thenReturn(list);
        List<Plant> plants = catalog.getBestValues(50);

        assertThat(plants).as("List is empty").isNotEmpty();
        assertThat(plants).as("List size does not match")
                .filteredOn(plant -> plant.getPrice() < 50).size().isEqualTo(50);

    }

}
