package ru.srafe.swingy.controller;

import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.persons.Villain;

import java.util.Random;

public class GameMap {
    private int size;
    private final char[][] map; // 0 - empty, 1 - wall, H - hero, V - villain


    public GameMap(int heroLevel) {
        size = (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
        if (size == 0)
            size = 10;
        map = new char[size][size];
        generateMap();
    }

    private void generateMap() {
        Random random = new Random();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int temp = random.nextInt(7);
                    if (temp <= 4)
                        map[i][j] = '.';
                    else
                        map[i][j] = '#';
                }
        }
//        checkEmptiness(); TODO Доделать генерацию, чтобы не попадать в стены
    }

    public void clearMap(GameInfo info) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == 'V' || map[i][j] == 'H')
                    map[i][j] = '.';
            }
        }

        setCellByCoordinates(info.getHero().getCoordinates(), 'H');

        for (Villain v : info.getVillains()) {
            info.getMap().setCellByCoordinates(v.getCoordinates(), 'V');
        }
    }

//    private void checkEmptiness() {
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (i < size - 1 && j < size - 1) {
//                    if (map[i][j] == '.' && (map[i + 1][j] == '#'
//                            && map[i][j + 1] == '#' && map[i - 1][j] == '#'
//                            && map[i][j - 1] == '#'))
//                        map[i][j] = 49;
//                }
//                else if (i == size - 1 && j < size - 1) {
//                    if (map[i][j] == '.' && map[i][j + 1] == '#' && map[i - 1][j] == '#'
//                            && map[i][j - 1] == '#')
//                        map[i][j] = 49;
//                }
//                else if (j == size - 1 && i < size - 1) {
//                    if (map[i][j] == '.' && map[i + 1][j] == '#'
//                            && map[i - 1][j] == 49 && map[i][j - 1] == '#')
//                        map[i][j] = '#';
//                }
//                else {
//                    if (map[i][j] == '.' && map[i - 1][j] == '#'
//                            && map[i][j - 1] == '#')
//                        map[i][j] = '#';
//                }
//            }
//        }
//    }

    public int getSize() {
        return size;
    }

    public char[][] getMap() {
        return map;
    }

    public Character getCellByCoordinates(Coordinates coordinates) {
        if (coordinates.getX() < 0 || coordinates.getY() < 0
                || coordinates.getX() >= size || coordinates.getY() >= size)
            return null;
        return map[coordinates.getX()][coordinates.getY()];
    }

    public void setCellByCoordinates(Coordinates coordinates, char cell) {
        map[coordinates.getX()][coordinates.getY()] = cell;
    }
}
