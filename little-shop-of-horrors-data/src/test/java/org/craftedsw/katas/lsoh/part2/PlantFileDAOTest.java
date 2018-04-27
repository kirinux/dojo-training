package org.craftedsw.katas.lsoh.part2;

import org.craftedsw.katas.lsoh.part2.model.Plant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Matchers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


public class PlantFileDAOTest {

    private final String PLANT_LIST_FILE_NAME = "plants.list";
    private final String PLANT_LIST_ORIGINAL_FILE_NAME = "plants.list.original";
    private final String PLANT_LIST_ORIGINAL_FILE_PATH = "./src/test/resources/" + PLANT_LIST_ORIGINAL_FILE_NAME;

    private PlantDAO createDAO() {
        try {
            return new PlantFileDAO(preparePlantListFile());
        } catch (Exception e) {
            fail("Impossible to create DAO", e);
        }
        return null;
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File preparePlantListFile() {
        try {
            File plantsList = folder.newFile(PLANT_LIST_FILE_NAME);
            Files.copy(Paths.get(PLANT_LIST_ORIGINAL_FILE_PATH),
                    Paths.get(plantsList.toURI()), StandardCopyOption.REPLACE_EXISTING);
            return plantsList;
        } catch (Exception e) {
            fail("impossible to prepare plant list file", e);
        }
        return null;
    }

    //just to show you can test code which is not supposed to throw exceptions
    @Test
    public void should_not_throw_any_exception_if_file_exists() {
        assertThatCode(() -> {
            createDAO();
        }).doesNotThrowAnyException();
    }

    @Test
    public void should_throw_exception_if_file_does_not_exists() {
        assertThatThrownBy(() -> {
            new PlantFileDAO(new File("plop.list"));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("file")
                .hasMessageContaining("not found");
    }

    @Test
    public void should_have_a_positive_size_after_reading_catalog_from_file() {
        assertThatCode(() -> {
            PlantDAO dao = createDAO();
            int size = dao.getSize();
            assertThat(size).isGreaterThan(0);
        }).doesNotThrowAnyException();
    }

    @Test
    public void save_new_plant_should_make_it_available() throws Exception {
        PlantDAO dao = createDAO();
        int initialSize = dao.getSize();
        Plant plant = PlantFactory.createDefaultPlant();
        Plant init = dao.getPlant(plant);
        assertThat(init).isNull();
        dao.save(plant);
        Plant retrieved = dao.getPlant(plant);
        int size = dao.getSize();

        assertThat(size).isEqualTo(initialSize + 1);
        assertThat(retrieved).isEqualTo(plant);
    }

    @Test
    public void save_new_plant_should_make_it_persistent() throws Exception {
        File plantsList = preparePlantListFile();

        PlantDAO dao = new PlantFileDAO(plantsList);
        Plant plant = PlantFactory.createDefaultPlant();
        Plant init = dao.getPlant(plant);
        assertThat(init).isNull();
        dao.save(plant);
        Plant retrieved = dao.getPlant(plant);
        assertThat(retrieved).isEqualTo(plant);

        dao = new PlantFileDAO(plantsList);
        retrieved = dao.getPlant(plant);
        assertThat(retrieved).isEqualTo(plant);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_existing_plant_should_throw_exception() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.anyString());
        PlantDAO dao = createDAO();
        Plant plant = PlantFactory.createDefaultPlant();
        dao.save(plant);
        dao.save(plant);
    }


    @Test
    public void remove_existing_plant_should_make_it_unavailable() throws Exception {
        PlantDAO dao = createDAO();
        int initialSize = dao.getSize();

        Plant plant = PlantFactory.createDefaultPlant();
        dao.save(plant);

        dao.delete(plant);
        int size = dao.getSize();
        Plant retrieved = dao.getPlant(plant);

        assertThat(size).isEqualTo(initialSize);
        assertThat(retrieved).isNull();
    }

    @Test
    public void remove_existing_plant_should_make_it_unavailable_persistent() throws Exception {
        //implements the re read from the file to verify the line have been deleted
    }


    @Test
    public void remove_unknown_plant_should_throw_exception() {
        PlantDAO dao = createDAO();
        Plant plant = PlantFactory.createDefaultPlant();
        assertThatIllegalArgumentException().as("plant does not exists in catalog").isThrownBy(() -> dao.delete(plant)).withMessageContaining("not").withMessageContaining("exists");
    }

    @Test
    public void given_exact_existing_plant_name_then_search_by_name_should_return_one_plant() throws Exception {
        //use of builder design pattern, should be used in other tests
        PlantDAO dao = createDAO();
        dao.save(new Plant("searched", "", "fa", 1));
        Optional<Plant> searched = dao.findByName("searched");
        assertThat(searched).as("Plant not found").isNotNull();
        assertThat(searched).as("Plant not found").isPresent();
        assertThat(searched.get().getName()).as("Plant name").isEqualTo("searched");
        assertThat(searched.get().getPrice()).as("Plant price").isEqualTo(1);

    }

    @Test
    public void given_exact_non_existing_plant_name_then_search_by_name_should_return_empty_result() {
        PlantDAO dao = createDAO();

        Optional<Plant> searched = dao.findByName("searched");
        assertThat(searched).as("Optional not null").isNotNull();
        assertThat(searched).as("Optional is not present").isNotPresent();
        //show some Optionnal use
        Plant searchedPlant = dao.findByName("searched").orElse(null);
        assertThat(searchedPlant).as("Optional not null").isNull();
    }


    @Test
    public void given_plant_family_then_return_all_plant_for_it() throws Exception {
        PlantDAO dao = createDAO();

        for (int i = 0; i<5; i++) {
            dao.save(new Plant("name"+i, "", "searchedFamily", 1));
        }

        List<Plant> plants = dao.findByFamily("searchedFamily");

        assertThat(plants).as("List is empty").isNotEmpty();
        assertThat(plants.size()).as("List size does not match").isEqualTo(5);
    }


    @Test
    public void given_unknown_plant_family_then_return_empty_result() throws Exception {
        PlantDAO dao = createDAO();

        for (int i = 0; i<5; i++) {
            dao.save(new Plant("name"+i, "", "searchedFamily", 1));
        }

        List<Plant> plants = dao.findByFamily("plop");

        assertThat(plants).as("List is empty").isEmpty();
        assertThat(plants.size()).as("List size does not match").isEqualTo(0);
    }


}
