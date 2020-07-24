package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    public Canvas canvas;
    @FXML
    public Text score;
    @FXML
    private Button button;
    @FXML
    public Text finalText;

    public Affine affine;

    public Scene scene;

    public Game game;

    public KeyThread keyThread;

    public Controller() {
        canvas = new Canvas(600, 600);
        affine = new Affine();
        affine.appendScale(30, 30);

        game = new Game(20, 20);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void start () {
        // Bind keys
        scene = button.getScene();

        keyThread = new KeyThread(this);
        keyThread.start();

        // draw
        draw();
    }

    public void draw () {
        score.setText("Score : " + game.score);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setTransform(affine);

        // fill all Rectangle
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, 600, 600);


        graphicsContext.setFill(Color.BLUE);
        // paint cells
        for (int x = 0; x < this.game.width; x++) {
            for (int y = 0; y < this.game.height; y++) {
                int type = game.getCell(x, y);
                if (type == 1) {
                    graphicsContext.fillRect(x, y, 1, 1);
                }
            }
        }


        graphicsContext.setFill(Color.GREEN);
        // paint snake
        for (Game.Cell cell : game.snakeCells) {
            graphicsContext.fillRect(cell.x, cell.y, 1, 1);
        }


        // draw food
        graphicsContext.setFill(game.foodColor);
        graphicsContext.fillRect(game.food.x, game.food.y, 1, 1);


        // paint lines (#)
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(0.05);
        for (int x = 0; x <= this.game.width; x++) {
            graphicsContext.strokeLine(x, 0, x, game.height);
        }

        for (int y = 0; y <= this.game.height; y++) {
            graphicsContext.strokeLine(0, y, game.width, y);
        }
    }
}
