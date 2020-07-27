package ru.srafe.swingy.controller.engine.combat;

import ru.srafe.swingy.controller.Game;
import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.model.persons.Villain;
import ru.srafe.swingy.model.persons.VillainsFactory;
import ru.srafe.swingy.view.Visualiser;

import java.util.Random;

public class Combat {
    public static boolean startCombat(GameInfo info, Coordinates c, Visualiser visualiser) {
        Villain villain = null;
        for (Villain v: info.getVillains()) {
            if (v.getCoordinates().equals(c))
                villain = v;
        }

        Hero hero = info.getHero();
        if (villain == null) {
            villain = VillainsFactory.generateVillain(hero.getLevel(), info.getMap().getSize());
            villain.setCoordinates(c);
        }

        Random rand = new Random(System.currentTimeMillis());
        int allInitiative = hero.getRealInitiative() + villain.getInitiative();
        while (true) {
            int initiative = rand.nextInt(allInitiative);
            if (initiative < hero.getRealInitiative()) {
                int attack = getAttack(rand, hero.getRealAttack(), villain.getDefense());
                visualiser.print(hero.getName() + " наносит " + attack + " урона по " + villain.getName() + "у");
                villain.setHp(villain.getHp() - attack);
            }
            else {
                int attack = getAttack(rand, villain.getAttack(), hero.getRealDefense());
                visualiser.print(villain.getName() + " наносит " + attack + " урона по герою!");
                hero.setRealHP(hero.getRealHP() - attack);
            }

            if (hero.getRealHP() <= 0)
                return false;
            if (villain.getHp() <= 0) {
                double exp = 500 * (1 + ((double)hero.getLevel() - (double)villain.getLevel()) / 10);
                visualiser.print("Победа в бою! Осталось здоровья " + hero.getRealHP() + "/" + hero.getMaxRealHP());
                Game.villainLevel = villain.getLevel();
                Game.winBattle = true;
                if (hero.addNewExp(exp)) {
                    visualiser.newLevel(hero);
                }
                info.getVillains().remove(villain);
                return true;
            }
        }
    }

    private static int getAttack(Random rand, int attack, int defense) {
        int cube = rand.nextInt(20);
        if (cube == 19)
            attack *= 2;
        else if (cube < 19 && cube > 14)
            attack = (attack * 3) / 2;
        else if (cube == 0)
            attack = 0;
        attack -= defense;
        if (attack < 0)
            attack = 0;

        return attack;
    }

}
