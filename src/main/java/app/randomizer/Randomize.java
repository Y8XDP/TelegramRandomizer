package app.randomizer;

import app.db.model.Nation;
import app.jsons.Families;
import app.jsons.Names;

import java.util.Locale;
import java.util.Random;

public class Randomize {

    private static final Random rand = new Random();

    private static final Names names = new Names();

    private static final Families families = new Families();

    public static RandomizedPerson randomizePerson(){
        var person = new RandomizedPerson();

        person.nation = Nation.values()[rand.nextInt(Nation.values().length - 1)];
        person.isMan = rand.nextBoolean();

        person.username = randomizeUsername(rand.nextInt(5) + 5);

        person.firstName = randomizeName(person.nation, person.isMan);
        person.lastName = randomizeFamily(person.nation, person.isMan);

        person.bio = randomizeUsername(25);

        return person;
    }

    private static String randomizeUsername(int length){
        if (length < 6) length = 6;
        if (length > 30) length = 30;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char)(rand.nextInt(25) + 65);
            sb.append(c);
        }

        return sb.toString().toUpperCase(Locale.ROOT);
    }

    private static String randomizeName(Nation nation, Boolean isMan){
        var namesList = names.getAll(isMan);
        return namesList.get(rand.nextInt(namesList.size() - 1)).name;
    }

    private static String randomizeFamily(Nation nation, Boolean isMan){
        var familiesList = families.getAll(isMan);
        return familiesList.get(rand.nextInt(familiesList.size() - 1)).family;
    }
}
