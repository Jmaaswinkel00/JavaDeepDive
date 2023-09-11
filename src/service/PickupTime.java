package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class PickupTime {
    static int x = 0;
    static int hoursLeftToAdd = 0;
    //bereken de pickup datum/tijd
    public static String getPickupTime(int totalHours, String pickUpDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date newdate = format1.parse(pickUpDate);
        Calendar dateNow = Calendar.getInstance();

        Date date;
        int day;
        //check de nieuwste datum (of vandaag of die van een vorige order)
        if ( newdate.after(dateNow.getTime())) {
            date = newdate;
            day = date.getDay() + 1;
        } else {
            date = dateNow.getTime();
            day = calendar.get(Calendar.DAY_OF_WEEK);
        }
        if (dayOfWeek(day).equals("404")) {
            System.out.println("Er is iets vreselijk fout gegaan!");
            return null;
        }
        calculatePickupTime(totalHours, day, date.getHours() + 1);

        date.setDate(date.getDate());
        if ( x > 0) {
            date = setTime(date, 9);
        }

        date = addDays(date, x);
        date = addHours(date, hoursLeftToAdd);

        return format1.format(date.getTime());
    }
    //haal het record uit het CSV bestand met de juiste openingstijden
    private static String dayOfWeek(int currentDayOfWeek) {
        String fileName = "src/data/PhotoShop_OpeningHours.csv";
        File file = new File(fileName);
        Scanner inputStream;
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String dateTime = inputStream.nextLine();
                int dayOfWeek =  Integer.parseInt(dateTime.split(";")[0]);
                if(dayOfWeek == currentDayOfWeek) {
                    return dateTime;
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "404";
    }
    //berekening voor de pickup tijd, dit is een recursieve functie die door blijft gaan tot hij geen uren meer over heeft
    private static void calculatePickupTime(int totalHours, int dayOfWeek, int currentHour) {
        String[] weekData = dayOfWeek(dayOfWeek).split(";");
        int openingHour = Integer.parseInt(weekData[2].split(":")[0]);

        int closingHour = Integer.parseInt(weekData[3].split(":")[0]);
        int hoursLeftToday = closingHour - (Math.max(openingHour, currentHour));
        int futureDay = (Integer.parseInt(weekData[0]) + 1 != 8) ? dayOfWeek + 1 : 1;

        int hoursLeftIntotal = (totalHours - hoursLeftToday < 0) ? totalHours : totalHours - hoursLeftToday;
        if(hoursLeftIntotal <= hoursLeftToday) {
            hoursLeftToAdd = hoursLeftIntotal;
        } else {
            x++;
            calculatePickupTime(hoursLeftIntotal,futureDay, 0);
        }
    }

    //dagen toevoegen aan de datum
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
    //uren toevoegen aan de datum
    public static Date addHours(Date date, int hours)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours); //minus number would decrement the days
        return cal.getTime();
    }
    public static Date setTime(Date date, int hour)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour); //minus number would decrement the days
        return cal.getTime();
    }
}
