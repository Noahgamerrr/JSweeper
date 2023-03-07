package at.htlVillach.noahArsic.minesweeper;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 750, HEIGHT = WIDTH;
    private static int flagCounter = 70;
    private Thread thread;
    private boolean running = false;
    private final Window window;
    public boolean[][] field = new boolean[20][20];
    public int[][] visible = new int[20][20];
    public int[][] bombsInRange = new int[20][20];
    private boolean firstFieldRevealed = false;
    public static final ImageLoader IMG_LOAD = new ImageLoader();
    public static final BufferedImage IMG_BOMB = IMG_LOAD.loadImage("Sprites/bomb.png");
    public static final BufferedImage IMG_FLAG = IMG_LOAD.loadImage("Sprites/flag.png");
    public static final HUD H = new HUD();
    private static BombHandler gen;
    private final Menu menu = new Menu();
    private final RunningGame rg = new RunningGame(this);
    private static final EndOfGame EOG = new EndOfGame();
    private static GAMESTATE state = GAMESTATE.Menu;

    public enum GAMESTATE {
        Menu,
        Game,
        EndOfGame
    }

    public Game() {
        this.addMouseListener(new MouseInput(this));
        gen = new BombHandler(this);
        window = new Window(HEIGHT, WIDTH, "Minesweeper", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        double maxFPS = 60.0;
        long renderLastTime = System.nanoTime();
        double renderNs = 1000000000 / maxFPS;
        double renderDelta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            now = System.nanoTime();
            renderDelta += (now - renderLastTime) / renderNs;
            renderLastTime = now;
            while(running && renderDelta >= 1){
                render();
                frames++;
                renderDelta--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                HUD.setFrames(frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (state == GAMESTATE.Game) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.black);
            g.fillRect(50, 50, 620, 620);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
        if (state == GAMESTATE.Menu) menu.render(g);
        else if (state == GAMESTATE.Game) rg.render(g);
        else EOG.render(g);
        g.dispose();
        bs.show();
    }

    public void initializeGame() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                field[i][j] = false;
                visible[i][j] = 0;
            }
        }
        flagCounter = 70;
        firstFieldRevealed = false;
    }

    public static int getFlagCounter() {
        return flagCounter;
    }

    public static void setFlagCounter(int flagCounter) {
        Game.flagCounter = flagCounter;
    }

    public boolean getFirstFieldRevealed() {
        return firstFieldRevealed;
    }

    public void setFirstFieldRevealed(boolean firstFieldRevealed) {
        this.firstFieldRevealed = firstFieldRevealed;
    }

    public static BombHandler getGen() {
        return gen;
    }

    public static GAMESTATE getState() {
        return state;
    }

    public static void setState(GAMESTATE state) {
        Game.state = state;
    }

    public static void main(String[] args) {
	// write your code here
        new Game();
    }
}