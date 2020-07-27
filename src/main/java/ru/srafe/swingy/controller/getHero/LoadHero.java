package ru.srafe.swingy.controller.getHero;

import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.view.GUIVisual.LoadHeroWindow;
import ru.srafe.swingy.view.Visualiser;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadHero {

    private static Hero hero;

    public static Hero loadHeroFromFile(Visualiser visualiser) {
        if (!visualiser.isGUI()) {
            loadHeroConsole(visualiser);
        }
        else {
            loadHeroGUI(visualiser);
        }
        return hero;
    }

    private static void loadHeroGUI(Visualiser visualiser) {
        LoadHeroWindow window = new LoadHeroWindow(visualiser);
        window.pack();
        window.setVisible(true);
    }

    private static void loadHeroConsole(Visualiser visualiser) {
        try {
            Hero h = load();
            visualiser.print("Найден герой:");
            visualiser.print("Имя: " + h.getName());
            visualiser.print("Класс: " + h.getHeroClass().toString());
            visualiser.print("Уровень: " + h.getLevel());

            while (true) {
                visualiser.print("Загрузить персонажа? y/n");
                String choice = visualiser.getCommand();
                switch (choice) {
                    case ("y"):
                        hero = h;
                        return;
                    case ("n"):
                        hero = null;
                        return;
                    default:
                        visualiser.print("Неверный ввод");
                }
            }
        } catch (Exception e) {
            visualiser.print("Файл сохранения не найден или повреждён.");
            hero = null;
        }
    }

    public static Hero load() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(".\\hero.save");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (Hero) objectInputStream.readObject();
    }
}
