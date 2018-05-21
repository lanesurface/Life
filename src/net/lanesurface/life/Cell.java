package net.lanesurface.life;

public class Cell {
    private boolean alive;
    private int neighbors;
    
    public void toggleState() {
        alive = !alive;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void setLiveNeighborCount(int count) {
        neighbors = count;
    }
    
    public int getLiveNeighbors() {
        return neighbors;      
    }
    
    public void updateState() {
        if (alive && neighbors >= 2 && neighbors <= 3)
            return;
        else if (!alive && neighbors == 3)
            alive = true;
        else if (neighbors < 2 || neighbors > 3)
            alive = false;
    }
}
