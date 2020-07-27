package ru.srafe.swingy.controller.getHero;

import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.model.persons.HeroClass;
import ru.srafe.swingy.view.GUIVisual.MakeHeroWindow;
import ru.srafe.swingy.view.Visualiser;

import java.io.IOException;

public class MakeHero {
    private static Hero hero;

    public static Hero makeHero(Visualiser visualiser) {
        if (!visualiser.isGUI()) {
            makeHeroConsole(visualiser);
        }
        else
            makeHeroGui(visualiser);
        return hero;
    }

    private static void makeHeroGui(Visualiser visualiser) {
        MakeHeroWindow window = new MakeHeroWindow(visualiser);
        window.pack();
        window.setVisible(true);
    }

    private static void makeHeroConsole(Visualiser visualiser) {
        String name;
        String heroClass;
        while (true) {
            try {
                visualiser.print("Введите имя героя");
                name = visualiser.getCommand();
                break;
            } catch (IOException ignored) {
            }
        }
        while (true) {
            try {
                visualiser.print("Выберите класс героя");
                visualiser.print("KNIGHT, ROGUE, BERSERK");
                heroClass = visualiser.getCommand().toUpperCase();
                if (heroClass.equals("KNIGHT") || heroClass.equals("ROGUE") || heroClass.equals("BERSERK")) {
                    break;
                } else {
                    visualiser.print("Неверный класс");
                }
            } catch (IOException ignored) {
            }
        }
        hero = new Hero(name, HeroClass.valueOf(heroClass));
    }
}
