/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testers;

import ZhengHe.MainFrame;

/**
 *
 * @author sntgc
 */
public class BotvBot {
    public static void main(String[] args) {
        double avg=0;
        int totalMatches=0;
        int bbWins=0;
        int jjWins=0;
        for (int i = 0; i < 1000000; i++) {
            int turns = 0;
            MainFrame bot1 = new MainFrame(null, "Billy Bob");
            MainFrame bot2 = new MainFrame(bot1, "Joey Joe");
            while (!bot2.gameOver()) {
                bot1.play();
                turns += 1;
                if (bot1.gameOver()) {
                    break;
                }
                bot2.play();
                turns += 1;
            }
            if(turns%2==0)
                jjWins++;
            else
                bbWins++;
            avg+=turns;
            totalMatches++;
        }
        System.out.println("ACROSS "+totalMatches+" GAME PLAYED:");
        System.out.println("BILLY BOB WON "+bbWins+" TIMES");
        System.out.println("JOEY JOE WIN "+jjWins+" TIMES");
        System.out.printf("\nAVERAGE TURNS PER GAME WAS %f",avg/totalMatches);
    }
}
