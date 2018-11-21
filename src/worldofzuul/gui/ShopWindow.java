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
import worldofzuul.interfaces.IItem;
import worldofzuul.interfaces.IItemGenerator;
import worldofzuul.interfaces.IPlayer;
import worldofzuul.interfaces.IShop;
import worldofzuul.logic.Item;
import worldofzuul.logic.ItemGenerator;
import worldofzuul.logic.Player;

/**
 *
 * @author SteamyBlizzard
 */
public class ShopWindow {
    IItem item;
    IItemGenerator itemGenerator= new ItemGenerator();
    IShop shop;
    IPlayer player;

    public ShopWindow(IShop shop, IPlayer player) {
        this.shop = shop;
        this.player = player;
    }
    
    
    public void shopHandler(ListView waresList, TextArea itemDescript){
        waresList.setItems(shop.getBuyableList());
        waresList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IItem>() {
            @Override
            public void changed(ObservableValue<? extends IItem> observable, IItem oldValue, IItem newValue) {
                waresList.refresh();
                itemDescript.clear();
                itemDescript.appendText(newValue.getStats().toString());
                item = newValue;
            }
        });
    }
    
    public void buyItem(){
        shop.buyWare((Item) item, (Player) player);
    }
}
