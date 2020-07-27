package ru.srafe.swingy.controller;

import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.persons.Villain;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.model.persons.VillainsFactory;

import java.util.ArrayList;

public class GameInfo {

    private GameMap map;
    private Hero hero;
    private ArrayList<Villain> villains = new ArrayList<>();

    public void generateGameInfo(Hero hero) {
        this.hero = hero;
        map = new GameMap(hero.getLevel());
        this.hero.setCoordinates(new Coordinates(map.getSize() / 2, map.getSize() / 2));
        map.setCellByCoordinates(this.hero.getCoordinates(), 'H');
        generateVillains();
    }

    public void generateVillains() {
        int villainsCount = hero.getLevel() * 4;
        while (villainsCount > 0) {
            villains.add(VillainsFactory.generateVillain(hero.getLevel(), map.getSize()));
            villainsCount--;
        }
        checkVillainsCoordinates();
    }

    private void checkVillainsCoordinates() {
        int i = 0;
        while (i < villains.size()) {
            Coordinates c = villains.get(i).getCoordinates();
            if (map.getCellByCoordinates(c) == '#' || map.getCellByCoordinates(c) == 'V' || map.getCellByCoordinates(c) == 'H') {
                villains.get(i).setCoordinates(new Coordinates(map.getSize()));
            } else {
                map.setCellByCoordinates(c, 'V');
                i++;
            }
        }
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
        Coordinates c = new Coordinates(map.getSize() / 2, map.getSize() / 2);
        hero.setCoordinates(c);
        map.setCellByCoordinates(c, 'H');
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public ArrayList<Villain> getVillains() {
        return villains;
    }

    public void setVillains(ArrayList<Villain> villains) {
        this.villains = villains;
    }
}
