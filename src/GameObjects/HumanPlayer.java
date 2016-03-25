/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author c0700859
 */
public class HumanPlayer implements Player{
    private GameGrid ownGrid;
    private Player opponent;
    private String name;
    
    public HumanPlayer(String name){
        this.name=name;
        ownGrid=new GameGrid(10);
    }
    
    public void setPlayer(Player p){
        opponent=p;
    }
    
    public void setGrid(GameGrid grid) throws CloneNotSupportedException{
        ownGrid=grid.clone();
    }
    
    public void setFleet(ArrayList<Ship> fleet){
        for(Ship s:fleet){
            ownGrid.addShip(s);
        }
        //System.out.println("PLAYER\n"+ownGrid);
    }
    
    public boolean gameOver(){
        if(ownGrid.gameOver())
            System.out.println("HUMAN LOST");
        else if(opponent.getGrid().gameOver())
            System.out.println("OPPONENT LOST");
        return ownGrid.gameOver()||opponent.getGrid().gameOver();
    }
    
    @Override
    public GameGrid getGrid() {
          return ownGrid;
    }

    @Override
    public void play() {
        Scanner scn=new Scanner(System.in);
        System.out.println("COORDS");
        Point p=new Point(scn.nextInt(),scn.nextInt());
        boolean hit=opponent.getGrid().hit(p.x, p.y);
        //System.out.println(hit);
    }
    
    public boolean play(Point p){
        return opponent.getGrid().hit(p.x, p.y);
    }
    
}
