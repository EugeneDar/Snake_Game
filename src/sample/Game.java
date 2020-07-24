package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Game {
    int width;
    int height;

    int amountOfFood = -1;
    int speed = 250;
    int score = -10;

    int[][] matrix;
    // 0 - empty
    // 1 - wall or body
    // 2 - fruit

    List<Cell> snakeCells = new ArrayList<>();

    Cell food;
    Color foodColor;

    public Way way = Way.RIGHT;

    boolean gameOver = false;

    public static class Cell {
        int x;
        int y;
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Game(int width, int height) {
        // constructor
        this.width = width;
        this.height = height;
        matrix = new int[height][width];

        // fill default
        for (int[] ints : matrix) {
            Arrays.fill(ints, 0);
        }

        // fill wals
        for (int x = 0;x < width;x++) {
            matrix[0][x] = 1;
            matrix[height - 1][x] = 1;
        }
        for (int y = 0;y < height;y++) {
            matrix[y][0] = 1;
            matrix[y][width - 1] = 1;
        }

        // initialize snake
        snakeCells.add(new Cell(width / 2 - 1, height / 2));
        snakeCells.add(new Cell(width / 2, height / 2));
        snakeCells.add(new Cell(width / 2 + 1, height / 2));

        // add food
        generateFood();
    }

    public void step () {

        if (amountOfFood > 0) {
            amountOfFood--;
        } else {
            snakeCells.remove(0);
        }

        Cell currentHead = snakeCells.get(snakeCells.size() - 1);

        switch (way) {
            case RIGHT -> setNewCellOfSnake(currentHead.x + 1, currentHead.y);
            case UP -> setNewCellOfSnake(currentHead.x, currentHead.y - 1);
            case LEFT -> setNewCellOfSnake(currentHead.x - 1, currentHead.y);
            case DOWN -> setNewCellOfSnake(currentHead.x, currentHead.y + 1);
        }

        Cell newHead = snakeCells.get(snakeCells.size() - 1);
        if (food.x == newHead.x && food.y == newHead.y) {
            generateFood();
        }




    }

    public void setNewCellOfSnake (int x, int y) {
        if (x == 0 || y == 0 || x >= width - 1 || y >= height - 1) {
            gameOver = true;
            return;
        }

        for (Cell cell : snakeCells) {
            if (cell.x == x && cell.y == y) {
                gameOver = true;
                return;
            }
        }

        snakeCells.add(new Cell(x, y));
    }

    public void printMatrix () {
        for (int i = 0;i < height;i++) {
            for (int j = 0;j < width;j++) {
                int cell = getCell(j, i);
                if (cell == 0) {
                    System.out.print('.');
                } else if (cell == 1) {
                    System.out.print('#');
                } else if (cell == 2) {
                    System.out.print('O');
                }
            }
            System.out.println();
        }
    }

    public int getCell (int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return 1;
        }
        return matrix[y][x];
    }

    public void setWay(Way way) {
        if (this.way == Way.UP && way != Way.DOWN) {
            this.way = way;
        }
        if (this.way == Way.LEFT && way != Way.RIGHT) {
            this.way = way;
        }
        if (this.way == Way.DOWN && way != Way.UP) {
            this.way = way;
        }
        if (this.way == Way.RIGHT && way != Way.LEFT) {
            this.way = way;
        }
    }

    public void generateFood () {
        amountOfFood++;
        score += 10;
        speed -= 10;

        boolean good;

        while (true) {
            good = true;
            int x = (int) (Math.random() * (width - 2) + 1);
            int y = (int) (Math.random() * (height - 2) + 1);
            for (Cell cell : snakeCells) {
                if (cell.x == x && cell.y == y) {
                    good = false;
                    break;
                }
            }
            if (good) {
                food = new Cell(x, y);
                break;
            }
        }

        Color[] colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.VIOLET, Color.PURPLE};
        foodColor = colors[(int) (new Date().getTime() % colors.length)];
    }

}
