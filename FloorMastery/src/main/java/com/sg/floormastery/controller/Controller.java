/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.controller;

import com.sg.floormastery.dao.DaoPersistenceException;
import com.sg.floormastery.dto.Order;
import com.sg.floormastery.service.FloorMasteryService;
import com.sg.floormastery.service.ServiceLocalDateException;
import com.sg.floormastery.service.ServiceProductionException;
import com.sg.floormastery.service.ServiceValidateOrderException;
import com.sg.floormastery.ui.View;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class Controller {

    private View view;
    private FloorMasteryService service;

    public Controller(View view, FloorMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void execute() {

        view.displayWelcome();
        boolean returnToMenu = true;

        while (returnToMenu) {

            view.displayMainMenu();
            int choice = view.getMenuChoice(1, 6);

            switch (choice) {
                case 1:
                    displayOrder();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    saveWork();
                    break;
                case 6:
                    view.displaySaveChoice();
                    int saveChoice = view.getMenuChoice(1, 2);
                    if (saveChoice == 1) {
                        saveWork();
                        returnToMenu = false;
                        break;
                    } else {
                        returnToMenu = false;
                        break;
                    }
            }
        }
        view.displayGoodBye();
    }

    private void displayOrder() {
        view.displayDisplayMenu();
        int choice = view.getMenuChoice(1, 2);
        if (choice == 1) {
            LocalDate displayDate = view.askForDate();
            try {
                List<Order> ordersByDate = service.getAllOrdersByDate(displayDate);
                view.displayOrdersByDate(ordersByDate, displayDate);
            } catch (DaoPersistenceException | 
                    ServiceLocalDateException e) {
                view.displayError(e.getMessage());
            }
        }

    }

    private void addOrder() {
        view.displayAddOrderMenu();
        int choice = view.getMenuChoice(1, 2);
        if (choice == 1) {
            Order newOrder = new Order();
            try {
                List<String> stateList = service.getStates();
                List<String> productList = service.getProducts();
                newOrder = view.getOrderInfo(stateList, productList);
                newOrder = service.addOrder(newOrder);
                view.displayNewOrUpdatedOrder(newOrder);
                view.displaySaveChoice();
                int saveChoice = view.getMenuChoice(1, 2);
                if(saveChoice == 1){
                    service.saveWork(1);
                } else {
                    service.saveWork(3);
                }
            } catch (ServiceValidateOrderException |
                    ServiceProductionException |
                    DaoPersistenceException e) {
                view.displayError(e.getMessage());
            }

        }
    }

    private void editOrder() {
        view.displayEditMenu();
        int choice = view.getMenuChoice(1, 2);
        if(choice == 1){
            LocalDate findDate = view.askForDate();
            try {
                List<Order> ordersByDate = service.getAllOrdersByDate(findDate);
                view.displayOrdersByDate(ordersByDate, findDate);
                int editChoice = view.getMenuChoice(1, ordersByDate.size());
                List<String> stateList = service.getStates();
                List<String> productList = service.getProducts();
                Order editOrder = view.editTheOrder(ordersByDate, editChoice,
                                                    stateList, productList);
                editOrder = service.editAnOrder(findDate, editOrder);
                view.displayNewOrUpdatedOrder(editOrder);
            } catch (ServiceValidateOrderException |
                    ServiceLocalDateException |
                    DaoPersistenceException e) {
                view.displayError(e.getMessage());
            }
            
        }
    }

    private void removeOrder() {
        view.displayRemoveMenu();
        int choice = view.getMenuChoice(1, 2);
        if (choice == 1){
            LocalDate findDate = view.askForDate();
            try{
                List<Order> ordersByDate = service.getAllOrdersByDate(findDate);
                view.displayOrdersByDate(ordersByDate, findDate);
                int removeChoice = view.getMenuChoice(1, ordersByDate.size());
                boolean okToRemove = view.removeTheOrder(ordersByDate, removeChoice);
                if (okToRemove == true){
                    Order removeOrder = ordersByDate.get(removeChoice - 1);
                    service.removeAnOrder(findDate, removeOrder);
                    view.displaySuccessfulRemoval(removeOrder);
                }
            } catch (ServiceLocalDateException | 
                    ServiceValidateOrderException |
                    DaoPersistenceException e) {
                view.displayError(e.getMessage());
            }
        }
    }

    private void saveWork() {
        try {
            service.saveWork(2);
        } catch (DaoPersistenceException |
                ServiceProductionException e) {
            view.displayError(e.getMessage());
        }
    }

}
