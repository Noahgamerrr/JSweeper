package at.htlVillach.noahArsic.minesweeper;

import java.awt.*;

public class HUD {
    private static int frames = 0;

    public void render(Graphics g) {
        g.drawImage(Game.IMG_FLAG, 300, 0, 45, 45, null);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 36));
        g.drawString(": " +Game.getFlagCounter(), 350, 35);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("FPS: " +frames, 20, 15);
    }

    public static void setFrames(int frames) {
        HUD.frames = frames;
    }
}
