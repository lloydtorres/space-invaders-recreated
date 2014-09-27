/*
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

///// MAIN CLASS
public class SpaceInvaders extends JFrame implements ActionListener{

	private javax.swing.Timer myTimer;

    private MainMenu menu; // first JPanel
    private boolean gameStart = false;

    private Overseer overseer; // component classes of the game
    private Cannon player;
    private AlienMan enemies;
    private Scorekeeper scoreMan;
    private Shield shield;
    private BulletMan shotsFired;
    private int wave = 0; // # of wins by the user, keeps track of subsequent alien start location
	
	public SpaceInvaders() throws IOException, FontFormatException{
		super("Space Invaders Recreated");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		setSize(770,652);

        menu = new MainMenu();
        add(menu);

		myTimer = new javax.swing.Timer(10,this); // update every 10 ms
		myTimer.start();
		
		setResizable(false);
		setVisible(true);
	}
	
	private void nextLevel() throws IOException, FontFormatException { // called every time user wins (all aliens destroyed), resets game setup
        remove(overseer);
		if (wave < 10){
			wave += 1;
		}
		player.addLife();
		enemies = new AlienMan(wave,scoreMan,player,shield);
		overseer = new Overseer(player,enemies,scoreMan,shield,shotsFired);
		add(overseer);
	}

    private void startOverGame() throws IOException, FontFormatException {

        remove(overseer);

        wave = 0;
        player = new Cannon();
        shield = new Shield();

        // error handling in case fonts are missing
        try {
            scoreMan = new Scorekeeper(player);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        enemies = new AlienMan(wave,scoreMan,player,shield);
        shotsFired = new BulletMan(player,enemies,shield);
        overseer = new Overseer(player,enemies,scoreMan,shield,shotsFired);
        add(overseer);
    }
	
	public void actionPerformed(ActionEvent evt){ // event listener stuff, update classes every 10 ms
		Object source = evt.getSource();
		if(source == myTimer){
            if (gameStart) {
                if (overseer.stillPlaying() && !overseer.isPaused() && !player.gotHit()) { // only move when not paused and player still alive
                    overseer.move(); // move player
                    if (enemies.metronome()) { // if aliens have moved
                        shotsFired.setAlienShots(enemies.attack()); // launch attack
                    }
                    enemies.ufoTrack(); // move mystery UFO regardless of beat
                    shotsFired.trackBullets(); // move shots if they exist
                }

                if (!overseer.stillPlaying()) {
                    enemies.ufoDestroy();
                    scoreMan.calculateHiScore();
                }

                overseer.repaint();

                if (enemies.aliensGone()) { // if no aliens left
                    try {
                        nextLevel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }
                }

                if (overseer.doRestartGame()) { // check if player wants to restart game
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
                    player = new Cannon();
                    shield = new Shield();

                    try {
                        scoreMan = new Scorekeeper(player);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }

                    enemies = new AlienMan(wave,scoreMan,player,shield);
                    shotsFired = new BulletMan(player,enemies,shield);

                    try {
                        overseer = new Overseer(player,enemies,scoreMan,shield,shotsFired);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }

                    remove(menu);
                    add(overseer);
                }
                menu.repaint();
            }
		}
	}
	
	public static void main(String[]args) throws IOException, FontFormatException{
		new SpaceInvaders();
	}
}
