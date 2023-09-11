package service;

import org.json.simple.parser.ParseException;
import pojo.Customer;
import pojo.Invoice;
import repository.PhotoRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class MenuChoice {
    static Customer currentCustomer;
    static String menuSelection;
    static String selectionList;
    static PhotoRepository photoRepository = new PhotoRepository();

    //keuze menu genereren, van daaruit de rest van de applicatie uitvoeren
    public static void MenuChoice() throws ParseException, java.text.ParseException {
        //de scanner en items 1x inladen, de rest blijven herhalen
        Scanner scan = new Scanner(System.in);
        service.ItemList.loadItems(photoRepository);
        while(true) {
            System.out.println("Welkom bij de fotoshop!");
            System.out.println("Wat zou je willen doen?");
            System.out.println("1 - Order aanmaken");
            System.out.println("2 - Order inzien");
            System.out.println("3 - Applicatie stoppen");
            BasketService basket = new BasketService(null);
            menuSelection = scan.nextLine();
            switch (menuSelection) {
                case "1" -> {
                    //make an order
                    service.ItemList.showItems(photoRepository);
                    System.out.println("Proper syntax to order an item is:");
                    System.out.println("ID:hoeveelheid,ID:hoeveelheid... ");
                    System.out.println("\nSelecteer welke producten je wilt bestellen: ");
                    selectionList = scan.next();
                    basket.selectItems(selectionList, photoRepository);
                    if(!basket.retrieveBasket("1").getItems().isEmpty()) {
                        currentCustomer = service.CustomerService.customerDetails();
                        scan.nextLine();
                        service.OrderService.createOrder(currentCustomer, basket.retrieveBasket("1"), photoRepository);
                    }
                }
                case "2" -> {
                    //review an order (list of all orders and select the ID)
                    service.InvoiceService.ShowOrders();
                    System.out.println("Selecteer welke order je wilt inzien");
                    String orderName = scan.nextLine();
                    Invoice invoice = service.InvoiceService.readOrder(orderName, photoRepository);
                    assert invoice != null;
                    service.OrderService.dataCheck(invoice.getCustomer(), photoRepository, Invoice.getOrder(invoice.getCustomer(), invoice.getBasket()), invoice.getPickUpDate());
                }
                //applicatie stoppen
                case "3" -> System.exit(0);

                //je hebt een fout nummer ingevoerd
                default -> System.out.println("Kies alsjeblieft 1 van de opties die gegeven zijn");
            }
        }
    }
}
