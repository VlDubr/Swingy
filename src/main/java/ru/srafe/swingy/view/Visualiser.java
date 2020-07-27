package ru.srafe.swingy.view;

import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.inventory.Artifact;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.view.GUIVisual.MainWindow;

import java.io.IOException;

public interface Visualiser {
    void visualiseTheMap(GameInfo info);
    void print(String s);
    void win();
    void defeat();
    void goToNextLevel();
    void newLevel(Hero hero);
    void printArtifact(Artifact artifact, boolean printCurse);
    void printHeroCharacteristics(Hero hero);
    String getCommand() throws IOException;
    boolean isGUI();
    void error(String s);
    void setMainWindow(MainWindow window);
    void printMessage(String s);
}
