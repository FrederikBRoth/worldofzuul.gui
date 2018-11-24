/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import worldofzuul.interfaces.IConsumable;
import worldofzuul.interfaces.IGame;
import worldofzuul.interfaces.IItem;
import worldofzuul.interfaces.IItemGenerator;
import worldofzuul.interfaces.IPlayer;
import worldofzuul.logic.Item;
import worldofzuul.logic.ItemGenerator;

/**
 *
 * @author SteamyBlizzard
 */
public class Inventory {
    IItem item;
    IItemGenerator itemGenerator = new ItemGenerator();
    IConsumable consumable;
    IPlayer player;
   
    public Inventory(IPlayer player){
        this.player = player;
    }
    public void inventoryHandler(ListView playerItemList, ListView playerConsumeList, TextArea itemDescript){
        playerItemList.setItems(player.getInventory());
        playerItemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IItem>() {
            @Override
            public void changed(ObservableValue<? extends IItem> observable, IItem oldValue, IItem newValue) {
                itemDescript.clear();
                itemDescript.appendText(newValue.getStats().toString());
                item = newValue;
            }
        });
        playerConsumeList.setItems(player.getPotInventory());
        playerConsumeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IConsumable>() {
            @Override
            public void changed(ObservableValue<? extends IConsumable> observable, IConsumable oldValue, IConsumable newValue) {
                itemDescript.clear();
                itemDescript.appendText(newValue.getDescription());
                consumable = newValue;
            }
        });
    }
    public void removeItem(){
        player.dropItem((Item) item);
    }
    public void addItem(){
        player.pickupItem(itemGenerator.generateItem(1));
    }
    public void toggleInventoryPane(Pane inventoryPane, MouseEvent event){
        inventoryPane.setVisible(true);
        inventoryPane.setDisable(false);
        inventoryPane.setLayoutX(event.getSceneX());
        inventoryPane.setLayoutY(event.getSceneY());
        System.out.println(event.getSceneX());
        System.out.println(event.getSceneY());
    }
    

    
}
   
