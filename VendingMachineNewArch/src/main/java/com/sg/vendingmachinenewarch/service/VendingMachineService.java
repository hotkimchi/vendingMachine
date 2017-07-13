/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinenewarch.service;

import com.sg.vendingmachinenewarch.dao.Change;
import com.sg.vendingmachinenewarch.dao.VendingMachineDaoPersistenceException;
import com.sg.vendingmachinenewarch.model.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface VendingMachineService {
    
    String purchaseMessage() throws VendingMachineDaoPersistenceException;
    String getChangeMessage();
    String getMessage();
    Item getItem();
    List<Item> getVendingMachineItems() throws VendingMachineDaoPersistenceException;
    Item selectItem(long id);
    BigDecimal addToTotal(String amount);
    BigDecimal getTotal();
    Map<Change, Integer> getChange() throws VendingMachineDaoPersistenceException;
}
