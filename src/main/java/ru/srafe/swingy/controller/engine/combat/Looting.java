package ru.srafe.swingy.controller.engine.combat;

import ru.srafe.swingy.model.inventory.Artifact;
import ru.srafe.swingy.model.inventory.ArtifactFactory;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.view.Visualiser;

import java.io.IOException;
import java.util.Random;

public class Looting {

    public static boolean loot(Hero hero, Random rand, Visualiser visualiser) {
        int count = rand.nextInt(100);
        if (count >= 94) {
            visualiser.printMessage("Найдено зелье очищения! Все проклятья с артефактов сняты.");
            purityPotion(hero, visualiser);
            return false;
        }
        else if (count >= 74) {
            int hp = hero.getMaxRealHP() / 2;
            visualiser.printMessage("Найдено лечебное зелье! Здоровье восстановлено на " + hp);
            healPotion(hero, visualiser, hp);
            return false;
        }
        else if (count >= 50) {
            return true;
        }
        return false;
    }

    private static void purityPotion(Hero hero,Visualiser visualiser) {
        for (int i = 0; i < 3; i++) {
            if (hero.getArtifacts()[i] != null && hero.getArtifacts()[i].isCursed()) {
                Artifact art = ArtifactFactory.cloneArtifact(hero.getArtifacts()[i]);
                art.setCursed(false);
                hero.setNewArtifact(art);
            }
        }
        visualiser.printHeroCharacteristics(hero);
    }

    private static void healPotion(Hero hero, Visualiser visualiser, int hp) {

        hero.setRealHP(Math.min(hero.getRealHP() + hp, hero.getMaxRealHP()));
        visualiser.print("Осталось здоровья " + hero.getRealHP() + "/" + hero.getMaxRealHP());
    }

    public static void artifact(Hero hero, int villainLevel, Visualiser visualiser) {
        Artifact newArtifact = ArtifactFactory.getRandomArtifact(villainLevel);
        visualiser.print(newArtifact.getInfo(false));
        int i = newArtifact.getType().ordinal();
        if (hero.getArtifacts()[i] != null) {
            visualiser.print("У вас уже есть артефакт такого типа");
            visualiser.print(hero.getArtifacts()[i].getInfo(true));
        }
        visualiser.print("Взять новый артефакт? y/n");
        boolean goOut = false;
        while (!goOut) {
            try {
                char yn = visualiser.getCommand().toLowerCase().charAt(0);
                switch (yn) {
                    case ('y'):
                        setNewArtifact(hero.getArtifacts()[i], newArtifact, hero, visualiser);
                        goOut = true;
                        break;
                    case ('n'):
                        goOut = true;
                        break;
                    default:
                        visualiser.print("Неверный ввод!");
                }
            }
            catch (IOException ignored) {}
        }
        visualiser.printHeroCharacteristics(hero);
    }

    public static void setNewArtifact(Artifact oldArtifact, Artifact newArtifact, Hero hero, Visualiser visualiser) {
        if (oldArtifact != null && oldArtifact.isCursed()) {
            visualiser.printMessage("Невозможно снять проклятый артефакт! Найдите зелье очищения.");
            return;
        }
        if (newArtifact.isCursed()) {
            visualiser.printMessage("Артефакт проклят! Теперь он снижает ваши характеристики!");
        }
        hero.setNewArtifact(newArtifact);
    }
}
