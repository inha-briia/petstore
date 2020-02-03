package com.petstore;

import com.petstore.controllers.PetsController;
import com.petstore.domain.Category;
import com.petstore.domain.Pet;
import com.petstore.domain.Status;
import com.petstore.domain.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * Created by inhab on 3/2/2020.
 */

public class PetTests {
    private static final String PHOTO_URL = "https://www.tesco.ie/groceries/MarketingContent/Sites/Retail/superstore/Online/P/i/departments/2016/Pets/1BC.jpg";
    PetsController petsController;
    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(10))
            .withName("My pet")
            .withPhotoUrls(Collections.singletonList(PHOTO_URL))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "golden-retriever")))
            .inCategory(new Category(1, "dogs")).build();

    @BeforeClass
    public void beforeClass() {
        petsController = new PetsController();
    }

    @Test(priority = 0)
    public void addNewPet() {
        Pet petResponse = petsController.addNewPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 1)
    public void verifyNewPet() {
        Pet petResponse = petsController.findPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 2)
    public void updatePet() {
        pet.setName("New name for my pet");
        Pet petResponse = petsController.updatePet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 3)
    public void verifyUpdatedPet() {
        Pet petResponse = petsController.findPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 4)
    public void deletePetAndDoCheck() {
        petsController.deletePet(pet);
        petsController.verifyPetDeleted(pet);
    }
}
