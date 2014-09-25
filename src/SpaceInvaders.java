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
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

///// MAIN CLASS
public class SpaceInvaders extends JFrame implements ActionListener{

	javax.swing.Timer myTimer;
	Drawer artist; // component classes of the game
	Cannon player;
	AlienTrackr enemies;
	Scorekeeper scoreMan;
	Shield shield;
	int wave = 0; // # of wins by the user, keeps track of subsequent alien start location
	
	public SpaceInvaders() throws IOException, FontFormatException{
		super("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		setSize(770,652);
		
		player = new Cannon();
		shield = new Shield();
		scoreMan = new Scorekeeper(player);
		enemies = new AlienTrackr(wave,scoreMan,player,shield);
		artist = new Drawer(player,enemies,scoreMan,shield);
		add(artist);

		myTimer = new javax.swing.Timer(10,this); // update every 10 ms
		myTimer.start();
		
		setResizable(false);
		setVisible(true);
	}
	
	public void resetGame(){ // called every time user wins (all aliens destroyed), resets game setup
		if (wave < 10){
			wave += 1;
		}
		player.addLife();
		enemies = new AlienTrackr(wave,scoreMan,player,shield);
		artist = new Drawer(player,enemies,scoreMan,shield);
		add(artist);
	}
	
	public void actionPerformed(ActionEvent evt){ // event listener stuff, update classes every 10 ms
		Object source = evt.getSource();
		if(source == myTimer){
			artist.move();
			artist.trackBullet();
			enemies.metronome();
	        enemies.attack();
			artist.repaint();
			if (enemies.aliensGone()){
				resetGame();
			}
		}
	}
	
	public static void main(String[]args) throws IOException, FontFormatException{
		new SpaceInvaders();
	}
}
