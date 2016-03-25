package GameObjects;


import java.awt.Point;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0700859
 */
public class GameGrid {
    private boolean[][] grid;
    private ArrayList<Ship> fleet;
    
    public GameGrid(int x){
        grid=new boolean[x][x];
        fleet=new ArrayList<>();
    }
    
    public void addShip(Ship... ships){
        for(Ship s:ships)
            fleet.add(s);
            
    }
    
    public boolean[][] getGrid(){
        return grid;
    }
    
    public int[] getRemainingShips(){
        int[] ret=new int[fleet.size()];
        int index=0;
        for(Ship s: fleet)
            ret[index++]=s.getLength();
        return ret;
    }
    
    public boolean inBounds(int r, int c){
        return r>-1&&c>-1&&r<grid.length&&c<grid.length;
    }
    public boolean hit(int r, int c){
        if(inBounds(r,c)){
                if(hasShip(r,c)){
                    grid[r][c]=true;
                    sinkShip();
                    return true;
                }
                grid[r][c]=true;
            return false;
        }else{
            return false;
        }
    }
    
    private boolean hasShip(int r, int c){
        for(Ship s:fleet){
                if(s.contains(r,c))
                    return true;
            }
        return false;
    }
    
    private void sinkShip(){
        for(int k=fleet.size()-1;k>=0;k--){
            Point[] points=fleet.get(k).getPoints();
            boolean delete=true;
            for(Point p:points){
                if(!grid[p.x][p.y])
                    delete=false;
            }
            if(delete){
                fleet.remove(k);
            }
            /*if(delete){
                System.out.println("----------------------"+fleet.remove(k)+" was removed \n"+"----------------------");
            }*/
        }
    }
    
    @Override
    public GameGrid clone() throws CloneNotSupportedException{
        return (GameGrid)super.clone();
    }
    
    public boolean gameOver(){
        return fleet.isEmpty();
    }
    
    public String toString(){
        char[][] output=new char[10][10];
        for(int r=0;r<10;r++){
            for(int c=0;c<10;c++){
                if(!grid[r][c]){
                    if(hasShip(r,c)){
                        output[r][c]='O';
                    }else{
                        output[r][c]='~';
                    }
                }else{
                    if(hasShip(r,c)){
                        output[r][c]='@';
                    }else{
                        output[r][c]='X';
                    }
                }
            }
        }
        String ret="  1 2 3 4 5 6 7 8 9 10\n";
        char c='A';
        for(char[] row:output){
            ret+=c;
            for(char col:row){
                ret+=" "+col;
            }
            ret+="\n";
            c++;
        }
        return ret;
    }
}
