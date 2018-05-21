
package net.lanesurface.life;

public class Game {
    GameHints config;
    
    private Cell[][] cells;
    
    /**
     * Keeps track of the amount of time that has passed since the last update.
     */
    private long lastTime = System.nanoTime();
    
    /**
     * The amount of time each update takes.
     */
    private final long nsPerUpdate;
    /**
     * The amount of ns our game is behind real time. 
     */
    private long lag;
    
    private boolean running = true,
    /**
     * When true, prevents the game from iterating, but does not prevent it
     * from drawing frames.
     */
        paused;
    
    /**
     * Instances of this class hold configuration information (user preferences)
     * about the game's state.
     */
    public static class GameHints {
        int width, height,
            cols, rows;
        String title = "Conway's Game of Life";
        
        /**
         * How often the game should step each second. It's quite low here because
         * it would be difficult to see the effects of updates if this was any 
         * larger.
         */
        int IPS = 10;
        
        public GameHints(int width, int height, int cols, int rows) {
            this.width = width;
            this.height = height;
            this.cols = cols;
            this.rows = rows;
        }
    }
    
    public Game(GameHints config) {
        this.config = config;
        nsPerUpdate = 1_000_000_000 / config.IPS;
        
        cells = new Cell[config.rows][config.cols];
        initializeCells(cells);
        
        Window window = new Window(this);
        window.setVisible(true);
        
        while(running) {
            long now = System.nanoTime();
            long elapsed = now - lastTime;
            lastTime = now;
            lag += elapsed;
                
            // Because the times that we update is fixed, the speed of the
            // simulation is unaffected by a faster/slower processing speed.
            // This configuration gives priority to updating and renders when
            // the processor has caught up.
            while (lag >= nsPerUpdate) {
                // Only update the game if the simulation is running; otherwise,
                // the simulation will continue to draw frames (render) without
                // updating the simulation.
                if (!paused) update();
                lag -= nsPerUpdate;
            }
            
            window.repaint();
        }
    }
    
    private void initializeCells(Cell[][] c) {
        for (int row = 0; row < config.rows; row++)
            for (int column = 0; column < config.cols; column++)
                // A new cell object must be created for every element in the
                // array. Each object stores the on/off state and houses the
                // logic for that state (since it's dependent on its position
                // in the array).
                cells[row][column] = new Cell();
    }
    
    private void update() {
        cells = getUpdatedLiveNeighborCount(cells);
        
        // Now that ALL of the neighbors have been accounted for, it's okay to
        // update the on/off state.
        cells = getUpdatedCellStates(cells);
    }
    
    private Cell[][] getUpdatedLiveNeighborCount(Cell[][] c) {
        Cell[][] updated = c;
        for (int row = 0; row < config.rows; row++) {
            for (int col = 0; col < config.cols; col++) {
                Cell cell = updated[row][col];
                cell.setLiveNeighborCount(getAliveNeighborCount(updated,
                    row, col));
            }
        }
        return updated;
    }
    
    private Cell[][] getUpdatedCellStates(Cell[][] c) {
        Cell[][] updated = c;
        for (int row = 0; row < config.rows; row++)
            for (int column = 0; column < config.cols; column++)
                updated[row][column].updateState();
        return updated;
    }
    
    public int getAliveNeighborCount(Cell[][] c, int row, int col) {
        int count = 0;
        
        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (i < 0 || i >= config.rows ||
                    j < 0 || j >= config.cols) continue;
                if (i == row && j == col) continue;
                if (c[i][j].isAlive())  count++;
            }
        }
        
        return count;
    }
    
    public Cell[][] getCells() {
        return cells;
    }
    
    public Cell getCell(int row, int column) {
        return cells[row][column];
    }
    
    public void pause() {
        paused = !paused;
    }
    
    public boolean isPaused() {
        return paused;
    }
}
