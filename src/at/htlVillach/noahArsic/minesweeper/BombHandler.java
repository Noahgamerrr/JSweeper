package at.htlVillach.noahArsic.minesweeper;

public class BombHandler {
    Game game;

    public BombHandler(Game game) {
        this.game = game;
    }

    public int getBombsInRange(int ia, int ja) {
        int bombs = 0;
        if (!game.field[ia][ja]) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (ia + i >= 0 && ia + i < game.field.length && ja + j >= 0 && ja + j < game.field[ia].length && !(i == 0 && j == 0) && game.field[ia + i][ja + j]) bombs++;
                }
            }
        }
        return bombs;
    }

    public void revealEmptyFields(int ia, int ja) {
        if (!game.field[ia][ja]) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (ia + i >= 0 && ia + i < game.field.length && ja + j >= 0 && ja + j < game.field[ia].length && !(i == 0 && j == 0) && !game.field[ia + i][ja + j] && game.visible[ia + i][ja + j] != -1) {
                        if (game.visible[ia + i][ja + j] == 1) Game.setFlagCounter(Game.getFlagCounter() + 1);
                        game.visible[ia + i][ja + j] = -1;
                        if (getBombsInRange(ia + i, ja + j) == 0) revealEmptyFields(ia + i, ja + j);
                    }
                }
            }
        }
    }
}
