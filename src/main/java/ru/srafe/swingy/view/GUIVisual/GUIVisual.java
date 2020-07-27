package ru.srafe.swingy.view.GUIVisual;

import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.inventory.Artifact;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.view.Visualiser;

import java.awt.*;
import java.io.IOException;

public class GUIVisual implements Visualiser {

    private MainWindow mainWindow;

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void visualiseTheMap(GameInfo info) {
        if (mainWindow != null)
            mainWindow.visualiseTheMap();
    }

    @Override
    public void print(String s) {
        if (mainWindow != null)
            mainWindow.print(s);
    }

    @Override
    public void printMessage(String s) {
        if (mainWindow != null)
            mainWindow.printMessage(s);
    }

    @Override
    public void win() {

    }

    @Override
    public void defeat() {

    }

    @Override
    public void goToNextLevel() {

    }

    @Override
    public void newLevel(Hero hero) {

    }

    @Override
    public void printArtifact(Artifact artifact, boolean printCurse) {

    }

    @Override
    public void printHeroCharacteristics(Hero hero) {

    }

    @Override
    public boolean isGUI() {
        return true;
    }


    @Override
    public String getCommand() throws IOException {
        return "";
    }

    @Override
    public void error(String s) {

    }

    public static Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }
}
