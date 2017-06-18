package net.lanesurface.life;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    
    public Canvas canvas;
    
    public Window(String name, int width, int height) {
        super(name);
        
        setLocationRelativeTo(null);
        setSize(width, height);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        canvas = new Canvas();
        add(canvas);
        
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent ke) {
                System.out.println(ke.getKeyCode());
                if (ke.getKeyCode() == 0) 
                    Game.pause();
            }
            public void keyReleased(KeyEvent ke) { }
            public void keyPressed(KeyEvent ke) { }
        });
        
        canvas.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent me) { }
            public void mousePressed(MouseEvent me) { }
            public void mouseExited(MouseEvent me) { }
            public void mouseEntered(MouseEvent me) { }
            public void mouseClicked(MouseEvent me) {
                if (Game.paused) {
                    int x = me.getX(),
                        y = me.getY();
                        
                    canvas.selectCell(x, y);
                }
            }
        });
    }
    /**
     * Handles rendering of the cells.
     */
    private static class Canvas extends JPanel {
        private static int cellWidth, cellHeight;
        
        public Canvas() {
            super();
        }
        @Override
        public void paint(Graphics graphics) {
            super.paint(graphics);
            
            cellWidth = getWidth() / Game.HORIZONTAL_CELLS;
            cellHeight = getHeight() / Game.VERTICAL_CELLS;
            
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            
            graphics.setColor(Color.WHITE);
            
            for (int row = 0; row < Game.VERTICAL_CELLS; row++) {
                for (int column = 0; column < Game.HORIZONTAL_CELLS; column++) {
                    Cell cell = Game.cells[row][column];
                    
                    int x = column * cellWidth;
                    int y = row * cellHeight;
                    
                    if (cell.isVisible()) {
                        graphics.fillRect(x, y, cellWidth, cellHeight);
                    }
                    else graphics.drawRect(x, y, cellWidth, cellHeight);
                }
            }
        }
        public void selectCell(int x, int y) {
            int row    = y / cellHeight,
                column = x / cellWidth;
            
            System.out.println(x + " " + y + " " + cellWidth + " " + cellHeight);
                
            Game.cells[row][column].toggleVisibility();
        }
    }
}
