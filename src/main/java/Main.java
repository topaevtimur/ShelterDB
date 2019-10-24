public class Main {



    public static void main(String[] argv) {
            Shelter shelter = new Shelter();
//            shelter.add(Animal.Dog, "1", 4);
//            shelter.add(Animal.Dog, "2", 5);
//            shelter.add(Animal.Dog, "3", 6);
//            shelter.add(Animal.Dog, "4", 3);
            shelter.getList(Animal.Dog);
            shelter.pickAnimal();


    }
}