package GameObjects;


import java.awt.Point;
import java.util.Arrays;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0700859
 */
public class Ship {
    private int length;
    private Point[] points;
    
    public Ship(int len, Point[] coords){
        points=coords.clone();
        length=len;
    }
    
    public boolean hit(int x, int y){
        for(Point p:points){
            if(p.x==x&&p.y==y)
                return true;
        }
        return false;
    }
    
    public boolean contains(int r, int c){
        for(Point p:points){
            if(p.x==r&&p.y==c)
                return true;
        }
        return false;
    }
    
    public Point[] getPoints(){
        return points.clone();
    }
    
    public int getLength(){
        return length;
    }
    
    public String toString(){
        return "Ship Length: "+length;//+" - "+Arrays.toString(points);
    }
    
    public boolean intersects(Ship s){
        for(Point p:points){
            for(Point p2:s.points){
                if(p.equals(p2))
                    return true;
            }
        }
        return false;
    }
}
