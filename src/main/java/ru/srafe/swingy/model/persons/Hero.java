package ru.srafe.swingy.model.persons;

import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.inventory.Artifact;

import java.io.Serializable;
import java.util.Random;

import static ru.srafe.swingy.model.persons.HeroClass.*;

public class Hero extends Person implements Serializable {


    private HeroClass heroClass;
    private double experience;

    private int realAttack;
    private int realDefense;
    private int realHP;
    private int realInitiative;


    public Hero(String name, HeroClass heroClass) {
        super(name, new Coordinates(0,0), 1,0, 0, 0, 0, new Artifact[3]);
        this.heroClass = heroClass;

        Random rand = new Random(System.currentTimeMillis());
        if (heroClass.equals(KNIGHT)) {
            super.setAttack(5 + rand.nextInt(11));
            super.setDefense(9 + rand.nextInt(7));
            super.setHp(30 + rand.nextInt(26));
            super.setInitiative(3 + rand.nextInt(8));
        }
        else if (heroClass.equals(ROGUE)) {
            super.setAttack(15 + rand.nextInt(16));
            super.setDefense(9 + rand.nextInt(4));
            super.setHp(20 + rand.nextInt(16));
            super.setInitiative(7 + rand.nextInt(14));
        }
        else if (heroClass.equals(BERSERK)) {
            super.setAttack(17 + rand.nextInt(26)); ;
            super.setDefense(1 + rand.nextInt(4));
            super.setHp(30 + rand.nextInt(26));
            super.setInitiative(5 + rand.nextInt(11));
        }
        realAttack = super.getAttack();
        realDefense = super.getDefense();
        realHP = super.getHp();
        realInitiative = super.getInitiative();
    }

    public boolean addNewExp(double exp) {
        experience += exp;
        double levelBoundary = super.getLevel() * 1000 + ((super.getLevel() - 1) * (super.getLevel() - 1)) * 450;
        if (experience >= levelBoundary) {
            super.setLevel(super.getLevel() + 1);
            experience -= levelBoundary;
            updateCharacteristics();
            return true;
        }
        return false;
    }

    private void updateCharacteristics() {
        int[] artifactBoosts = new int[3];
        for (int i = 0; i < 3; i++) {
            if (super.getArtifacts()[i] != null) {
                artifactBoosts[i] += super.getArtifacts()[i].getBoost(true);
            }
        }
        super.setAttack(super.getAttack() + (super.getAttack() / 3 * (super.getLevel() - 1)));
        realAttack = super.getAttack() + artifactBoosts[0];

        super.setDefense(super.getDefense() + (super.getDefense() / 3 * (super.getLevel() - 1)));
        realDefense = super.getDefense() + artifactBoosts[1];;

        super.setHp(super.getHp() + (super.getHp() / 3 * (super.getLevel() - 1)));
        realHP = super.getHp() + artifactBoosts[2];;

        super.setInitiative(super.getInitiative() + (super.getInitiative() / 3 * (super.getLevel() - 1)));
        realInitiative = super.getInitiative();
    }

    public void setNewArtifact(Artifact newArtifact) {
        Artifact[] artifacts = super.getArtifacts();

        switch (newArtifact.getType().ordinal()) {
            case(0):
                if (artifacts[newArtifact.getType().ordinal()] != null)
                    realAttack -= artifacts[newArtifact.getType().ordinal()].getBoost(true);
                realAttack += newArtifact.getBoost(true);
                break;
            case(1):
                if (artifacts[newArtifact.getType().ordinal()] != null)
                    realDefense -= artifacts[newArtifact.getType().ordinal()].getBoost(true);
                realDefense += newArtifact.getBoost(true);
                break;
            case(2):
                if (artifacts[newArtifact.getType().ordinal()] != null)
                    realHP -= artifacts[newArtifact.getType().ordinal()].getBoost(true);
                realHP += newArtifact.getBoost(true);
        }

        artifacts[newArtifact.getType().ordinal()] = newArtifact;
        super.setArtifacts(artifacts);
    }


    public void setRealAttack(int realAttack) {
        this.realAttack = realAttack;
    }

    public void setRealDefense(int realDefense) {
        this.realDefense = realDefense;
    }

    public int getMaxRealHP() {
        Artifact[] arts = super.getArtifacts();
        if (arts[2] != null)
            return super.getHp() + arts[2].getBoost(true);
        return super.getHp();
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public int getRealAttack() {
        return realAttack;
    }

    public int getRealDefense() {
        return realDefense;
    }

    public int getRealHP() {
        return realHP;
    }

    public void setRealHP(int realHP) {
        this.realHP = realHP;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getRealInitiative() {
        return realInitiative;
    }

    public void setRealInitiative(int realInitiative) {
        this.realInitiative = realInitiative;
    }

    public String getHeroCharacteristics() {
        StringBuilder s = new StringBuilder("Класс: " + heroClass +
                "\nИмя: " + super.getName() +
                "\nУровень: " + getLevel() +
                "\nЗдоровье: " + realHP + "/" + getMaxRealHP() +
                "\nАтака: " + realAttack +
                "\nЗащита: " + realDefense +
                "\nИнициатива: " + getRealInitiative() +
                "\nАртефакты:");

        for (int i = 0; i < 3; i++) {
            if (getArtifacts()[i] == null) {
                s.append("\n").append(Artifact.artifactType[i]).append(": Нет");
            }
            else {
                s.append("\n").append(getArtifacts()[i].getInfo(true));
            }

        }
        return s.toString();
    }
}
