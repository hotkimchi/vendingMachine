/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class ProductInfoDao {
    
    private Map<String, ArrayList<BigDecimal>> productMap;
    public static final String DELIMITER = "::";
    public static final String PRODUCT_FILE = "product.txt";
    
    public Map<String, ArrayList<BigDecimal>> readProductInfo() throws DaoPersistenceException{
        Scanner myScanner = new Scanner(System.in);
        this.productMap = new HashMap();
        
        try {
            myScanner = new Scanner(new BufferedReader(
                    new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new DaoPersistenceException("-_- Could not load products "
                    + "data into memory.", e);
        }
        
        String currentLine;
        String[] currentToken;
        
        while (myScanner.hasNextLine()) {
            ArrayList<BigDecimal> costList = new ArrayList();
            currentLine = myScanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            if(!currentLine.startsWith("Product")){
                String productName = currentToken[0];
                String sqrCost = currentToken[1];
                String laborCost = currentToken[2];
                BigDecimal sqrCostBD = new BigDecimal(sqrCost);
                BigDecimal laborCostBD = new BigDecimal(laborCost);
                costList.add(sqrCostBD);
                costList.add(laborCostBD);
                productMap.put(productName, costList);
            }
        }
        myScanner.close();
        return productMap;
    }
    
}
