package at.htlVillach.noahArsic.minesweeper;

import java.awt.*;

public class Menu {

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Minesweeper", 180, 150);
        g.drawRect(285, 325, 175, 50);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g.drawString("Play", 345, 362);
    }
}
