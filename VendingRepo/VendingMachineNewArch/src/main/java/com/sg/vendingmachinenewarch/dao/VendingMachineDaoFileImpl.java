/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinenewarch.dao;

import com.sg.vendingmachinenewarch.model.Item;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private List<Item> items = new ArrayList();
    private BigDecimal moneyInserted = new BigDecimal("0.00");
    private Map<String, String> itemAndCost = new HashMap();
    private Map<Change, Integer> changeMap = new HashMap();
    private Item currentItem = new Item();
    public static final String DELIMITER = "::";

//    public VendingMachineDaoFileImpl() {
//        changeMap.put(Change.QUARTER, 0);
//        changeMap.put(Change.DIME, 0);
//        changeMap.put(Change.NICKEL, 0);
//        changeMap.put(Change.PENNY, 0);
//    }

    private void loadStock() throws VendingMachineDaoPersistenceException {
        Scanner myScanner;
        items.clear();
        File f = new File(getClass().getClassLoader().getResource("vendingstock.txt").getFile());
        try {
            myScanner = new Scanner(new BufferedReader(
                    new FileReader(f)));
        } catch (FileNotFoundException e) {
            throw new VendingMachineDaoPersistenceException("-_- Could not load stock "
                    + "data into memory.", e);
        }

        String currentLine;
        String[] currentToken;
        while (myScanner.hasNextLine()) {
            currentLine = myScanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            Item currentItem = new Item();
            String stringId = currentToken[0];
            long id = Long.parseLong(stringId);
            currentItem.setId(id);
            currentItem.setName(currentToken[1]);
            currentItem.setPrice(currentToken[2]);
            String stringQ = currentToken[3];
            int quantity = Integer.parseInt(stringQ);
            currentItem.setQuantity(quantity);
            items.add(currentItem);
        }
        myScanner.close();
    }
    
    private void writeStock() throws VendingMachineDaoPersistenceException {
        
        PrintWriter out;
        File f = new File(getClass().getClassLoader().getResource("vendingstock.txt").getFile());
        try {
            out = new PrintWriter(new FileWriter(f));
        } catch (IOException e) {
            throw new VendingMachineDaoPersistenceException("Could not save stock data.", e);
        }

        
        for (Item i : items) {
            out.println(i.getId() + DELIMITER + i.getName() + DELIMITER + i.getPrice()
                    + DELIMITER + i.getQuantity());

            out.flush();
        }
        out.close();
    }
    
    @Override
    public Item removeOneStock() throws VendingMachineDaoPersistenceException{
        int stock = currentItem.getQuantity();
        currentItem.setQuantity(stock - 1);
        int index = (int) currentItem.getId();
        items.set(index - 1, currentItem);
        writeStock();
        currentItem = new Item();
        return currentItem;
    }

    @Override
    public List<Item> getVendingMachineItems() throws VendingMachineDaoPersistenceException {
        loadStock();
        return items;
    }

    @Override
    public BigDecimal addToTotal(BigDecimal amount) {
        changeMap.clear();
        moneyInserted = moneyInserted.add(amount).setScale(2, RoundingMode.HALF_UP);
        return moneyInserted;
    }

    @Override
    public BigDecimal getTotal() {
        return moneyInserted;
    }

    @Override
    public Map<Change, Integer> getChangeMap() {
        return changeMap;
    }

    @Override
    public Map<Change, Integer> getChange(Item item, BigDecimal total) throws VendingMachineDaoPersistenceException {
        changeMap.clear();
        BigDecimal zeroSum = new BigDecimal("0.00");

        if (total.compareTo(item.getPrice()) >= 0) {
            total = total.subtract(item.getPrice().setScale(2, RoundingMode.HALF_UP));
        }
        if (changeMap.isEmpty()) {
            changeMap.put(Change.QUARTER, 0);
            changeMap.put(Change.DIME, 0);
            changeMap.put(Change.NICKEL, 0);
            changeMap.put(Change.PENNY, 0);
        }
        while (total.compareTo(zeroSum) > 0) {

            if (total.setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.25")) >= 0) {
                int appendValue = changeMap.get(Change.QUARTER);
                appendValue++;
                changeMap.put(Change.QUARTER, appendValue);
                total = total.subtract(new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP));
            } else if (total.setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.10")) >= 0) {
                int appendValue = changeMap.get(Change.DIME);
                appendValue++;
                changeMap.put(Change.DIME, appendValue);
                total = total.subtract(new BigDecimal("0.10").setScale(2, RoundingMode.HALF_UP));
            } else if (total.setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.05")) >= 0) {
                int appendValue = changeMap.get(Change.NICKEL);
                appendValue++;
                changeMap.put(Change.NICKEL, appendValue);
                total = total.subtract(new BigDecimal("0.05").setScale(2, RoundingMode.HALF_UP));
            } else if (total.setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal("0.01").setScale(2, RoundingMode.HALF_UP)) >= 0) {
                int appendValue = changeMap.get(Change.PENNY);
                appendValue++;
                changeMap.put(Change.PENNY, appendValue);
                total = total.subtract(new BigDecimal("0.01").setScale(2, RoundingMode.HALF_UP));
            } else if (total.setScale(2, RoundingMode.HALF_UP).compareTo(zeroSum) < 0) {
                throw new VendingMachineDaoPersistenceException("ERROR: "
                        + "Change was miscalculated");
            }
        }
        moneyInserted = new BigDecimal("0.00");
        
        return changeMap;
    }

    @Override
    public Item selectItem(long id) {
        for(Item item : items) {
            if (item.getId() == id){
                currentItem = item;
            }
        }
        changeMap.clear();
        return currentItem;
    }
    
    @Override
    public Item getItem(){
        return currentItem;
    }

}
