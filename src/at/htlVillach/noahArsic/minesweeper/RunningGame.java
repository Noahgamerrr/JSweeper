package at.htlVillach.noahArsic.minesweeper;

import java.awt.*;

public class RunningGame {
    private final Game game;

    public RunningGame(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (game.visible[i][j] == -1) g.setColor(Color.gray);
                else g.setColor(Color.darkGray);
                g.fillRect((((Game.WIDTH) / 25) * i + i) + 50, ((Game.HEIGHT / 25) * j + j) + 50,
                        (Game.WIDTH) / 25, Game.HEIGHT / 25);
                if (game.field[i][j] && game.visible[i][j] == -1) g.drawImage(Game.IMG_BOMB, (((Game.WIDTH) / 25) * i + i) + 50, ((Game.HEIGHT / 25) * j + j) + 50, null);
                if (game.visible[i][j] == 1) g.drawImage(Game.IMG_FLAG, (((Game.WIDTH) / 25) * i + i) + 50, ((Game.HEIGHT / 25) * j + j) + 50, null);
                if (game.visible[i][j] == -1 && game.bombsInRange[i][j] != 0) {
                    g.setFont(new Font("Arial", Font.BOLD, 24));
                    g.setColor(Color.black);
                    g.drawString(String.valueOf(game.bombsInRange[i][j]), (((Game.WIDTH) / 25) * i + i) + 60, ((Game.HEIGHT / 25) * j + j) + 75);
                }
            }
        }
        Game.H.render(g);
    }
}
