package client;/*
========================================
Space Invaders Recreated
by Lloyd Torres

Published on February 2013
Updated on July 2014

Space Invaders is copyrighted by Taito Corporation.
Code provided by Lloyd on the GNU GPL v3.0 license.
========================================
Legal:

Copyright (C) 2014 Lloyd Torres

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
========================================
*/

///// MODULES TO IMPORT


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

///// MAIN CLASS
public class SpaceInvadersFrame extends JFrame implements ActionListener{

    private javax.swing.Timer myTimer;
    private MainMenu menu; // first JPanel
    private boolean gameStart = false;
    private int playerId;
    private Game game; // component classes of the game
    private Cannon player;
    private AlienMan enemies;
    private Scorekeeper scoreMan;
    private Shield shield;
    private BulletMan shotsFired;
    private JPanel gamePanel;
    private JPanel currentGameView;
    private int wave = 0; // # of wins by the user, keeps track of subsequent alien start location
    private Style logTextStyle;
    private StyledDocument logDocument;
    public SpaceInvadersFrame(String title){
        super(title);
        playerId = -10;
        try{
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 800);

            gamePanel = new JPanel();
            gamePanel.setBackground(Color.BLACK);
            menu = new MainMenu();
            scoreMan = new Scorekeeper();
            currentGameView = menu;

            changeGameView(menu);
            JTextPane logText = new JTextPane();
            logText.setEditable(false);
            logText.setBackground(new Color(50, 50, 50));
            logText.setBorder(null);
            logDocument = logText.getStyledDocument();
            logTextStyle = logText.addStyle("Color Style", null);
            StyleConstants.setForeground(logTextStyle, Color.YELLOW);
            JScrollPane commandScrollPane = new JScrollPane(logText);
            commandScrollPane.setPreferredSize(new Dimension(200, 800));
            commandScrollPane.setBorder(null);
            setLayout(new BorderLayout());
            add(gamePanel, BorderLayout.CENTER);
            add(commandScrollPane, BorderLayout.WEST);
            appendToLog("Initialized");

            myTimer = new javax.swing.Timer(10,this); // update every 10 ms
            myTimer.start();
            setResizable(false);
        }catch (IOException e){
            e.printStackTrace();
        }catch (FontFormatException e){
            e.printStackTrace();
        }

    }
    public void appendToLog(String logEntry){
        try {
            if(logDocument != null){
                logDocument.insertString(logDocument.getLength(), "[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + logEntry + "\n", logTextStyle);
                System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + logEntry);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    private void changeGameView(JPanel gameView){
        if(currentGameView != null){
            gamePanel.remove(currentGameView);
        }
        gamePanel.add(gameView, BorderLayout.LINE_START);
        gamePanel.revalidate();
        currentGameView = gameView;
    }
    private void nextLevel() throws IOException, FontFormatException { // called every time user wins (all aliens destroyed), resets game setup
        if (wave < 10){
            wave += 1;
        }
        player.addLife();
        enemies = new AlienMan(wave,scoreMan,player,shield);
        shotsFired = new BulletMan(player,enemies,shield);
        game = new Game(player,enemies,scoreMan,shield,shotsFired);
        changeGameView(game);
    }

    private void startOverGame() throws IOException, FontFormatException {

        appendToLog("Started game");
        wave = 0;
        player = new Cannon();
        shield = new Shield();
        scoreMan.setShip(player);
        scoreMan.resetScore();
        enemies = new AlienMan(wave,scoreMan,player,shield);
        shotsFired = new BulletMan(player,enemies,shield);
        game = new Game(player,enemies,scoreMan,shield,shotsFired);
        changeGameView(game);
    }
    
    public void actionPerformed(ActionEvent evt){ // event listener stuff, update classes every 10 ms
        Object source = evt.getSource();
        if(source == myTimer){
            if (gameStart) {
                if (game.stillPlaying() && !game.isPaused() && !player.gotHit()) { // only move when not paused and player still alive
                    game.move(); // move player
                    if (enemies.metronome()) { // if aliens have moved
                        shotsFired.setAlienShots(enemies.attack()); // launch attack
                    }
                    enemies.ufoTrack(); // move mystery UFO regardless of beat
                    shotsFired.trackBullets(); // move shots if they exist
                }

                if (!game.stillPlaying()) {
                    enemies.ufoDestroy();
                }

                game.repaint();

                if (enemies.aliensGone()) { // if no aliens left
                    // error handling in case font doesn't exist
                    try {
                        nextLevel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }
                }

                if (game.doRestartGame()) { // check if player wants to restart game
                    // error handling in case font doesn't exist
                    try {
                        startOverGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                gameStart = menu.getStatus();
                if (gameStart){ // initialize if player starts game
                    // error handling in case font doesn't exist
                    try {
                        startOverGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }
                }
                menu.repaint();
            }
        }
    }

}
