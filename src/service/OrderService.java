package service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import pojo.*;
import repository.OrderRepository;
import repository.PhotoRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.File;

public class OrderService {
    static int totalHours;
    static OrderRepository orderRepository = new OrderRepository();
    //order aanmaken
    public static void createOrder(Customer customer, Basket basket, PhotoRepository photoRepository) throws ParseException, java.text.ParseException {
        orderRepository.createOrder(new Order("1", customer, basket));
        dataCheck(customer, photoRepository, orderRepository.retrieveOrder("1"), null);
        createOrderJson(orderRepository.retrieveOrder("1"), customer, photoRepository);
    }

    /*
    check of alle gegevens kloppen die ingevuld zijn
     */
    public static void dataCheck(Customer customer, PhotoRepository photoRepository, Order order, String pickupdate) {
        BigDecimal totalPrice = new BigDecimal(0);
        int totalHours = 0;
        System.out.println("Zijn deze gegevens correct:");
        System.out.printf(
                        """
                        %-25s %-25s
                        Naam: %-15s Naam: %-15s    
                        Email: %-15s
                        Adres: %-15s
                        ""","Klantgegevens", "Geholpen door", customer.getName(), "Jordy Maaswinkel", customer.getEmail(), customer.getAddress());
        if (order == null) {
            order = orderRepository.retrieveOrder("1");
        }

        ArrayList<Photo> array = order.getBasket().getItems();
        System.out.printf("%-30s | %-10s | %-14s | %-6s \n", "Naam", "Prijs p/s", "Hoeveelheid", "Prijs totaal");
        for (Photo photo : array) {
            System.out.printf("%-30s | %-10s | %-14s | %-6s\n", photo.getName(), photo.getPrice(), photo.getAmount(), photo.getTotalPrice());
            totalPrice = totalPrice.add(photo.getTotalPrice());
            totalHours += photo.getTotalPrepareTime();
        }
        System.out.println("Je totale bedrag komt uit op: " + totalPrice);
        if (pickupdate != null) {
            System.out.println("De totale productie tijd is " + totalHours + " uur");
            System.out.println("Je kan de bestelling ophalen vanaf " + pickupdate);
        }
        validationData(0, customer, order, photoRepository);
    }

    /*
    check of er al orders bestaan, als dat het geval is tel hoeveel orders en doe + 1 om het nieuwe order nummer te vinden
    als dat niet het geval is zet het order nummer op 1
    */
    public static String getOrderNumber() {
        File folder = new File("src/data/orders");
        File[] listOfFiles = folder.listFiles();

        return (listOfFiles != null) ? String.format(String.valueOf(listOfFiles.length + 1)) : "1";
    }

    /*
    validatie of alle gegevens juist zijn
    wrongchecks betekenis:
    0 = voer de gehele functie uit
    1 = geen ja of nee ingevuld
    2 = geen klant of order gekozen
     */
    public static void validationData(int wrongChecks, Customer customer, Order order, PhotoRepository photoRepository){
        Scanner scanner = new Scanner(System.in);
        if (wrongChecks == 0) {
            System.out.println("Zijn deze gegevens juist? (ja/nee)");
        }
        if (wrongChecks == 1) {
            System.out.println("je hebt een fout gemaakt in ja of nee, graag ja of nee ingeven");
        }
        String yesOrNo = scanner.nextLine();
        if (yesOrNo.equals("nee") || wrongChecks == 2) {
            if (wrongChecks == 2) {
                System.out.println("Je hebt een fout gemaakt, graag aangeven welk deel je wilt aanpassen");
            }
            System.out.println("Welke gegevens kloppen niet? (klant/order)");
            String change = scanner.nextLine();
            if (change.equals("klant")) {
                service.CustomerService.changeCustomerData(customer);
                dataCheck(customer, photoRepository, order, null);
            } else if (change.equals("order")) {
                BasketService basketService = new BasketService(null);
                basketService.changeOrderData(order.getBasket(), photoRepository);
                dataCheck(customer, photoRepository, order, null);
            } else {
                validationData(2, customer, order, photoRepository);
            }
        } else if (yesOrNo.equals("ja")) {
            return; //niks aan passen dus ga uit de functie
        } else {
            validationData(1, customer, order, photoRepository);
        }
    }
    //order json aanmaken
    private static void createOrderJson(Order order, Customer customer, PhotoRepository photoRepository) throws ParseException, java.text.ParseException {
        JSONObject mainObject = new JSONObject();

        JSONObject orderData = new JSONObject();
        mainObject.put("orderNumber", getOrderNumber());

        mainObject.put("customer", customerJsonData(customer));
        ArrayList<Photo> array = order.getBasket().getItems();
        int i = 1;
        for (Photo photo : array) {
            orderData.put(i, orderJsonData(photo));
            i++;
        }
        mainObject.put("items", orderData);
        String pickupdate;
        if(getOrderNumber().equals("1")) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            pickupdate = format1.format(calendar.getTime());
        } else {
            Invoice invoice = service.InvoiceService.readOrder(String.valueOf(Integer.parseInt(getOrderNumber()) - 1) + "_order", photoRepository);
            pickupdate = invoice.getPickUpDate();
        }

        //productie tijd berekenen
        mainObject.put("pickupdate", PickupTime.getPickupTime(totalHours, pickupdate));

        try (FileWriter file = new FileWriter("src/data/orders/" + getOrderNumber() +"_order.json"))
        {
            file.write(mainObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //customer data klaar zetten om in de json te zetten
    private static JSONObject customerJsonData(Customer customer) {
        JSONObject customerData = new JSONObject();
        customerData.put("name", customer.getName());
        customerData.put("email", customer.getEmail());
        customerData.put("address", customer.getAddress());
        return customerData;
    }
    //order data klaar zetten om in de json te zetten en de totalhours variable vullen voor later
    private static JSONArray orderJsonData(Photo photo) {
        JSONArray array = new JSONArray();
        array.add(photo.getId());
        array.add(photo.getName());
        array.add(photo.getPrepareTime());
        array.add(photo.getPrice());
        array.add(photo.getAmount());
        array.add(photo.getTotalPrice());
        totalHours += photo.getTotalPrepareTime();
        return array;
    }


}