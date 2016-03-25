/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author c0700859
 */
public interface Player {
    public GameGrid getGrid();
    public void play();
    public void setPlayer(Player p);
}
