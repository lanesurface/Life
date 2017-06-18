package net.lanesurface.life;

public class Game {
    public static final int WIDTH  = 600,
                            HEIGHT = 600;
    
    public static final int HORIZONTAL_CELLS = 20,
                            VERTICAL_CELLS   = 20;
    
    public static Cell[][] cells = new Cell[VERTICAL_CELLS][HORIZONTAL_CELLS];
    
    private static Window window;
    
    /**
     * Keeps track of the amount of time that has passed since the last update.
     */
    private static long lastTime = System.nanoTime();
    /**
     * How often the game should step each second. It's quite low here because
     * it would be difficult to see the effects of updates if this was any 
     * larger.
     */
    private static final int IPS = 2;
    /**
     * The amount of time each update takes.
     */
    private static final long NS_PER_UPDATE = 1_000_000_000 / IPS;
    /**
     * The amount of ns our game is behind real time. 
     */
    private static long lag;
    
    private static boolean running = true;
    
    /**
     * When true, prevents the game from iterating, but does not prevent it
     * from drawing frames.
     */
    public static boolean paused;
    
    public static void main(String[] args) {
        initializeCells();
        
        window = new Window(
            "Conway's Game of Life",
            WIDTH, HEIGHT
        );
        window.setVisible(true);
        
        long lastFpsTime = 0;
        int updates = 0,
            fps     = 0;
        
        // Main game loop.
        while(running) {
            long now = System.nanoTime();
            long elapsed = now - lastTime;
            lastTime = now;
            lag += elapsed;
                
            lastFpsTime += elapsed;
            fps++;
            if (lastFpsTime >= 1_000_000_000) {
                System.out.println("FPS: " + fps + " Updates: " + updates);
                lastFpsTime = updates = fps = 0;
            }
                
            // Because the times that we update is fixed, the speed of the
            // simulation is unaffected by a faster/slower processing speed.
            while (lag >= NS_PER_UPDATE) {
                if (!paused) update();
                lag -= NS_PER_UPDATE;
                updates++;
            }
            
            // Render as many times as we can between updates.
            window.repaint();
        }
    }
    private static void update() {
        updateCells();
        
        for (int row = 0; row < VERTICAL_CELLS; row++)
            for (int column = 0; column < HORIZONTAL_CELLS; column++)
                cells[row][column].updateState();
    }
    private static void initializeCells() {
        for (int row = 0; row < VERTICAL_CELLS; row++)
            for (int column = 0; column < HORIZONTAL_CELLS; column++)
                cells[row][column] = new Cell();
    }
    private static void updateCells() {
        for (int row = 0; row < VERTICAL_CELLS; row++) {
            for (int column = 0; column < HORIZONTAL_CELLS; column++) {
                Cell cell = cells[row][column];
                
                int count = 0;
                for (int i = row-1; i <= row+1; i++) {
                    for (int j = column-1; j <= column+1; j++) {
                        if (i < 0 || i >= VERTICAL_CELLS ||
                            j < 0 || j >= HORIZONTAL_CELLS)
                            continue;
                        if (i == row && j == column)
                            continue;
                        if (cells[i][j].isVisible()) count++;
                    }
                }
                
                cell.setLiveNeighborCount(count);
            }
        }
    }
    public static void pause() {
        paused = !paused;
    }
}
