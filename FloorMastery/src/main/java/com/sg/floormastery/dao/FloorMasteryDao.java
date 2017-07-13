/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.dao;

import com.sg.floormastery.dto.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface FloorMasteryDao {
    
    List<String> getProductNames();
    void saveAndWriteOrders(int operation) throws DaoPersistenceException;
    List<String> getStates();
    List<Order> displayOrders(LocalDate orderDate) throws DaoPersistenceException;
    Order addOrder(Order order);
    Order editOrder(LocalDate orderDate, Order order) ;
    List<Order> removeOrder(LocalDate orderDate, Order order);
}
