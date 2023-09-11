package service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pojo.Basket;
import pojo.Customer;
import pojo.Invoice;
import pojo.Photo;
import repository.PhotoRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InvoiceService {
    //orders laten zien
    public static void ShowOrders() {
        File folder = new File("src/data/orders");
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            String fileName = file.getName().split("\\.")[0]; 
            System.out.println(fileName);
        }
    }

//    public static File getOrder(String orderName) {
//        File folder = new File("src/data/orders");
//        File[] listOfFiles = folder.listFiles();
//
//        assert listOfFiles != null;
//        for (File file : listOfFiles) {
//            String fileName = file.getName().split("\\.")[0];
//            if(orderName.equals(fileName)) {
//                return file;
//            }
//        }
//        return new File("");
//    }
    //order uit de json lezen


    public static Invoice readOrder(String filename, PhotoRepository photoRepository) throws ParseException{
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/data/orders/"+filename+".json"))
        {
            // //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            //create customer json object and convert it to a customer object
            JSONObject customerObject = (JSONObject) obj.get("customer");
            JSONObject itemsObject = (JSONObject) obj.get("items");
            Customer customer = new Customer("1", (String) customerObject.get("name"), (String) customerObject.get("email"), (String) customerObject.get("address"));

            Basket basket = new Basket("1", new ArrayList<>());
            for (int i = 1; i < itemsObject.size() + 1; i++) {
                JSONArray item = (JSONArray) itemsObject.get(Integer.toString(i));
                Photo photo = photoRepository.retrievePhotoByName(item.get(0).toString());
                photo.setAmount(Integer.parseInt(item.get(4).toString()));
                basket.setItems(photoRepository.retrievePhoto(photo.getId()));

            }
            String pickUpDate = (String) obj.get("pickupdate");

            return new Invoice("1", customer, basket, pickUpDate);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        }
}