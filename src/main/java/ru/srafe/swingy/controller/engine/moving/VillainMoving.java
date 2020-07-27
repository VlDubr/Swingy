package ru.srafe.swingy.controller.engine.moving;

import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.controller.engine.combat.Combat;
import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.persons.Villain;
import ru.srafe.swingy.view.Visualiser;

import java.util.ArrayList;
import java.util.Random;

public class VillainMoving {
    public static void villainMoving(Villain villain, GameInfo info, Visualiser visualiser) {
        ArrayList<Coordinates> coordinates = new ArrayList<>(4);
        int x = villain.getCoordinates().getX();
        int y = villain.getCoordinates().getY();
        coordinates.add(new Coordinates(x - 1, y));
        coordinates.add(new Coordinates(x, y - 1));
        coordinates.add(new Coordinates(x + 1, y));
        coordinates.add(new Coordinates(x, y + 1));

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinates c = coordinates.get(i);
            if (!Coordinates.checkCoordinate(c, info.getMap().getSize())
                    || info.getMap().getCellByCoordinates(c) == '#'
                    || info.getMap().getCellByCoordinates(c) == 'V') {
                coordinates.remove(c);
                i--;
            }
        }

        if (coordinates.size() > 0) {
            Random rand = new Random(System.currentTimeMillis());
            Coordinates goCoord = coordinates.get(rand.nextInt(coordinates.size()));
            if (goCoord.equals(info.getHero().getCoordinates())) {
                Combat.startCombat(info, villain.getCoordinates(), visualiser);
            } else {
                villain.setCoordinates(goCoord);
            }
        }
    }
}
