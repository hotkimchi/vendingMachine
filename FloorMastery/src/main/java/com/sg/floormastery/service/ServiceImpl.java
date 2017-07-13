/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.service;

import com.sg.floormastery.dao.DaoPersistenceException;
import com.sg.floormastery.dao.FloorMasteryDao;
import com.sg.floormastery.dto.Order;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class ServiceImpl implements FloorMasteryService {

    private FloorMasteryDao dao;
    private boolean prodIsGo;

    public ServiceImpl(FloorMasteryDao dao, String production) {
        this.dao = dao;
        this.prodIsGo = false;
        if (production.equalsIgnoreCase("production")) {
            this.prodIsGo = true;
        }
    }

    @Override
    public Order addOrder(Order newOrder) throws ServiceValidateOrderException{
        validateOrder(newOrder);
        return dao.addOrder(newOrder);
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate displayDate) throws 
            ServiceLocalDateException, DaoPersistenceException {
        List<Order> dateOrders = new ArrayList();
        validateDate(displayDate);
        dateOrders = dao.displayOrders(displayDate);
        return dateOrders;
    }

    @Override
    public Order editAnOrder(LocalDate orderDate, Order order) throws
            ServiceValidateOrderException, ServiceLocalDateException {
        Order editOrder = new Order();
        validateDate(orderDate);
        validateOrder(order);
        editOrder = dao.editOrder(orderDate, order);
        return editOrder;
    }

    @Override
    public List<Order> removeAnOrder(LocalDate orderDate, Order order) throws
            ServiceLocalDateException, ServiceValidateOrderException {
        validateDate(orderDate);
        validateOrder(order);
        List<Order> orderList = dao.removeOrder(orderDate, order);
        return orderList;
    }

    @Override
    public void saveWork(int operation) throws ServiceProductionException,
            DaoPersistenceException{
        if (prodIsGo == true) {
            dao.saveAndWriteOrders(operation);
        } else {
            throw new ServiceProductionException("Production mode not enabled. "
                    + "Training is being run");
        }
    }
    
    @Override
    public List<String> getStates(){
        return dao.getStates();
    }
    
    @Override
    public List<String> getProducts(){
        return dao.getProductNames();
    }

    private void validateDate(LocalDate date) throws ServiceLocalDateException {
        LocalDate firstOrderDate = LocalDate.parse("2017-06-07");
        LocalDate todayDate = LocalDate.now();
        if (date == null){
            throw new ServiceLocalDateException(
                    "There was no date entered");
        }
        if (date.isAfter(todayDate)) {
            throw new ServiceLocalDateException(
                    "We do not create any future orders past today's date");
        }
        if (date.isBefore(firstOrderDate)) {
            throw new ServiceLocalDateException(
                    "There is no order that has happened before that date");
        }
    }

    private void validateOrder(Order order) throws ServiceValidateOrderException {

        List<String> stateList = dao.getStates();
        List<String> productList = dao.getProductNames();

        if (!stateList.contains(order.getState())) {
            throw new ServiceValidateOrderException("ERROR: state was not found");
        } else if (!productList.contains(order.getProductType())) {
            throw new ServiceValidateOrderException("ERROR: product was not found");
        }
    }

}
