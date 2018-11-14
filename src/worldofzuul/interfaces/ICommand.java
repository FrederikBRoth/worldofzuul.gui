/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul.interfaces;

import worldofzuul.logic.CommandWord;

/**
 *
 * @author SteamyBlizzard
 */
public interface ICommand {
    public CommandWord getCommandWord();
    public String getSecondWord();
    public boolean isUnknown();
    public boolean hasSecondWord();
}
