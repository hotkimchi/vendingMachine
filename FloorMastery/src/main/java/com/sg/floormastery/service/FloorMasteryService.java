/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.service;

import com.sg.floormastery.dao.DaoPersistenceException;
import com.sg.floormastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface FloorMasteryService {
    
    Order addOrder(Order newOrder) throws ServiceValidateOrderException;
    List<Order> getAllOrdersByDate(LocalDate displayDate) throws 
            ServiceLocalDateException, DaoPersistenceException;
    Order editAnOrder (LocalDate orderDate, Order order) throws
            ServiceValidateOrderException, ServiceLocalDateException;
    List<Order> removeAnOrder(LocalDate orderDate, Order order) throws
            ServiceLocalDateException, ServiceValidateOrderException;
    void saveWork(int operation) throws ServiceProductionException,
            DaoPersistenceException;
    List<String> getStates();
    List<String> getProducts();
}
