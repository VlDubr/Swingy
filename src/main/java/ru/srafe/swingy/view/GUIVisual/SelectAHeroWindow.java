package ru.srafe.swingy.view.GUIVisual;

import ru.srafe.swingy.controller.getHero.LoadHero;
import ru.srafe.swingy.controller.getHero.MakeHero;
import ru.srafe.swingy.view.Visualiser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectAHeroWindow extends JFrame{

    private final Visualiser visualiser;

    public SelectAHeroWindow(Visualiser visualiser) {
        super("Выбор героя");

        this.visualiser = visualiser;
        this.setResizable(false);
        this.setBounds(500,500,250,100);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,2,2,2));
        JLabel label = new JLabel("Создать персонажа или загрузить?");
        container.add(label);

        JPanel jPanel = new JPanel();
        JButton makeHero = new JButton("Создать");
        JButton loadHero = new JButton("Загрузить");

        Font tempFont = GUIVisual.getFont(null, -1, 24, makeHero.getFont());
        if (tempFont != null) {
            makeHero.setFont(tempFont);
            loadHero.setFont(tempFont);
            label.setFont(tempFont);
        }

        jPanel.add(makeHero);
        jPanel.add(loadHero);




        makeHero.addActionListener(new makeHeroEvenListener());
        loadHero.addActionListener(new loadHeroEvenListener());

        container.add(jPanel);

    }

    class makeHeroEvenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MakeHero.makeHero(visualiser);
            dispose();
        }
    }

    class loadHeroEvenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LoadHero.loadHeroFromFile(visualiser);
            dispose();
        }
    }

}
