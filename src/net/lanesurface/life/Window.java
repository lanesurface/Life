package net.lanesurface.life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    public Canvas canvas;
    
    public Window(String name, int width, int height) {
        super(name);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        
        canvas = new Canvas(width, height);
        add(canvas);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) 
                    Game.pause();
            }
        });
        
        canvas.addMouseListener(new MouseAdapter() {
            @Override
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
        private int cellWidth, cellHeight;
        
        public Canvas(int width, int height) {
            super();
            
            cellWidth = width / Game.HORIZONTAL_CELLS;
            cellHeight = height / Game.VERTICAL_CELLS;
        }
        @Override
        public void paint(Graphics graphics) {
            super.paint(graphics);
            
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            
            for (int row = 0; row < Game.VERTICAL_CELLS; row++) {
                for (int column = 0; column < Game.HORIZONTAL_CELLS; column++) {
                    Cell cell = Game.cells[row][column];
                    
                    int x = column * cellWidth,
                        y = row * cellHeight;
                    
                    if (cell.isVisible()) {
                        graphics.setColor(Color.BLACK);
                        graphics.fillRect(x, y, cellWidth, cellHeight);
                    }
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.drawRect(x, y, cellWidth, cellHeight);
                }
            }
        }
        public void selectCell(int x, int y) {
            int row    = y / cellHeight,
                column = x / cellWidth;
                
            Game.cells[row][column].toggleVisibility();
        }
    }
}
