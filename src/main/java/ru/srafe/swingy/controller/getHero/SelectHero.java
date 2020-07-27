package ru.srafe.swingy.controller.getHero;

import ru.srafe.swingy.controller.Game;
import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.view.GUIVisual.SelectAHeroWindow;
import ru.srafe.swingy.view.Visualiser;

public class SelectHero {

    private static Hero hero;

    public static void heroSelection(Visualiser visualiser) {

        if (!visualiser.isGUI()) {
            selectHeroConsole(visualiser);

            GameInfo info = new GameInfo();
            info.generateGameInfo(hero);

            try {
            Game game = new Game();
            game.game(info, visualiser);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            selectHeroGUI(visualiser);
        }
    }

    public static void setHero(Hero h) {
        SelectHero.hero = h;
    }

    public static void selectHeroGUI(Visualiser visualiser) {
        SelectAHeroWindow window = new SelectAHeroWindow(visualiser);
        window.pack();
        window.setVisible(true);
    }


    private static void selectHeroConsole(Visualiser visualiser) {
        char choice = 'v';
        Hero h = null;
        while (true) {
            try {
                visualiser.print("Создать нового персонажа? y/n");
                choice = visualiser.getCommand().toLowerCase().charAt(0);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            switch (choice) {
                case ('y') :
                    h = MakeHero.makeHero(visualiser);
                    break;
                case ('n') :
                    h = LoadHero.loadHeroFromFile(visualiser);
                    break;
                default:
                    visualiser.print("Неверный ввод");
            }
            if (h != null) {
                hero = h;
                break;
            }
        }
    }





}
