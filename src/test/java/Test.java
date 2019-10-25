import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

public class Test {
    private Shelter shelter;
    private ArrayList<String> list;
    private ArrayList<String> result;

    @Before
    public void before() {
        shelter = new Shelter();
        ConnectDB.getConnection();
        list = new ArrayList<>();
        result = new ArrayList<>();
        try {
            PreparedStatement pst = ConnectDB.getConnection().prepareStatement("DELETE FROM shelter");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAnimal(Animal animal, String name, int age) throws Exception {
        shelter.add(animal, name, age);
        list.add(animal.toString() + ' ' + name);
    }

    private void pickAnimal() {
        shelter.pickAnimal();
        if (!list.isEmpty()) list.remove(0);
    }

    private void pickAnimal(Animal animal) {
        shelter.pickAnimal(animal);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith(animal.toString())) {
                list.remove(i);
                break;
            }
        }
    }

    @org.junit.Test
    public void testAddAnimalCat() throws Exception {
        addAnimal(Animal.Cat, "Frau", 5);
    }

    @org.junit.Test
    public void testAddAnimalDog() throws Exception {
        addAnimal(Animal.Dog, "Rex", 6);
    }

    @org.junit.Test
    public void testAddAnimalTurtle() throws Exception {
        addAnimal(Animal.Turtle, "Till", 10);
    }

    @org.junit.Test
    public void testAddAllAnimals() throws Exception {
        addAnimal(Animal.Turtle, "Till", 10);
        addAnimal(Animal.Cat, "Frau", 5);
        addAnimal(Animal.Dog, "Rex", 2);
    }

    @org.junit.Test(expected = Exception.class)
    public void testAddEmptyName() throws Exception {
        addAnimal(Animal.Turtle, "", 10);
    }

    @org.junit.Test
    public void testAddManyAnimals() throws Exception {
        Random random = new Random();
        ArrayList<Animal> animals = new ArrayList<>(
                Arrays.asList(Animal.Cat, Animal.Dog, Animal.Turtle));
        for (int i = 0; i < 100; i++) {
            addAnimal(animals.get(random.nextInt(3)), RandomStringUtils.randomAlphabetic(10), random.nextInt(100));

        }
    }

    @org.junit.Test
    public void testEmptyList() throws Exception {

    }

    @org.junit.Test
    public void testPickAnimal() throws Exception {
        addAnimal(Animal.Turtle, "Till", 10);
        addAnimal(Animal.Cat, "Frau", 5);
        addAnimal(Animal.Dog, "Rex", 2);
        pickAnimal();
    }

    @org.junit.Test
    public void testPickWithNoAnimals() throws Exception {
        pickAnimal();
    }

    @org.junit.Test
    public void testPickAnimalCat() throws Exception {
        addAnimal(Animal.Cat, "Frau", 5);
        pickAnimal(Animal.Cat);
    }

    @org.junit.Test
    public void testAddAndPickManyAnimals() throws Exception {
        Random random = new Random();
        ArrayList<Animal> animals = new ArrayList<>(
                Arrays.asList(Animal.Cat, Animal.Dog, Animal.Turtle));
        for (int i = 0; i < 100; i++) {
            addAnimal(animals.get(random.nextInt(3)), RandomStringUtils.randomAlphabetic(10), random.nextInt(100));

        }

        for (int i = 0; i < 10; i++) {
            pickAnimal();
        }
        for (int i = 0; i < 10; i++) {
            pickAnimal(animals.get(random.nextInt(3)));
        }


    }


    @After
    public void after() {
        result.addAll(shelter.getList(Animal.Cat));
        result.addAll(shelter.getList(Animal.Dog));
        result.addAll(shelter.getList(Animal.Turtle));

        Collections.sort(list);

        assertEquals(list, result);
    }


}
