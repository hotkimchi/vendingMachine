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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class StateAndTaxDao {
    
    private Map<String, BigDecimal> taxMap;
    public static final String DELIMITER = "::";
    public static final String TAX_FILE = "tax.txt";
    
    public Map<String, BigDecimal> readStateAndTaxes() throws DaoPersistenceException{
        Scanner myScanner = new Scanner(System.in);
        this.taxMap = new HashMap();
        
        try {
            myScanner = new Scanner(new BufferedReader(
                    new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new DaoPersistenceException("-_- Could not load taxes "
                    + "data into memory.", e);
        }
        
        String currentLine;
        String[] currentToken;
        while (myScanner.hasNextLine()) {
            currentLine = myScanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            if(!currentLine.startsWith("State")){
                String state = currentToken[0];
                String taxRate = currentToken[1];
                BigDecimal taxRateBD = new BigDecimal(taxRate);
                taxMap.put(state, taxRateBD);
            }
        }
        myScanner.close();
        return taxMap;
    }
    
}
