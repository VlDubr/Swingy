package ru.srafe.swingy.view.GUIVisual;

import ru.srafe.swingy.controller.Game;
import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.model.persons.Hero;
import ru.srafe.swingy.model.persons.HeroClass;
import ru.srafe.swingy.view.Visualiser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakeHeroWindow extends JFrame{


    private final Visualiser visualiser;
    private final Container container = this.getContentPane();

    String[] classes = {
            "Rogue",
            "Knight",
            "Berserk"
    };

    private final JComboBox<String> heroClasses = new JComboBox<>(classes);
    private final JTextField heroName = new JTextField();

    public MakeHeroWindow(Visualiser visualiser) {
        super("Создание героя");
        this.setResizable(false);
        this.visualiser = visualiser;
        this.setBounds(500,500,250,100);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        heroClasses.setEditable(false);
        container.setLayout(new GridLayout(3,2,2,2));
        JLabel setNameLabel = new JLabel("Введите имя");
        JButton setHero = new JButton("Готово");

        Font tempFont = GUIVisual.getFont(null, -1, 24, heroClasses.getFont());
        if (tempFont != null) {
            heroClasses.setFont(tempFont);
            setNameLabel.setFont(tempFont);
            setHero.setFont(tempFont);
        }


        container.add(setNameLabel);
        container.add(heroName);
        container.add(heroClasses);


        setHero.addActionListener(new setNameEventListener());
        container.add(setHero);
    }

    class setNameEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = heroName.getText();
            String heroClass = (String) heroClasses.getSelectedItem();
            if (heroClass == null)
                heroClass = "Rogue";

            if (name.equals("")) {
                JOptionPane.showMessageDialog(container, "Имя не может быть пустым.");
            }
            else {
                GameInfo info = new GameInfo();
                info.generateGameInfo(new Hero(name, HeroClass.valueOf(heroClass.toUpperCase())));
                dispose();
                try {
                    Game game = new Game();
                    game.game(info, visualiser);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }


}
