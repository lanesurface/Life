package net.lanesurface.life;

public class Cell {
    private boolean visible;
    private int neighbors;
    
    public void toggleVisibility() {
        visible = !visible;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setLiveNeighborCount(int count) {
        neighbors = count;
    }
    public int getLiveNeighbors() {
        return neighbors;      
    }
    public void updateState() {
        if (visible && neighbors >= 2 && neighbors <= 3)
            return;
        else if (!visible && neighbors == 3)
            visible = true;
        else if (neighbors < 2 || neighbors > 3)
            visible = false;
    }
}
