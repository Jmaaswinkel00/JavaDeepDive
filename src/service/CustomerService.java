package service;

import pojo.Customer;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerService {

    /*
    Klant gegevens ingeven en valideren
     */
    public static Customer customerDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Klant gegevens");
        System.out.print("Naam: ");
        String name = scanner.nextLine();
        while (name.isEmpty()){
            System.out.println("Je hebt geen naam ingevuld!");
            System.out.print("\nNaam: ");
            name = scanner.nextLine();
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();

        //regex om te checken of het een echt emailadres is
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        while (!pattern.matcher(email).find()){
            System.out.println("Het emailadres die je hebt ingevoerd is geen echt emailadres, probeer opnieuw!");
            System.out.print("Email: ");
            email = scanner.nextLine();
        }
        System.out.print("Address: ");

        String address = scanner.nextLine();
        while (address.isEmpty()){
            System.out.println("Je hebt geen adres ingevuld!");
            System.out.print("\nAddress: ");
            address = scanner.nextLine();
        }


        return new Customer("1", name, email, address);
    }
    //klant gegevens aanpassen
    public static void changeCustomerData(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wat wil je graag aanpassen?");
        String choice = scanner.nextLine();
        switch (choice) {
            case "naam" -> {
                System.out.print("Nieuwe naam: ");
                String name = scanner.nextLine();
                while (name.isEmpty()){
                    System.out.println("Je hebt geen naam ingevuld!");
                    System.out.print("\nNaam: ");
                    name = scanner.nextLine();
                }
                customer.setName(name);
            }
            case "email" -> {
                System.out.print("Nieuw email: ");
                String email = scanner.nextLine();
                //regex om te checken of het een echt emailadres is
                Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

                while (!pattern.matcher(email).find()){
                    System.out.println("Het emailadres die je hebt ingevoerd is geen echt emailadres, probeer opnieuw!");
                    System.out.print("Email: ");
                    email = scanner.nextLine();
                }
                customer.setEmail(email);
            }
            case "adres" -> {
                System.out.print("Nieuw adres: ");

                String address = scanner.nextLine();
                while (address.isEmpty()){
                    System.out.println("Je hebt geen adres ingevuld!");
                    System.out.print("\nAddress: ");
                    address = scanner.nextLine();

                }
                customer.setAddress(address);
            }
            default -> System.out.println("Je hebt een fout gemaakt, probeer opnieuw");
        }
    }
}
