package ru.srafe.swingy.model.persons;

import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.inventory.Artifact;

import java.util.Random;

public class Villain extends Person {

    protected Villain(String name, int level, Coordinates coordinates) {
        super(name, coordinates, level,0, 0, 0, 0, new Artifact[3]);
        Random rand = new Random();
        int temp = 5 + rand.nextInt(11);
        super.setAttack(temp + (temp / 3 * (level - 1)));

        temp = 9 + rand.nextInt(4);
        super.setDefense(temp + (temp / 3 * (level - 1)));

        temp = 20 + rand.nextInt(16);
        super.setHp(temp + (temp / 3 * (level - 1)));

        temp = 5 + rand.nextInt(8);
        super.setInitiative(temp + (temp / 3 * (level - 1)));
    }

}
