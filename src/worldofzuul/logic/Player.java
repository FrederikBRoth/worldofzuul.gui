package worldofzuul.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.interfaces.IConsumable;
import worldofzuul.interfaces.IItem;
import worldofzuul.interfaces.IPlayer;

public class Player implements IPlayer{

    private String name;
    private int maxHp = 100;

    private int hp;
    private Stats stats;
    private int gold = 100;
    private ObservableList<IItem> inventory = FXCollections.observableArrayList();
    private ObservableList<IConsumable> potInventory = FXCollections.observableArrayList();

    public Player(String name) {
        this.name = name;
        this.stats = new Stats();
        this.hp = maxHp;
    }

    @Override
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void addHp(int amount) {
        this.hp += amount;
    }
    
    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    public int getEndurance() {
        return this.stats.getEndurance();
    }

    public void addEndurance(int amount) {
        this.stats.setEndurance(this.stats.getEndurance() + amount);
    }

    public int getStrength() {
        return this.stats.getStrength();
    }

    public void addStrength(int amount) {
        this.stats.setStrength(this.stats.getStrength() + amount);
    }

    public int getAgility() {
        return this.stats.getAgility();
    }

    public void addAgility(int amount) {
        this.stats.setAgility(this.stats.getAgility() + amount);
    }

    public int getIntelligence() {
        return this.stats.getIntelligence();
    }

    public void addIntelligence(int amount) {
        this.stats.setIntelligence(this.stats.getIntelligence() + amount);
    }

//    public void getInventory() {
//        if (inventory.isEmpty()) {
//            System.out.println("You haven't collected any items yet, go defeat math monster to get some");
//        } else {
//            for (Item item : inventory) {
//                System.out.print(inventory.indexOf(item));
//                System.out.println(" " + item);
//                System.out.println(item.getStats());
//            }
//        }
//        if (potInventory.isEmpty()) {
//            System.out.println("You have 0 healing potions");
//        } else {
//            for (Consumable pot : potInventory) {
//                System.out.println("You have " + potInventory.size() + " healing potions");
//            }
//        }
//    }
    @Override
    public ObservableList<IItem> getInventory() {
        return inventory;
    }
     
    @Override
    public ObservableList<IConsumable> getPotInventory() {
        return potInventory;
    }

    @Override
    public String toString() {
        String statSummary = getName() + "'s Stats:";
        statSummary += "\n\tHealth: " + getHp();
        statSummary += "\n\tAgility: " + getAgility();
        statSummary += "\n\tStrength: " + getStrength();
        statSummary += "\n\tIntelligence: " + getIntelligence();
        statSummary += "\n\tEndurance: " + getEndurance();
        return statSummary;
    }

    @Override
    public void pickupItem(Item item) {
        inventory.add(item);
        addAgility(item.getStats().getAgility());
        addStrength(item.getStats().getStrength());
        addIntelligence(item.getStats().getIntelligence());
        addEndurance(item.getStats().getEndurance());
    }

    public void pickupPot(Consumable healingPot) {
        potInventory.add(healingPot);
    }
    public void dropPot(Consumable healingPot) {
        potInventory.remove(healingPot);
    }

    @Override
    public void dropItem(Item item) {
        inventory.remove(item);
        addAgility(-item.getStats().getAgility());
        addStrength(-item.getStats().getStrength());
        addIntelligence(-item.getStats().getIntelligence());
        addEndurance(-item.getStats().getEndurance());
    }

    @Override
    public void useHealing(Consumable pot) {
        addHp(pot.getHealing());
        getPotInventory().remove(pot);
    }

}
