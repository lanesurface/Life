### Conway's Game of Life

This is a recreation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life), titled _life_. 

#### Setup 

To run the game, clone the repository and compile the classes.

```
$ git clone https://github.com/lanesurface/Life.git
$ cd Life
$ javac src/net/lanesurface/Life/Game.java -d bin/ && cd bin
$ java net.lanesurface.life.Game
```

Or download the release and run

```
$ java -jar life.jar
```

#### How to Play

The game starts with a 20 x 20 grid and all of the cells deactivated. To create a formation, click the space bar (pause) and enable cells with your mouse pointer. After enabling all of the cells in your formation, click space again to run the simulation. You can pause the simulation at any time during its execution and edit the cells.
