package ru.srafe.swingy.controller.engine.moving;

import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.view.Visualiser;

public class HeroMoving {
    public static Coordinates moveHero(GameInfo info, char move) {
        switch (move) {
            case('w') :
                return moveTo(info, -1, 0);
            case('a') :
                return moveTo(info, 0, -1);
            case('s') :
                return moveTo(info, 1, 0);
            case('d') :
                return moveTo(info, 0, 1);
            default:
                return info.getHero().getCoordinates();
        }
    }

    private static Coordinates moveTo(GameInfo info, int x, int y) {
        Coordinates c = new Coordinates(info.getHero().getCoordinates().getX(), info.getHero().getCoordinates().getY());
        c.setX(c.getX() + x);
        c.setY(c.getY() + y);
        return c;
    }

    public static void goToCell(GameInfo info, Coordinates newCoords) {
        info.getMap().setCellByCoordinates(info.getHero().getCoordinates(), '.');
        info.getHero().setCoordinates(newCoords);
        info.getMap().setCellByCoordinates(newCoords, 'H');
    }

}
