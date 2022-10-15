package app.randomizer;

import app.db.model.Nation;

import java.util.Locale;
import java.util.Random;

public class Randomize {

    private static final Random rand = new Random();

    public static RandomizedPerson randomizePerson(){
        var person = new RandomizedPerson();

        person.nation = Nation.values()[rand.nextInt(Nation.values().length - 1)];
        person.isMan = rand.nextBoolean();
        person.username = RandomizeUsername(rand.nextInt(5) + 5);
        person.firstName = RandomizeUsername(rand.nextInt(5) + 5);
        person.lastName = RandomizeUsername(rand.nextInt(5) + 5);
        person.bio = RandomizeUsername(25);

        return person;
    }

    private static String RandomizeUsername(int length){
        if (length < 6) length = 6;
        if (length > 30) length = 30;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char)(rand.nextInt(25) + 65);
            sb.append(c);
        }

        return sb.toString().toUpperCase(Locale.ROOT);
    }
}
