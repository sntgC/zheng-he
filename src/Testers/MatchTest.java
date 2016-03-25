/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testers;

import GameObjects.HumanPlayer;
import GameObjects.Ship;
import ZhengHe.MainFrame;
import java.awt.Point;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author sntgc
 */
public class MatchTest {
    public static void main(String[] args) {
        HumanPlayer human=new HumanPlayer("PLAYER");
        human.getGrid().addShip(new Ship(3, new Point[] {
                                                new Point(0,0), 
                                                new Point(0,1),
                                                new Point(0,2)}),
                                new Ship(2, new Point[] {
                                                new Point(7,2), 
                                                new Point(6,2)}),
                                new Ship(2, new Point[] {
                                                new Point(0,8), 
                                                new Point(0,7)}),
                                new Ship(4, new Point[] {
                                                new Point(8,1), 
                                                new Point(8,2),
                                                new Point(8,3),
                                                new Point(8,4)}),
                                new Ship(5, new Point[] {
                                                new Point(9,9), 
                                                new Point(9,8),
                                                new Point(9,7),
                                                new Point(9,6),
                                                new Point(9,5)}));
        MainFrame ai=new MainFrame(human, "BOT");
        //human.play();
        //human.play();
        char[][] print=new char[10][10];
        for(char[] row:print){
            Arrays.fill(row,'~');
        }
        Scanner scn=new Scanner(System.in);
        while(!human.gameOver()){
            try{
            System.out.println("----------------------------------------------------------------");
            System.out.println("OPPONENT BOARD: \n"+getString(print));
            System.out.println("YOUR BOARD: \n"+human.getGrid());
            String input=scn.next();
            Point p =new Point(input.charAt(0)-65,Integer.parseInt(input.substring(1))-1);
            if(human.play(p)){
                print[p.x][p.y]='@';
            }else
                print[p.x][p.y]='X';
            ai.play();
            System.out.println("----------------------------------------------------------------");
            //System.out.println("AI: \n"+ai.getGrid());
            }catch(Exception e){
                System.out.println("SMALL MISHAP");
            }
        }
        System.out.println("DONE");
        
    }
    public static String getString(char[][] grid){
        String ret="  1 2 3 4 5 6 7 8 9 10\n";
        char c='A';
        for(char[] row:grid){
            ret+=c;
            for(char col:row){
                ret+=" "+col;
            }
            c++;
            ret+="\n";
        }
        return ret;
    }
}
