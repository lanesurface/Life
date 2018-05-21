package net.lanesurface.life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private Game game;
    
    public Window(Game game) {
        super(game.config.title);
        
        this.game = game;
        Game.GameHints config = game.config; // For convenience.
        
        setLocationByPlatform(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        getContentPane().setPreferredSize(new Dimension(config.width, 
                                                        config.height));
        pack();
        
        Canvas canvas = new Canvas(game);
        add(canvas);
        
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) 
                    game.pause();
            }
        });
        
        canvas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouse) {
                if (game.isPaused()) {
                    int x = mouse.getX(),
                        y = mouse.getY();
                        
                    canvas.selectCell(x, y);
                }
            }
        });
    }
    /**
     * Handles rendering of the cells.
     */
    private static class Canvas extends JPanel {
        private final int cellWidth, cellHeight,
                          rows, cols;
        
        private Game game;
        
        public Canvas(Game game) {
            super();
            
            this.game = game;
            Game.GameHints conf = game.config;
            
            rows = conf.rows;
            cols = conf.cols;
            cellWidth = conf.width / cols;
            cellHeight = conf.height / rows;
        }
        
        @Override
        public void paint(java.awt.Graphics graphics) {
            super.paint(graphics);
            
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < cols; column++) {
                    Cell cell = game.getCell(row, column);
                    
                    int x = column * cellWidth,
                        y = row * cellHeight;
                    
                    if (cell.isAlive()) {
                        graphics.setColor(new Color(150, 65, 200));
                        graphics.fillRect(x, y, cellWidth, cellHeight);
                    }
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.drawRect(x, y, cellWidth, cellHeight);
                }
            }
        }
        
        public void selectCell(int x, int y) {
            int row = y / cellHeight,
                column = x / cellWidth;
            
            game.getCell(row, column).toggleState();
        }
    }
}
