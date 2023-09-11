package service;

import pojo.Basket;
import pojo.Photo;
import repository.BasketRepository;
import repository.PhotoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BasketService {
    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    public void createBasket(Basket basket) {
        this.basketRepository.createBasket(basket);
    }

    public Basket retrieveBasket(String id) {
        return this.basketRepository.retrieveBasket(id);
    }

    public void updateBasket(Basket basket) {
        this.basketRepository.updateBasket(basket);
    }

    public void deleteBasket(String id) {
        basketRepository.deleteBasket(id);
    }
    //item lijst invullen
    public void selectItems(String selectionList, PhotoRepository photoRepository) {
        this.basketRepository = new BasketRepository();
        this.createBasket(new Basket("1", new ArrayList<>()));

        Basket basket = basketRepository.retrieveBasket("1");
        String[] orderItems = selectionList.split(",");
        Arrays.stream(orderItems).forEach(item -> {
            String id = item.split(":")[0];
            String amount = item.split(":")[1];
            if(id.length() < 3) {
                if(photoRepository.retrievePhoto(id) == null) {
                    System.out.println("Dit product bestaat niet: " + id);
                    return;
                }
                photoRepository.retrievePhoto(id).setAmount(Integer.parseInt(amount));
                basket.setItems(photoRepository.retrievePhoto(id));
            } else {
                photoRepository.retrievePhotoByName(id).setAmount(Integer.parseInt(amount));
                basket.setItems(photoRepository.retrievePhotoByName(id));
            }
        });
    }

    public void changeOrderData(Basket basket, PhotoRepository photoRepository){
        System.out.println("Wil je iets toevoegen (of aanpassen) of verwijderen?");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if(choice.equals("toevoegen")){
            System.out.println("Geef het id en hoeveelheid op (ID:Hoeveelheid)");
            String photo = scanner.nextLine();
            String id = photo.split(":")[0];
            int amount = Integer.parseInt(photo.split(":")[1]);
            Photo item = photoRepository.retrievePhoto(id);
            if(basket.getItems().contains(item) && item.getAmount() == amount){
                System.out.println("Dit product zit al in jou basket met dezelfde hoeveelheid");
            } else {
                basket.deleteItem(item);
                item.setAmount(amount);
                basket.setItems(item);
            }
        }else if (choice.equals("verwijderen")) {
            System.out.println("Geef het ID op van welk product je wilt verwijderen");
            String photo = scanner.nextLine();
            Photo item = photoRepository.retrievePhoto(photo);
            basket.deleteItem(item);
        }
    }
}
