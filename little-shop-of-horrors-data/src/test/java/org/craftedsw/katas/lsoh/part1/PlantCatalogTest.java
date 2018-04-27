package org.craftedsw.katas.lsoh.part1;

import org.assertj.core.api.SoftAssertions;
import org.craftedsw.katas.lsoh.part1.model.Plant;
import org.craftedsw.katas.lsoh.part1.model.PlantCatalog;
import org.craftedsw.katas.lsoh.part2.PlantFileDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 *
 */
public class PlantCatalogTest {


    @Test
    public void add_new_plant_should_make_it_available_in_catalog() {
        PlantCatalog catalog = new PlantCatalog();
        int initialSize = catalog.getSize();
        Plant plant = new Plant("name", "longName", "family", 10);
        catalog.addPlant(plant);
        int size = catalog.getSize();
        Plant retrieved = catalog.getPlant(plant);

        assertThat(size).isEqualTo(initialSize + 1);
        assertThat(retrieved).isEqualTo(plant);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_existing_plant_should_throw_exception() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.anyString());
        PlantCatalog catalog = new PlantCatalog();
        Plant plant = new Plant("name", "longName", "family", 10);
        catalog.addPlant(plant);
        catalog.addPlant(plant);
    }

    //http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#exception-assertion
    @Test
    public void add_incomplete_data_for_plant_should_throw_exception() {
        PlantCatalog catalog = new PlantCatalog();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> catalog.addPlant(new Plant("", "longName", "family", 10))).as("check plant name").isInstanceOf(IllegalArgumentException.class).hasMessageContaining("incomplete");
            softly.assertThatThrownBy(() -> catalog.addPlant(new Plant("name", "longName", "", 10))).as("check plant family").isInstanceOf(IllegalArgumentException.class).hasMessageContaining("incomplete");
            softly.assertThatThrownBy(() -> catalog.addPlant(new Plant("name", "longName", "family", -10))).as("check plant price").isInstanceOf(IllegalArgumentException.class).hasMessageContaining("incomplete");
        });
    }

    @Test
    public void remove_existing_plant_should_make_it_unavailable() {
        PlantCatalog catalog = new PlantCatalog();
        int initialSize = catalog.getSize();

        Plant plant = new Plant("name", "longName", "family", 10);
        catalog.addPlant(plant);

        catalog.removePlant(plant);
        int size = catalog.getSize();
        Plant retrieved = catalog.getPlant(plant);

        assertThat(size).isEqualTo(initialSize);
        assertThat(retrieved).isNull();
    }

    @Test
    public void remove_unknown_plant_should_throw_exception() {
        PlantCatalog catalog = new PlantCatalog();
        Plant plant = new Plant("name", "longName", "family", 10);
        assertThatIllegalArgumentException().as("plant does not exists in catalog").isThrownBy(() -> catalog.removePlant(plant)).withMessageContaining("not").withMessageContaining("exists");
    }

    @Test
    public void given_exact_existing_plant_name_then_search_by_name_should_return_one_plant() {
        //use of builder design pattern, should be used in other tests
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog()
                .withPlant("searched", "", "family", 10)
                .withNRandomPlant(4)
                .build();
        Optional<Plant> searched = catalog.searchPlantByName("searched");
        assertThat(searched).as("Plant not found").isNotNull();
        assertThat(searched).as("Plant not found").isPresent();
        assertThat(searched.get().getName()).as("Plant name").isEqualTo("searched");
        assertThat(searched.get().getPrice()).as("Plant price").isEqualTo(10);

    }

    @Test
    public void given_exact_non_existing_plant_name_then_search_by_name_should_return_empty_result() {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog()
                .withNRandomPlant(10)
                .build();

        Optional<Plant> searched = catalog.searchPlantByName("searched");
        assertThat(searched).as("Optional not null").isNotNull();
        assertThat(searched).as("Optional is not present").isNotPresent();

        Plant searchedPlant = catalog.searchPlantByName("searched").orElse(null);
        assertThat(searchedPlant).as("Optional not null").isNull();
    }


    @Test
    public void given_plant_family_then_return_all_plant_for_it() {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog()
                .withNRandomPlant(10)
                .withNFamilyPlant(3, "searchedFamily")
                .build();

        List<Plant> plants = catalog.searchPlantByFamily("searchedFamily");

        assertThat(plants).as("List is empty").isNotEmpty();
        assertThat(plants.size()).as("List size does not match").isEqualTo(3);
    }


    @Test
    public void given_unknown_plant_family_then_return_empty_result() {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog()
                .withNRandomPlant(10)
                .withNFamilyPlant(3, "searchedFamily")
                .build();

        List<Plant> plants = catalog.searchPlantByFamily("plop");

        assertThat(plants).as("List is empty").isEmpty();
        assertThat(plants.size()).as("List size does not match").isEqualTo(0);
    }

    @Test
    public void get_best_values_plant_should_return_non_empty_list() {
        PlantCatalog catalog = CatalogBuilder.givenPlantCatalog()
                .withNRangedPRicePlant(1, 100)
                .build();

        List<Plant> plants = catalog.getBestValues(50);

        assertThat(plants).as("List is empty").isNotEmpty();
        assertThat(plants).as("List size does not match")
                .filteredOn(plant -> plant.getPrice() < 50).size().isEqualTo(49);

    }


}
