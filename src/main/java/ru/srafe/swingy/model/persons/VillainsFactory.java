package ru.srafe.swingy.model.persons;

import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.inventory.Artifact;

import java.util.Random;

public class VillainsFactory {

    public static Villain generateVillain(int heroLevel, int mapSize) {
        Random random = new Random();

        int villainLevel = random.nextInt(heroLevel + 3);
        if (villainLevel < 1)
            villainLevel = 1;
        return new Villain("Монстр", villainLevel, new Coordinates(mapSize));
    }
}
