package ru.srafe.swingy.controller;

import ru.srafe.swingy.controller.engine.combat.Combat;
import ru.srafe.swingy.controller.engine.combat.Looting;
import ru.srafe.swingy.controller.engine.moving.HeroMoving;
import ru.srafe.swingy.controller.engine.moving.VillainMoving;
import ru.srafe.swingy.model.Coordinates;
import ru.srafe.swingy.view.GUIVisual.MainWindow;
import ru.srafe.swingy.view.Visualiser;
import java.io.IOException;
import java.util.Random;

public class Game {

    public static boolean defeat = false;
    public static boolean goToNextLevel = false;

    public static boolean winBattle = false;
    public static int villainLevel = 0;

    public void game(GameInfo info, Visualiser visualiser) {
        try {
            if (!visualiser.isGUI()) {
                consoleGame(info, visualiser);
            } else {
                MainWindow window = new MainWindow(visualiser, info);
                visualiser.setMainWindow(window);
                window.pack();
                window.setVisible(true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consoleGame(GameInfo info, Visualiser visualiser) throws Exception {
        boolean villainsStep = false;
        while (!defeat && !goToNextLevel) {
            try {
                visualiser.visualiseTheMap(info);
                visualiser.print("Выберите направление хода w/a/s/d");
                String command = visualiser.getCommand().toLowerCase();
                if (command.equals("gui")) {
                    game(info, Menu.gui);
                    break;
                }
                else {
                    char move = command.toCharArray()[0];
                    villainsStep = !villainsStep;
                    nextStep(info, visualiser, move, villainsStep);
                    if (winBattle) {
                        if (Looting.loot(info.getHero(), new Random(), visualiser)) {
                            visualiser.print("Найден артефакт!");
                            Looting.artifact(info.getHero(), villainLevel, visualiser);
                        }
                        winBattle = false;
                    }
                }
            }
            catch (StringIndexOutOfBoundsException e) {
                visualiser.print("Неверный ввод!");
            }
        }
        if (goToNextLevel) {
            visualiser.win();
            visualiser.goToNextLevel();
            char goToNextLevel = visualiser.getCommand().toLowerCase().charAt(0);
            goToNextLevel(info, visualiser, goToNextLevel);
        }
        else if (defeat){
            visualiser.defeat();
        }
    }

    public static void nextStep(GameInfo info, Visualiser visualiser, char move, boolean villainsStep) throws IOException {
        try {
            Coordinates newCoords = HeroMoving.moveHero(info, move);
            Character cell = info.getMap().getCellByCoordinates(newCoords);
            if (cell == null) {
                goToNextLevel = true;
                return;
            }
            switch (cell) {
                case ('.'):
                    HeroMoving.goToCell(info, newCoords);
                    Random random = new Random(System.currentTimeMillis());
                    if (random.nextInt(100) > 94)
                        Looting.loot(info.getHero(), random, visualiser);
                    break;
                case ('#'):
                    visualiser.error("Тут стена");
                    return;
                case ('V'):
                    if (Combat.startCombat(info, newCoords, visualiser)) {
                        winBattle = true;
                        HeroMoving.goToCell(info, newCoords);
                    }
                    else {
                        defeat = true;
                    }
                    break;
                case ('H'):
                    visualiser.error("Неверный ввод");
            }

            if (villainsStep) {
                int size = info.getVillains().size();
                for (int i = 0; i < size; i++) {
                    VillainMoving.villainMoving(info.getVillains().get(i), info, visualiser);
                    if (info.getVillains().size() < size) {
                        i--;
                        size--;
                    }
                }
            }
        }
        catch (Exception e) {
            visualiser.error("Неверный ввод!");
        }
    }


    public static void goToNextLevel(GameInfo info, Visualiser visualiser, char command) throws IOException {
        while (true) {
            if (command == 'y') {
                Game newGame = new Game();
                info.generateGameInfo(info.getHero());
                newGame.game(info, visualiser);
                break;
            }
            else if (command != 'n') {
                visualiser.error("Неверный ввод");
            }
            else {
                Menu.saveHero(info.getHero());
                break;
            }
        }
    }

}
