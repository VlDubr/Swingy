package ru.srafe.swingy;

import ru.srafe.swingy.controller.Menu;

public class Application {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Требуется аргумент \"console\" или \"gui\"");
            return;
        }
        try {
            Menu.menu(args[0]);
        } catch (Exception e) {
            System.out.println("Что-то пошло не так...");
            System.out.println(e.getMessage());
        }
    }
}
