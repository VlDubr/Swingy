package ru.srafe.swingy.controller;


import ru.srafe.swingy.controller.getHero.SelectHero;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.view.ConsoleVisual;
import ru.srafe.swingy.view.GUIVisual.GUIVisual;
import ru.srafe.swingy.view.Visualiser;

import java.io.*;



public class Menu {
    public static Visualiser console;
    public static Visualiser gui;

    public static void menu(String view) throws IOException {
        console = new ConsoleVisual();
        gui = new GUIVisual();
        if (view.equals("console")) {
            SelectHero.heroSelection(console);
        }
        else if (view.equals("gui")) {
            SelectHero.heroSelection(gui);
        }
        else {
            System.out.println("Неверный аргумент.");
            System.out.println("Принимаются только аргументы \"console\" или \"gui\"");
            return;
        }
    }

    public static void saveHero(Hero hero) throws IOException{
        FileOutputStream outputStream = new FileOutputStream(".\\hero.save");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // сохраняем игру в файл
        objectOutputStream.writeObject(hero);

        //закрываем поток и освобождаем ресурсы
        objectOutputStream.close();
    }



}

