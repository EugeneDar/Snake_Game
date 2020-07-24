package sample;

import javafx.scene.input.KeyCode;

public class KeyThread extends Thread{
    Controller controller;

    public KeyThread(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run () {
        initialize();
        while (true) {
            try {
                if (controller.game.gameOver) {
                    throw new Exception();
                }
                Thread.sleep(controller.game.speed);
                controller.game.step();
                controller.draw();
            } catch (Exception e) {
                controller.finalText.setText("Game Over");
                System.out.println("End of game");
                return;
            }
        }
    }

    public void initialize () {
        controller.scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.W)) {
                controller.game.setWay(Way.UP);
                return;
            }
            if (keyCode.equals(KeyCode.A)) {
                controller.game.setWay(Way.LEFT);
                return;
            }
            if (keyCode.equals(KeyCode.S)) {
                controller.game.setWay(Way.DOWN);
                return;
            }
            if (keyCode.equals(KeyCode.D)) {
                controller.game.setWay(Way.RIGHT);
            }
        });
    }
}
