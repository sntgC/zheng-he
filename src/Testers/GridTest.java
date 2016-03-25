/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testers;

import GameObjects.GameGrid;
import GameObjects.Ship;
import java.awt.Point;
/**
 *
 * @author c0700859
 */
public class GridTest {
    public static void main(String[] args) {
        GameGrid s=new GameGrid(10);
        s.addShip(new Ship(2,new Point[]{new Point(2,0),new Point(2,1)}));
        s.hit(2, 0);
        System.out.println(s);
    }
}
