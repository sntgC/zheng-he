/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ZhengHe;

import GameObjects.GameGrid;
import GameObjects.HumanPlayer;
import GameObjects.Player;
import GameObjects.Ship;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author c0700859
 */
public class MainFrame implements Player {

    private GameGrid ownGrid;
    private String name;
    private Player opponent;
    private ArrayList<Point> possible;
    private ArrayList<ArrayList<Point>> highPriority;
    private boolean[] posLoc;
    private boolean[] sunk;
    private int lastSunk;
    private ArrayList<Point> succesfulHits;
    private String log;
    private Point lastHit;

    public MainFrame(Player p, String n) {
        lastHit=null;
        log="";
        name=n;
        posLoc=new boolean[4];
        lastSunk=0;
        sunk = new boolean[5];
        opponent = p;
        possible = new ArrayList<>();
        highPriority = new ArrayList<>();
        succesfulHits=new ArrayList<>();
        //NESW
        highPriority.add(new ArrayList<Point>());
        highPriority.add(new ArrayList<Point>());
        highPriority.add(new ArrayList<Point>());
        highPriority.add(new ArrayList<Point>());
        if(p!=null)
            p.setPlayer(this);
        initiateGame();
        possibleLocations();
    }

    public void setPlayer(Player p){
        opponent=p;
    }
    
    private boolean beenHitBefore(Point p){
        return succesfulHits.contains(p);
    }
    
    private void initiateGame() {
        ownGrid = new GameGrid(10);
        ArrayList<Ship> fleet = new ArrayList<>();
        for (int i = 2; i <= 5; i++) {
            Ship temp = buildShip(i);
            while (intersectsShip(temp, fleet)) {
                temp = buildShip(i);
            }
            if (fleet.isEmpty()) {
                i--;
            }
            fleet.add(temp);
        }
        for (Ship s : fleet) {
            ownGrid.addShip(s);
        }
        //System.out.println("AI\n"+ownGrid);

    }

    private boolean inBounds(int r, int c) {
        return r > -1 && c > -1 && r < 10 && c < 10;
    }

    private void possibleLocations() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (r % 2 == c % 2) {
                    possible.add(new Point(r, c));
                }
            }
        }
    }
    
    public String getName(){
        return name;
    }

    private boolean intersectsShip(Ship s, ArrayList<Ship> obs) {
        for (Ship o : obs) {
            if (o.intersects(s)) {
                return true;
            }
        }
        return false;
    }

    public Ship buildShip(int length) {
        Point[] coords = new Point[length];
        int index = 0;
        boolean LR = Math.random() >= .5;
        if (LR) {
            int r = (int) (Math.random() * 10);
            int start = (int) (Math.random() * (9 - length));
            for (int i = start; i < start + length; i++) {
                coords[index++] = new Point(r, i);
            }
        } else {
            int c = (int) (Math.random() * 10);
            int start = (int) (Math.random() * (9 - length));
            for (int i = start; i < start + length; i++) {
                coords[index++] = new Point(i, c);
            }
        }
        return new Ship(length, coords);
    }

    @Override
    public GameGrid getGrid() {
        return ownGrid;
    }

    private int getLongestShip() {
        for (int i = 4; i >= 0; i--) {
            if (!sunk[i]) {
                if (i == 0) {
                    return 2;
                }
                return i + 1;
            }
        }
        return 0;
    }
    
    private int getShortestShip(){
        for (int i = 0; i <5; i++) {
            if (!sunk[i]) {
                if (i == 0) {
                    return 2;
                }
                return i + 1;
            }
        }
        return 0;
    }
    
    private void updatePosLoc(){
        
    }
    
    private boolean hasBetterMove(){
        if(!(posLoc[0]||posLoc[1]||posLoc[2]||posLoc[3]))
                return false;
        for(ArrayList<Point> arr: highPriority)
            if(!arr.isEmpty())
                return true;
        return false;
    }
    
    public boolean gameOver(){
        /*if(ownGrid.gameOver())
            System.out.println(name+" LOST");
        else if(opponent.getGrid().gameOver())
            System.out.println(((MainFrame)opponent).getName()+" LOST");*/
        return ownGrid.gameOver()||opponent.getGrid().gameOver();
    }

    private void failSafe(){
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (r % 2 != c % 2) {
                    possible.add(new Point(r, c));
                }
            }
        }
    }
    
    @Override
    public void play() {
        if (!hasBetterMove()) {
            int index = (int) (Math.random() * possible.size());
            Point p=null;
            try{
                p = possible.remove(index);
            }catch(Exception e){
                System.out.println("RAN OUT OF MOVES-----------------------------------------------------------------------------------WITH INDEX "+index);
                System.out.println(possible.size());
                System.out.println(opponent.getGrid());
                System.out.println(log);
                failSafe();
                return;
            }
            boolean success = opponent.getGrid().hit(p.x, p.y);
            if (success) {
                int len = getLongestShip();
                //System.out.println("LENGTH IS "+len);
                boolean neg=true;
                boolean pos=true;
                succesfulHits.add(new Point(p.x,p.y));
                lastHit=new Point(p.x,p.y);
                for (int i = 1; i <len; i++) {
                    //Checks for positive aspect
                    if (opponent.getGrid().inBounds(p.x + i, p.y)) {
                        if (!opponent.getGrid().getGrid()[p.x + i][p.y] && pos) {
                            highPriority.get(1).add(new Point(p.x + i, p.y));
                            //possible.remove(new Point(p.x + i, p.y));
                            posLoc[1] = true;
                        } else {
                            if (!beenHitBefore(new Point(p.x + i, p.y))) {
                                pos=false;
                        }
                    }
                    }
                    //Checks for negative aspect
                    if (opponent.getGrid().inBounds(p.x-i, p.y)) {
                        if (!opponent.getGrid().getGrid()[p.x-i][p.y]&&neg) {
                            highPriority.get(3).add(new Point(p.x-i, p.y));
                            //possible.remove(new Point(p.x-i,p.y));
                            posLoc[3]=true;
                        }else
                            if(!beenHitBefore(new Point(p.x-i,p.y)))
                                neg=false;
                    }
                }
                neg=true;
                pos=true;
                for (int i = 1; i < len; i++) {
                    //Positive Aspect
                    if (opponent.getGrid().inBounds(p.x, p.y+i)) {
                        if (!opponent.getGrid().getGrid()[p.x][p.y+i]&&pos) {
                            highPriority.get(2).add(new Point(p.x, p.y+i));
                            //possible.remove(new Point(p.x,p.y+i));
                            posLoc[2]=true;
                        }else
                            if(!beenHitBefore(new Point(p.x,p.y+i)))
                                pos=false;
                    }
                    //Negative Aspect
                    if (opponent.getGrid().inBounds(p.x, p.y-i)) {
                        if (!opponent.getGrid().getGrid()[p.x][p.y-i]&&neg) {
                            highPriority.get(0).add(new Point(p.x, p.y-i));
                            //possible.remove(new Point(p.x,p.y-i));
                            posLoc[0]=true;
                        }else
                            if(!beenHitBefore(new Point(p.x,p.y-i)))
                                neg=false;
                    }
                }
            }
        } else {
            //System.out.println("\n\nHP MODE ACTIVATED\n\n");
            //System.out.println(Arrays.toString(posLoc));
            if(lastHit!=null){
                for(int i=-1;i<=1;i+=2){
                    if(inBounds(lastHit.x+i,lastHit.y)){
                        if(!opponent.getGrid().getGrid()[lastHit.x+i][lastHit.y]){
                            log+="CALLED 1. at "+(lastHit.x+i)+","+(lastHit.y)+"\n";
                            possible.remove(new Point(lastHit.x+i,lastHit.y));
                            possible.add(new Point(lastHit.x+i,lastHit.y));
                        }
                        
                    }
                    if(inBounds(lastHit.x,lastHit.y+i)){
                        if(!opponent.getGrid().getGrid()[lastHit.x][lastHit.y+i]){
                            log+="CALLED 2 at "+(lastHit.x)+","+(lastHit.y+i)+"\n";
                            possible.remove(new Point(lastHit.x,lastHit.y+i));
                            possible.add(new Point(lastHit.x,lastHit.y+i));
                        }
                        
                    }
                }
                lastHit=null;
            }
            int index = (int) (Math.random() * 4);
            while(!posLoc[index]){
                index = (int) (Math.random() * 4);
            }
            Point p = highPriority.get(index).remove(0);
            possible.remove(p);
            /*
            0->1 3
            1->0 2
            2->1 3
            3->0 2
            */
            if(opponent.getGrid().hit(p.x, p.y)){
                posLoc[(index+1)%2]=false;
                posLoc[(index+1)%2+2]=false;
                succesfulHits.add(new Point(p.x,p.y));
                //Adds the surrounding area to the point, technically more likely to have a point,
                //but the PQueue system isn;t integrated yet.
                for(int i=-1;i<=1;i+=2){
                    if(inBounds(p.x+i,p.y)){
                        if(!opponent.getGrid().getGrid()[p.x+i][p.y]){
                            log+="CALLED 1. at "+(p.x+i)+","+(p.y)+"\n";
                            possible.remove(new Point(p.x+i,p.y));
                            possible.add(new Point(p.x+i,p.y));
                        }
                        
                    }
                    if(inBounds(p.x,p.y+i)){
                        if(!opponent.getGrid().getGrid()[p.x][p.y+i]){
                            log+="CALLED 2 at "+(p.x)+","+(p.y+i)+"\n";
                            possible.remove(new Point(p.x,p.y+i));
                            possible.add(new Point(p.x,p.y+i));
                        }
                        
                    }
                }
            }else{
                posLoc[index]=false;
            }
            if(highPriority.get(index).isEmpty()){
                posLoc[index]=false;
            }
        }
        //Methods to be called every turn
        updateSunk();
        if(lastSunk!=0){
            lastSunk=0;
            posLoc=new boolean[4];
            for(int k=highPriority.size()-1;k>=0;k--){
                for(int j=highPriority.get(k).size()-1;j>=0;j--){
                    if(!highPriority.get(k).isEmpty()){
                        Point p=highPriority.get(k).remove(j);
                        if(p.x%2==p.y%2)
                            possible.add(p);
                    }
                }
            }
        }
    }
    
    private void updateSunk(){
        int[] ships=opponent.getGrid().getRemainingShips();
        boolean[] temp=new boolean[5];
        Arrays.fill(temp, true);
        for(int i:ships){
            if(i==2&&!temp[1])
                temp[0]=false;
            else
                temp[i-1]=false;
        }
        for(int i=0;i<5;i++){
            if(temp[i]!=sunk[i]){
                lastSunk=i==0? 2:i+1;
                break;
            }
        }
        sunk=temp.clone();
    }
}
