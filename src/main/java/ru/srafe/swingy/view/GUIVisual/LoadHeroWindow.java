package ru.srafe.swingy.view.GUIVisual;

import ru.srafe.swingy.controller.Game;
import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.controller.getHero.LoadHero;
import ru.srafe.swingy.controller.getHero.SelectHero;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.model.persons.HeroClass;
import ru.srafe.swingy.view.Visualiser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadHeroWindow extends JFrame {

    private Visualiser visualiser;
    private Hero hero;

    public LoadHeroWindow(Visualiser visualiser) {
        super("Загрузка персонажа");
        this.visualiser = visualiser;
        this.setResizable(false);
        this.setBounds(500,500,250,100);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4,2,2,2));

        JLabel heroNameLabel = new JLabel("Имя");
        JLabel heroNameText = new JLabel("");
        JLabel heroClassLabel = new JLabel("Класс");
        JLabel heroClassText = new JLabel("");
        JLabel heroLevelLabel = new JLabel("Уровень");
        JLabel heroLevelText = new JLabel("");

        JButton loadButton = new JButton("Загрузить");
        loadButton.addActionListener(new loadButtonEventListener());
        JButton denyButton = new JButton("Отмена");
        denyButton.addActionListener(new denyButtonEventListener());

        Font tempFont = GUIVisual.getFont(null, -1, 24, heroNameLabel.getFont());
        if (tempFont != null) {
            heroNameLabel.setFont(tempFont);
            heroNameText .setFont(tempFont);
            heroClassLabel.setFont(tempFont);
            heroClassText.setFont(tempFont);
            heroLevelLabel.setFont(tempFont);
            heroLevelText.setFont(tempFont);
            loadButton.setFont(tempFont);
            denyButton.setFont(tempFont);
        }



        container.add(heroNameLabel);
        container.add(heroNameText);
        container.add(heroClassLabel);
        container.add(heroClassText);
        container.add(heroLevelLabel);
        container.add(heroLevelText);
        container.add(loadButton);
        container.add(denyButton);

        try {
            hero = LoadHero.load();
            heroNameText.setText(hero.getName());
            heroClassText.setText(hero.getHeroClass().toString());
            heroLevelText.setText("" + hero.getLevel());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(container, "Файл сохранения не найден или повреждён.");
            dispose();
            SelectHero.selectHeroGUI(visualiser);
        }
    }

    class loadButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GameInfo info = new GameInfo();
            info.generateGameInfo(hero);
            dispose();
            try {
                Game game = new Game();
                game.game(info, visualiser);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            dispose();
        }
    }

    class denyButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SelectHero.selectHeroGUI(visualiser);
            dispose();
        }
    }



}

