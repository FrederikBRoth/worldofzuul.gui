/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import worldofzuul.interfaces.IItem;
import worldofzuul.interfaces.IPlayer;
import worldofzuul.logic.Item;

/**
 *
 * @author SteamyBlizzard
 */
public class Inventory {
    IItem item;
    IPlayer player;
   
    public Inventory(IPlayer player){
        this.player = player;
    }
    public void inventoryHandler(ListView invList){
        invList.setItems(player.getInventory());
        invList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IItem>() {
            @Override
            public void changed(ObservableValue<? extends IItem> observable, IItem oldValue, IItem newValue) {
                System.out.println(newValue.getStats());
                item = newValue;
            }
        });
    }
    public void removeItem(){
        player.dropItem(item);
    }
    public void addItem(){
        player.pickupItem((Item) item);
    }
}
   