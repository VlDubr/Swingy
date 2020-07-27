package ru.srafe.swingy.model.persons;

import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.inventory.Artifact;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int level;

    private Coordinates coordinates;
    
    private int attack;
    private int defense;
    private int hp;
    private int initiative;

    private Artifact[] artifacts;

    public Person(String name, Coordinates coordinates, int level,int attack, int defense, int hp, int initiative, Artifact[] artifacts) {
        this.level = level;
        this.name = name;
        this.coordinates = coordinates;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.initiative = initiative;
        this.artifacts = artifacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public Artifact[] getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Artifact[] artifacts) {
        this.artifacts = artifacts;
    }

}
