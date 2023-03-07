package at.htlVillach.noahArsic.minesweeper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MouseInput extends MouseAdapter {
    private final Game game;
    private static final int startFlagValue = Game.getFlagCounter();
    private static boolean wonGame;
    private static long timePlayed;

    public MouseInput(Game game) {
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx, my; //Positionen x y an der Maus
        mx = e.getX();
        my = e.getY();
        if (Game.getState() == Game.GAMESTATE.Menu) {
            if (mx >= 285 && mx <= 460 && my >= 325 == my <= 375) {
                Game.setState(Game.GAMESTATE.Game);
                timePlayed = System.currentTimeMillis();
            }
        }
        else if (Game.getState() == Game.GAMESTATE.Game) {
            int xa; //Position x in the Array (1-20)
            int ya; //Positionen y in the Array (1-20)
            Random r = new Random();
            wonGame = true;
            mx -= 50;
            my -= 50;
            if (mx >= 0 && mx < 620 && my >= 0 && my < 620) {
                xa = (mx - (mx % 31)) / 31;
                ya = (my - (my % 31)) / 31;
                if (e.getButton() == MouseEvent.BUTTON1 && game.visible[xa][ya] == 0) {
                    if (!game.getFirstFieldRevealed()) {
                        for (int i = 0; i < startFlagValue; i++) {
                            int fx = r.nextInt(20); //x on the field
                            int fy = r.nextInt(20); //y on the field
                            if (!(fx == xa && fy == ya) == !game.field[fx][fy]) game.field[fx][fy] = true;
                            else i--;
                        }
                        game.setFirstFieldRevealed(true);
                        for (int i = 0; i < game.field.length; i++) {
                            for (int j = 0; j < game.field[i].length; j++) {
                                game.bombsInRange[i][j] = Game.getGen().getBombsInRange(i, j);
                            }
                        }
                    }
                    game.visible[xa][ya] = -1;
                    if (game.bombsInRange[xa][ya] == 0) Game.getGen().revealEmptyFields(xa, ya);
                    if (game.field[xa][ya]) endGame();
                    for (int i = 0; i < 20; i++) {
                        for (int j = 0; j < 20; j++) {
                            if (!game.field[i][j] == (game.visible[i][j] != -1)) {
                                wonGame = false;
                                break;
                            }
                        }
                    }
                    if (wonGame) endGame();
                }
                else if (e.getButton() == MouseEvent.BUTTON3 && game.visible[xa][ya] != -1) {
                    Game.setFlagCounter(game.visible[xa][ya] == 0 ? Game.getFlagCounter() - 1 : Game.getFlagCounter() + 1);
                    game.visible[xa][ya] = game.visible[xa][ya] == 0 ? 1 : 0;
                }
            }
        }
        else if (mx >= 285 && mx <= 460 && my >= 430 == my <= 480) {
            Game.setState(Game.GAMESTATE.Game);
            timePlayed = System.currentTimeMillis();
            game.initializeGame();
        }
    }

    private void endGame() {
        long time = System.currentTimeMillis();
        timePlayed = (System.currentTimeMillis() - timePlayed) / 1000;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (game.field[i][j]) game.visible[i][j] = -1;
            }
        }
        while (System.currentTimeMillis() - time < 2000);
        Game.setState(Game.GAMESTATE.EndOfGame);
    }

    public static boolean getWonGame() {
        return wonGame;
    }

    public static long getTimePlayed() {
        return timePlayed;
    }
}
