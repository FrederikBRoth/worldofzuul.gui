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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import worldofzuul.interfaces.IGame;
import worldofzuul.interfaces.IItem;
import worldofzuul.interfaces.IPlayer;
import worldofzuul.logic.Game;

/**
 *
 * @author SteamyBlizzard
 */
public class FXMLDocumentController implements Initializable {

    IGame game = new Game();
    IPlayer player = game.getPlayer();
    IItem item;
    Inventory inventory = new Inventory(player);
    ShopWindow shopWindows = new ShopWindow(game.getCurrentRoom().getShop(), player);
    String output;
    @FXML
    private AnchorPane inventoryPane, shopPane;
    @FXML
    private TextArea console, itemDescript, waresDescipt;
    @FXML
    private TabPane shopMode, inventoryMode;
    @FXML
    private ListView<IItem> playerItemList, playerConsumeList, waresList, consumableList;
    @FXML
    private Circle playerGui, playerHitbox;
    @FXML
    private Rectangle north, south, east, west, down, up, shop;
    HashMap<String, Rectangle> exitMap = new HashMap<>();
    @FXML
    private Pane gameWindow;
    @FXML
    private Button focusButton, inventoryButton;
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
            if (shop.getBoundsInParent().intersects(playerHitbox.getBoundsInParent())
                    && !shop.isDisabled()) {
                shopToggle();

            }
        }
        if (event.getCode() == KeyCode.B) {

        }
        if (event.getCode() == KeyCode.C) {
            inventory.removeItem();
        }
        if (event.getCode() == KeyCode.I) {
            System.out.println(game.getCurrentRoom().getDifficulty());
        }
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        mover.keyReleased(event);

    }

    @FXML
    private void inventoryToggle(ActionEvent event) {
        if (inventoryPane.isVisible()) {
            inventoryPane.setVisible(false);
            inventoryPane.setDisable(true);
        } else {
            inventoryPane.setVisible(true);
            inventoryPane.setDisable(false);
        }
    }

    @FXML
    private void buyItem(ActionEvent event) {
        if (shopMode.getTabs().get(0) == shopMode.getSelectionModel().getSelectedItem()) {
            shopWindows.buyItem();
        } else if (shopMode.getTabs().get(1) == shopMode.getSelectionModel().getSelectedItem()) {
            shopWindows.buyConsumable();
        }

    }

    @FXML
    private void getItemOptions(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY){
            System.out.println("YES");
        } else {
            System.out.println("NOOO");
        }
    }

    @FXML
    private void shopToggle() {
        if (shopPane.isVisible()) {
            shopPane.setVisible(false);
            shopPane.setDisable(true);
            shopWindows.stopShopHandler(waresList, consumableList);
            moveTimer.start();
        } else {
            shopPane.setVisible(true);
            shopPane.setDisable(false);
            shopWindows = new ShopWindow(game.getCurrentRoom().getShop(), player);
            shopWindows.shopHandler(waresList, consumableList, waresDescipt);
            moveTimer.stop();

        }
    }
    private AnimationTimer moveTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (!mover.checkBorders(playerGui, playerHitbox, gameWindow)) {
                playerGui.setCenterX(mover.getPlayerX());
                playerGui.setCenterY(mover.getPlayerY());
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
                    playerGui.setCenterY(mover.getPlayerY());
                    playerHitbox.setCenterY(mover.getPlayerYCheck());
                } else if (exit.equals(south)) {
                    output = game.goRoom("south");
                    console.appendText(output);
                    mover.setPlayerY(-gameWindow.getHeight() / 2 + 20);
                    playerGui.setCenterY(mover.getPlayerY());
                    playerHitbox.setCenterY(mover.getPlayerYCheck());
                } else if (exit.equals(west)) {
                    output = game.goRoom("west");
                    console.appendText(output);
                    mover.setPlayerX(gameWindow.getWidth() / 2 - 20);
                    playerGui.setCenterX(mover.getPlayerX());
                    playerHitbox.setCenterX(mover.getPlayerXCheck());
                } else if (exit.equals(east)) {
                    output = game.goRoom("east");
                    console.appendText(output);
                    mover.setPlayerX(-gameWindow.getWidth() / 2 + 20);
                    playerGui.setCenterX(mover.getPlayerX());
                    playerHitbox.setCenterX(mover.getPlayerXCheck());
                }
                setExits();
                setShops();
            }
        }
    }

    private void setExits() {
        for (String guiExits : exitMap.keySet()) {
            for (String exits : game.getCurrentRoom().getExits()) {
                if (guiExits.equals(exits)) {
                    exitMap.get(guiExits).setDisable(false);
                    exitMap.get(guiExits).setVisible(true);
                    game.getCurrentRoom().getExit(exits);
                    break;
                } else {
                    exitMap.get(guiExits).setDisable(true);
                    exitMap.get(guiExits).setVisible(false);
                }

            }
        }
    }

    private void setShops() {
        if (game.getCurrentRoom().isShop()) {
            shop.setVisible(true);
            shop.setDisable(false);
        } else {
            shop.setVisible(false);
            shop.setDisable(true);
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
        inventory.inventoryHandler(playerItemList, playerConsumeList, itemDescript);
        System.out.println(shopMode.getTabs().get(0));
        setExits();
        setShops();
        focusButton.requestFocus();
        moveTimer.start();
    }

}
