package ru.srafe.swingy.view;

import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.inventory.Artifact;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.model.persons.Villain;
import ru.srafe.swingy.view.GUIVisual.MainWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleVisual implements Visualiser{

    public static BufferedReader reader;

    String[] artifactType = {"Оружие","Броня","Шлем"};

    public void visualiseTheMap(GameInfo info) {
        System.out.println();

        info.getMap().clearMap(info);

        for (int i = 0; i < info.getMap().getSize(); i++) {
            for (int j = 0; j < info.getMap().getSize(); j++) {
                System.out.print(info.getMap().getMap()[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void print(String s) {
        System.out.println(s);
    }

    @Override
    public void error(String s) {
        print(s);
    }

    @Override
    public void win() {
        print("Победа, уровень пройден!");
    }

    @Override
    public void defeat() {
        print("Поражение!");
        print("T__T");
    }

    @Override
    public void goToNextLevel() {
        print("Перейти на следующий уровень? y/n");
        print("При отказе персонаж сохранится и игра будет завершена.");
    }

    @Override
    public void newLevel(Hero hero) {
        print("Получен новый уровень!");
        print("Уровень: " + hero.getLevel());
        print("Здоровье: " + hero.getRealHP());
        print("Атака: " + hero.getRealAttack());
        print("Защита: " + hero.getRealDefense());
        print("Инициатива: " + hero.getRealInitiative());
    }

    @Override
    public void printArtifact(Artifact artifact, boolean printCurse) {
        print(artifact.getInfo(printCurse));
    }

    @Override
    public void printHeroCharacteristics(Hero hero) {
        print("Класс: " + hero.getHeroClass());
        print("Имя: " + hero.getName());
        print("Уровень: " + hero.getLevel());
        print("Здоровье: " + hero.getRealHP() + "/" + hero.getMaxRealHP());
        print("Атака: " + hero.getRealAttack());
        print("Защита: " + hero.getRealDefense());
        print("Инициатива: " + hero.getRealInitiative());
        print("Артефакты:");
        for (int i = 0; i < 3; i++) {
            if (hero.getArtifacts()[i] == null) {
                print(artifactType[i] + ": Нет");
            }
            else {
                printArtifact(hero.getArtifacts()[i], true);
            }

        }
        print("");
    }


    @Override
    public String getCommand() {
        while (true) {
            try {
                if (reader == null)
                    reader = new BufferedReader(new BufferedReader(new InputStreamReader(System.in)));
                return reader.readLine();
            }
            catch (Exception e) {
                print("Неверный ввод!");
            }
        }

    }

    @Override
    public void setMainWindow(MainWindow window) {
    }

    @Override
    public void printMessage(String s) {
        print(s);
    }

    @Override
    public boolean isGUI() {
        return false;
    }
}
