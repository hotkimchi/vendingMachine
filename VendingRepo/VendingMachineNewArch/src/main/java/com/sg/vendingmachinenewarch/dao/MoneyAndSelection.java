/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinenewarch.dao;

import com.sg.vendingmachinenewarch.model.Item;
import java.math.BigDecimal;

/**
 *
 * @author apprentice
 */
public class MoneyAndSelection {
    
    private BigDecimal moneyInsreted;
    private Item currentItem;

    public BigDecimal getMoneyInsreted() {
        return moneyInsreted;
    }

    public void setMoneyInsreted(String moneyInsreted) {
        this.moneyInsreted = new BigDecimal(moneyInsreted);
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }
    
    
}
