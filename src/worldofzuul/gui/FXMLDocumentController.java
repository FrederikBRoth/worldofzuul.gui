/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul.gui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import worldofzuul.interfaces.IGame;
import worldofzuul.logic.Game;

/**
 *
 * @author SteamyBlizzard
 */
public class FXMLDocumentController implements Initializable {

    IGame game = new Game();
    String output;
    @FXML
    private TextArea console;
    @FXML
    private Circle player, playerHitbox;
    @FXML
    private Rectangle north, south, east, west, down, up;
    HashMap<String, Rectangle> exitMap = new HashMap<>();
    @FXML
    private Pane gameWindow;
    @FXML
    private Button focusButton;
    private Mover mover = new Mover();

    @FXML
    private void keyPressed(KeyEvent event) {
        mover.keyPressed(event);
        if (event.getCode() == KeyCode.E) {
            if (exitMap.get("down").getBoundsInParent().intersects(playerHitbox.getBoundsInParent())
                    && !exitMap.get("down").isDisabled()) {
                output = game.goRoom("down");
                console.appendText(output);
            }
            if (exitMap.get("up").getBoundsInParent().intersects(playerHitbox.getBoundsInParent())
                    && !exitMap.get("up").isDisabled()) {
                output = game.goRoom("up");
                console.appendText(output);
            }
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        mover.keyReleased(event);
    }
    private AnimationTimer moveTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (!checkBorders()) {
                player.setCenterX(mover.getPlayerX());
                player.setCenterY(mover.getPlayerY());
                checkExits();
            }

        }
    };

    private void checkExits() {
        for (Rectangle exit : exitMap.values()) {
            if (exit.getBoundsInParent().intersects(playerHitbox.getBoundsInParent())
                    && !exit.isDisabled()) {
                if (exit.equals(north)) {
                    output = game.goRoom("north");
                    console.appendText(output);
                    mover.setPlayerY(gameWindow.getHeight() / 2 - 20);
                    player.setCenterY(mover.getPlayerY());
                    playerHitbox.setCenterY(mover.getPlayerYCheck());
                } else if (exit.equals(south)) {
                    output = game.goRoom("south");
                    console.appendText(output);
                    mover.setPlayerY(-gameWindow.getHeight() / 2 + 20);
                    player.setCenterY(mover.getPlayerY());
                    playerHitbox.setCenterY(mover.getPlayerYCheck());
                } else if (exit.equals(west)) {
                    output = game.goRoom("west");
                    console.appendText(output);
                    mover.setPlayerX(gameWindow.getWidth() / 2 - 20);
                    player.setCenterX(mover.getPlayerX());
                    playerHitbox.setCenterX(mover.getPlayerXCheck());
                } else if (exit.equals(east)) {
                    output = game.goRoom("east");
                    console.appendText(output);
                    mover.setPlayerX(-gameWindow.getWidth() / 2 + 20);
                    player.setCenterX(mover.getPlayerX());
                    playerHitbox.setCenterX(mover.getPlayerXCheck());
                }
                setExits();
            }
        }
    }

    private void setExits() {
        for (String guiExits : exitMap.keySet()) {
            for (String exits : game.getCurrentRoom().getExits()) {
                if (guiExits.equals(exits)) {
                    exitMap.get(guiExits).setDisable(false);
                    exitMap.get(guiExits).setVisible(true);
                    break;
                } else {
                    exitMap.get(guiExits).setDisable(true);
                    exitMap.get(guiExits).setVisible(false);
                }
            }
        }

    }

    private boolean checkBorders() {
        playerHitbox.setCenterX(mover.getPlayerXCheck());
        playerHitbox.setCenterY(mover.getPlayerYCheck());
        double pHMinY = playerHitbox.getBoundsInParent().getMinY();
        double pHMinX = playerHitbox.getBoundsInParent().getMinX();
        double pHMaxY = playerHitbox.getBoundsInParent().getMaxY();
        double pHMaxX = playerHitbox.getBoundsInParent().getMaxX();
        if (pHMinX < 0 || pHMaxX > gameWindow.getWidth() || pHMinY < 0
                || pHMaxY > gameWindow.getHeight()) {
            playerHitbox.setCenterX(player.getCenterX());
            playerHitbox.setCenterY(player.getCenterY());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        console.appendText(game.printWelcome());
        exitMap.put("north", north);
        exitMap.put("south", south);
        exitMap.put("west", west);
        exitMap.put("east", east);
        exitMap.put("down", down);
        exitMap.put("up", up);
        setExits();
        focusButton.requestFocus();
        moveTimer.start();
    }

}
