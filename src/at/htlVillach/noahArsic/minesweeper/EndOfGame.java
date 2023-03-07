package at.htlVillach.noahArsic.minesweeper;

import java.awt.*;

public class EndOfGame {

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("You've " +(MouseInput.getWonGame() ? "won" : "lost"), 200, 150);
        g.drawString("Time: " +MouseInput.getTimePlayed(), 250, 300);
        g.drawRect(285, 430, 175, 50);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g.drawString("Play", 345, 463);
    }
}
