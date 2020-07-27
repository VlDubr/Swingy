package ru.srafe.swingy.view.GUIVisual;

import ru.srafe.swingy.controller.Game;
import ru.srafe.swingy.controller.GameInfo;
import ru.srafe.swingy.controller.engine.combat.Looting;
import ru.srafe.swingy.model.inventory.Artifact;
import ru.srafe.swingy.model.inventory.ArtifactFactory;
import ru.srafe.swingy.view.ConsoleVisual;
import ru.srafe.swingy.view.Visualiser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel buttonsPanel;
    private JPanel arrowsPanel;
    private JButton saveAndExitButton;
    private JButton upButton;
    private JButton downButton;
    private JButton rightButton;
    private JButton leftButton;
    private JButton inventoryButton;
    private JButton consoleButton;
    private JScrollPane textPanel;
    private JTextArea textArea;


    private final Visualiser visualiser;
    private final GameInfo info;
    private boolean villainsStep = false;
    private ImageIcon grassIcon = new ImageIcon(".\\images\\grass.jpeg");
    private ImageIcon stoneIcon = new ImageIcon(".\\images\\stone.png");
    private ImageIcon heroIcon = new ImageIcon(".\\images\\hero.png");
    private ImageIcon villainIcon = new ImageIcon(".\\images\\villain.png");

    public MainWindow(Visualiser visualiser, GameInfo info) {
        super("Игра!");
        setupUI();
        this.visualiser = visualiser;
        this.info = info;
        this.setResizable(false);

        this.setBounds(500, 500, 2500, 1000);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Container container = this.getContentPane();
        container.add(mainPanel);

        upButton.addActionListener(new MainWindow.upStepEventListener());
        downButton.addActionListener(new MainWindow.downStepEventListener());
        leftButton.addActionListener(new MainWindow.leftStepEventListener());
        rightButton.addActionListener(new MainWindow.rightStepEventListener());
        saveAndExitButton.addActionListener(new MainWindow.saveAndExitEventListener());
        inventoryButton.addActionListener(new MainWindow.inventoryEventListener());
        consoleButton.addActionListener(new MainWindow.consoleEventListener());

        mapPanel.setLayout(new GridLayout(info.getMap().getSize(), info.getMap().getSize(), 1, 1));

        Image scaledImage = grassIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        grassIcon = new ImageIcon(scaledImage);
        scaledImage = stoneIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        stoneIcon = new ImageIcon(scaledImage);
        scaledImage = heroIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        heroIcon = new ImageIcon(scaledImage);
        scaledImage = villainIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        villainIcon = new ImageIcon(scaledImage);

//        mapPanel.addComponentListener(new ComponentAdapter() {
//            public void componentResized(ComponentEvent evt) {
//                visualiseTheMap();
//            }
//        });

        visualiseTheMap();
    }

    private void game(char move) {
        try {
            villainsStep = !villainsStep;
            Game.nextStep(info, visualiser, move, villainsStep);
            visualiseTheMap();
            if (Game.winBattle) {
                getLoot();
                Game.winBattle = false;
            }
            if (Game.goToNextLevel) {
                int x = JOptionPane.showConfirmDialog(this, "Победа! \n Перейти на следующий уровень?",
                        "Уровень пройден!", JOptionPane.YES_NO_OPTION);
                dispose();
                Game.goToNextLevel = false;
                if (x == 0)
                    Game.goToNextLevel(info, visualiser, 'y');
                else
                    Game.goToNextLevel(info, visualiser, 'n');
            }
            if (Game.defeat) {
                int x = JOptionPane.showConfirmDialog(this, "Поражение...\n Создать нового персонажа?",
                        "Поражение", JOptionPane.YES_NO_OPTION);
                if (x == 0) {
                    Game.defeat = false;
                    ru.srafe.swingy.controller.Menu.menu("gui");
                }
                dispose();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLoot() {
        if (Looting.loot(info.getHero(), new Random(System.currentTimeMillis()), visualiser)) {
            Artifact newArtifact = ArtifactFactory.getRandomArtifact(Game.villainLevel);
            String message = "Найден артефакт!\n" + newArtifact.getInfo(false);
            int i = newArtifact.getType().ordinal();
            if (info.getHero().getArtifacts()[i] != null) {
                message += "\nУ вас уже есть артефакт такого типа. \n" + info.getHero().getArtifacts()[i].getInfo(true);
            }
            message += "\nВзять его?";
            int x = JOptionPane.showConfirmDialog(this, message, "Найден артефакт!", JOptionPane.YES_NO_OPTION);
            if (x == 0)
                Looting.setNewArtifact(info.getHero().getArtifacts()[i], newArtifact, info.getHero(), visualiser);
        }

    }

    public void printMessage(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public void print(String s) {
        textArea.append("\n" + s);
    }


//    private ImageIcon getScaledIcon(ImageIcon icon) {
//        int width = mapPanel.getSize().width / (info.getMap().getSize() + 1);
//        if (width == 0)
//            width = 50;
//        int height = mapPanel.getSize().height / (info.getMap().getSize() + 1);
//        if (height == 0)
//            height = 50;
//        Image scaledIcon = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        return new ImageIcon(scaledIcon);
//    }

    public void visualiseTheMap() {
        info.getMap().clearMap(info);
        mapPanel.removeAll();
//        ImageIcon scaledGrass = getScaledIcon(grassIcon);
//        ImageIcon scaledStone = getScaledIcon(stoneIcon);
//        ImageIcon scaledVillain = getScaledIcon(villainIcon);
//        ImageIcon scaledHero = getScaledIcon(heroIcon);
        char[][] map = info.getMap().getMap();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                char cell = map[y][x];
                JLabel label = new JLabel();
                switch (cell) {
                    case ('.'):
                        label.setIcon(grassIcon);
                        break;
                    case ('#'):
                        label.setIcon(stoneIcon);
                        break;
                    case ('V'):
                        label.setIcon(villainIcon);
                        break;
                    case ('H'):
                        label.setIcon(heroIcon);
                }
                int index;
                if (y == 0)
                    index = x;
                else if (x == 0)
                    index = y * info.getMap().getSize();
                else
                    index = (y * info.getMap().getSize()) + x;
                mapPanel.add(label, index);
            }
        }
        mapPanel.updateUI();
    }

    private void setupUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mapPanel = new JPanel();
        mapPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 6.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(mapPanel, gbc);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setAlignmentX(0.5f);
        buttonsPanel.setAutoscrolls(false);
        buttonsPanel.setInheritsPopupMenu(false);
        buttonsPanel.setMinimumSize(new Dimension(238, 176));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 3.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonsPanel, gbc);
        saveAndExitButton = new JButton();
        Font tempFont = GUIVisual.getFont(null, -1, 24, saveAndExitButton.getFont());
        if (tempFont != null) saveAndExitButton.setFont(tempFont);
        saveAndExitButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        saveAndExitButton.setMinimumSize(new Dimension(362, 30));
        saveAndExitButton.setText("Сохранить и выйти");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        buttonsPanel.add(saveAndExitButton, gbc);
        arrowsPanel = new JPanel();
        arrowsPanel.setLayout(new GridBagLayout());
        arrowsPanel.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        buttonsPanel.add(arrowsPanel, gbc);
        upButton = new JButton();
        if (tempFont != null) upButton.setFont(tempFont);
        upButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        upButton.setMinimumSize(new Dimension(156, 30));
        upButton.setText("Вверх");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        arrowsPanel.add(upButton, gbc);
        downButton = new JButton();
        if (tempFont != null) downButton.setFont(tempFont);
        downButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        downButton.setMinimumSize(new Dimension(156, 30));
        downButton.setText("Вниз");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        arrowsPanel.add(downButton, gbc);
        rightButton = new JButton();
        if (tempFont != null) rightButton.setFont(tempFont);
        rightButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        rightButton.setMinimumSize(new Dimension(196, 30));
        rightButton.setText("Направо");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        arrowsPanel.add(rightButton, gbc);
        leftButton = new JButton();
        if (tempFont != null) leftButton.setFont(tempFont);
        leftButton.setInheritsPopupMenu(false);
        leftButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        leftButton.setMinimumSize(new Dimension(196, 30));
        leftButton.setText("Налево");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        arrowsPanel.add(leftButton, gbc);
        inventoryButton = new JButton();
        if (tempFont != null) inventoryButton.setFont(tempFont);
        inventoryButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        inventoryButton.setMinimumSize(new Dimension(362, 30));
        inventoryButton.setText("Инвентарь");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        buttonsPanel.add(inventoryButton, gbc);
        consoleButton = new JButton();
        consoleButton.setContentAreaFilled(true);
        if (tempFont != null) consoleButton.setFont(tempFont);
        consoleButton.setMaximumSize(new Dimension(2147483647, 2147483647));
        consoleButton.setMinimumSize(new Dimension(362, 30));
        consoleButton.setText("Консольный режим");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        buttonsPanel.add(consoleButton, gbc);
        textPanel = new JScrollPane();
        textPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 10.0;
        gbc.fill = GridBagConstraints.BOTH;
        buttonsPanel.add(textPanel, gbc);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFocusable(true);
        Font textAreaFont = GUIVisual.getFont(null, -1, 18, textArea.getFont());
        if (textAreaFont != null) textArea.setFont(textAreaFont);
        textPanel.setViewportView(textArea);
    }



    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    class upStepEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            game('w');
        }
    }

    class downStepEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            game('s');
        }
    }

    class leftStepEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            game('a');
        }
    }

    class rightStepEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            game('d');
        }
    }

    class saveAndExitEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int x = JOptionPane.showConfirmDialog(null, "Сохранить персонажа и выйти из игры?", "Выход", JOptionPane.YES_NO_OPTION);
                if (x == 0)
                    ru.srafe.swingy.controller.Menu.saveHero(info.getHero());
            }
            catch (IOException error) {
                error.printStackTrace();
            }
            dispose();
        }
    }

    class inventoryEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, info.getHero().getHeroCharacteristics());
        }
    }

    class consoleEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ConsoleVisual visual = new ConsoleVisual();
            ConsoleVisual.reader = new BufferedReader(new InputStreamReader(System.in));
            Game game = new Game();
            dispose();
            game.game(info, visual);
        }
    }

}
