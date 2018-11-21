
package worldofzuul.logic;

import worldofzuul.interfaces.IConsumable;


public class Consumable implements IConsumable{
    
    private String name;
    private int healing;

    public Consumable(int healing) {
        this.healing = healing;
        this.name = "Healing potion";
    }
    public Consumable(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealing() {
        return healing;
    }

    public void setHealing(int healing) {
        this.healing = healing;
    }
    
    @Override
    public String getDescription(){
        String toText = "Healing potion:\n\theals for " + getHealing();
        return toText;
    }
    @Override
    public String toString() {
        return this.name;
    }
    

}
