/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinenewarch.dao;

import com.sg.vendingmachinenewarch.model.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface VendingMachineDao {
    
    List<Item> getVendingMachineItems() throws VendingMachineDaoPersistenceException;
    BigDecimal addToTotal(BigDecimal amount);
    Map<Change, Integer> getChange(Item item, BigDecimal total) throws VendingMachineDaoPersistenceException;
    Map<Change, Integer> getChangeMap();
    Item getItem();
    Item selectItem(long id);
    BigDecimal getTotal();
    Item removeOneStock() throws VendingMachineDaoPersistenceException;
}
