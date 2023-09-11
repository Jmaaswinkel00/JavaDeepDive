package service;

import pojo.Photo;
import repository.PhotoRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemList {
    //items inladen in de photo class
    public static void loadItems(PhotoRepository photoRepository){
        ArrayList<Photo> items = new ArrayList<>();
        String fileName = "src/data/PhotoShop_PriceList.csv";
        File file = new File(fileName);
        Scanner inputStream;
        int x = 0;
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                photoRepository.createPhoto(new Photo(values[0], values[1], values[3], 0, new BigDecimal(values[2])));
                items.add(photoRepository.retrievePhoto(values[0]));
                x++;
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showItems(PhotoRepository photoRepository) {
        for (int i = 1; i < 13; i++) {
            Photo photo = photoRepository.retrievePhoto(Integer.toString(i));
            System.out.printf("ID: %-2s - naam: %-30s - prijs: %-10s - productie tijd: %-5s \n", photo.getId(), photo.getName(), photo.getPrice(), photo.getPrepareTime());
        }
    }

}
