//241RDB316 Vladislav Ebert 7.gruppa
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        List<One> total = new ArrayList<>();
        Path filePath = Paths.get("src", "db.csv");

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                String[] parts = line.split(";");

                int id = Integer.parseInt(parts[0].trim());
                String city = parts[1].trim();
                String date = parts[2].trim();
                int days = Integer.parseInt(parts[3].trim());
                double price = Double.parseDouble(parts[4].trim());
                String vehicle = parts[5].trim();

                total.add(new One(id, city, date, days, price, vehicle));
            }

            System.out.println("We have: ");
            for (One one : total) {
                System.out.println(one);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String choiceStr;


        while (true) {

            System.out.println("Enter your choise: ");

            choiceStr = sc.nextLine().trim().toLowerCase();

            switch (choiceStr) {
                case "add":
                    Add(total);
                    break;
                case "del":
                    Delete(total);
                    break;
                case "edit":
                    Edit(total);
                    break;
                case "print":
                    Print(total);
                    break;
                case "sort":
                    Sort(total);
                    break;
                case "find":
                    Find(total);
                    break;
                case "avg":
                    Avg(total);
                    break;
                case "exit":
                    Exit(total, filePath);
                    return;
                default:
                    System.out.println("Incorrect Input!!!");
            }
        }
    }

    public static void Add(List<One> total) {



         String input=sc.nextLine().trim();
        String[] parts =input.split(";");
        if (parts.length !=6){
            System.out.println("Wrong field count");
            return;
        }

    try {
        int id = Integer.parseInt(parts[0].trim());
        if (String.valueOf(id).length() != 3){
            System.out.println("Wrong ID input!");
            return;
    }
        for (One one : total) {
            if(one.checkId() == id){
                System.out.println("Wrong Id input!");
                return;
            }
    }


        String city = parts[1].trim();
        String[] words = city.toLowerCase().split("\\s+");
        StringBuilder NewCity = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {

                NewCity.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        city = NewCity.toString().trim();


        String date = parts[2].trim();
        String[] dateSeparetly = date.split("/");
        if (dateSeparetly.length !=3) {
            System.out.println("Wrong date");
            return;
        }
        try{
            int day=Integer.parseInt(dateSeparetly[0]);
            int mounth=Integer.parseInt(dateSeparetly[1]);
            int year=Integer.parseInt(dateSeparetly[2]);

            if ( day < 1 || day > 31 || mounth < 1 || mounth > 12){
                System.out.println("Wrong date");
                return;
            }
        }catch (NumberFormatException e){
            System.out.println("Wrong date");
            return;
        }

        int days;
        try {
            days = Integer.parseInt(parts[3].trim());
            if (days < 0){
                System.out.println("wrong day");
                return;
            }
        }catch (NumberFormatException e){
            System.out.println("Wrong day output");
            return;
        }


        double price;
        try {
            price = Double.parseDouble(parts[4].trim());
        }catch (NumberFormatException e){
            System.out.println("Wrong price");
            return;
        }


        String vehicle = parts[5].trim().toUpperCase();
        if(!vehicle.equals("TRAIN") || !vehicle.equals("PLANE") || !vehicle.equals("BOAT") || vehicle.equals("BUS")){
            System.out.println("Wrong vehicle");
            return;
        }


        total.add(new One(id, city, date, days, price, vehicle));

        System.out.println("Added");

    }catch (NumberFormatException e){
        System.out.println("Wrong id Input!!");
    }
    }

    public static void Delete(List<One> total) {
        String in = sc.nextLine().trim();
        String[] parts = in.split("\\s+");

        if (parts.length != 2 || !parts[0].equalsIgnoreCase("del")) {
            System.out.println("Wrong command format");
            return;
        }
        try {
            int id = Integer.parseInt(parts[1]);
            if (String.valueOf(id).length() != 3 ) {
                System.out.println("Wrong ID :(");
                return;
            }
            boolean exist = false;
            Iterator<One> iterator = total.iterator();
            while (iterator.hasNext()) {
                One one = iterator.next();
                if (one.checkId() == id) {
                    iterator.remove();
                    exist = true;
                    break;
                }
            }

            if (exist) {
                System.out.println("Deleted");
            } else {
                System.out.println("wrong Id");
            }

        } catch (NumberFormatException e) {
            System.out.println("wrong id");

        }
    }

    public static void Edit(List<One> total) {

        String input=sc.nextLine().trim();
        String[] parts =input.split(";");

        if(parts.length !=2 || !parts[0].equalsIgnoreCase("edit")){
            System.out.println("wrong command");
            return;
        }

        if (parts.length != 6){
            System.out.println("Wrong field count");
            return;
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            if (String.valueOf(id).length() != 3 ) {
                System.out.println("Wrong ID input!");
                return;
            }

            One edittedTrip = null;
            for(One one: total){
                if (one.checkId() == id){
                    edittedTrip = one;
                    break;
                }
            }

            if (edittedTrip == null){
                System.out.println("wrong id");
                return;
            }

            if(!parts[1].trim().isBlank()){
                edittedTrip.setCity(parts[1].trim());
            }

            if(!parts[2].trim().isBlank()){
                edittedTrip.setDate(parts[2].trim());
            } else {
                System.out.println("wrong input");
                return;
            }

            if(!parts[3].trim().isBlank()){
                try {
                    int days = Integer.parseInt(parts[3].trim());
                    if (days <= 0){
                        System.out.println("wrong days format");
                        return;
                    }
                    edittedTrip.setDays(days);
                }catch (NumberFormatException e){
                    System.out.println("wrong days format");
                    return;
                }
            }


            if(!parts[4].trim().isBlank()){
                try{
                    float price = Float.parseFloat(parts[4].trim());
                if (price <= 0 ){
                    System.out.println("wromg price format");
                }
                }catch (NumberFormatException e){
                    System.out.println("wrong price format");
                    return;
                }
            }

            if(!parts[5].trim().isBlank()){
               String vehicle = parts[5].trim().toUpperCase();
               if (vehicle.equals("PLANE") || vehicle.equals("BUS") ||
                       vehicle.equals("BOAT") || vehicle.equals("TRAIN"));
               edittedTrip.setVehicle(vehicle);
            } else {
                System.out.println("wrong vehicle format");
                return;
            }

        System.out.println("changed");

        }catch (NumberFormatException e){
            System.out.println("Wrong id");
            return;
        }


    }

    public static void Print(List<One> total) {
        System.out.println("----------------------------------------------------------------------");
    String first_lane =
            String.format("%-4s %-27s %-11s %-6s %-10s %-8s",
                                    "Id", "City","Date", "Days", "Price", "Vehicle");

    System.out.println(first_lane);
        System.out.println("----------------------------------------------------------------------");

    for (One one: total){
        String elements =
                String.format("%-4s %-27s %-11s %-6s %-10s %-8s",
                        one.id, one.city, one.date, one.days, one.price, one.vehicle);
        System.out.println(elements);

    }
        System.out.println("----------------------------------------------------------------------");
    }

    public static void Sort(List<One> total) {

    for (int i =0; i< total.size() - 1; i++)
        for(int j = 0; j < total.size() - i - 1; j++){

            if(total.get(j).date.compareTo(total.get(j+1).date) > 0){

                One temp = total.get(j);
                total.set(j, total.get( j +1 ));
                total.set(j + 1,temp);


            }
        }
    }

    public static void Find(List<One> total){

        String input = sc.nextLine().trim();

        String[] parts = input.split("/");
        if(parts.length !=2 || !parts[0].equalsIgnoreCase("find")){
            System.out.println("wrong command");
            return;
        }
        double same;

        try{
            same = Double.parseDouble(parts [1]);
        } catch (NumberFormatException e){
            System.out.println("wrong price");
            return;
        }
            boolean found= false;

        for (One one: total){
            if (one.price == same){
               found =true;
                    System.out.println("----------------------------------------------------------------------");
                    System.out.printf("%-4s %-27s %-11s %-6s %-10s %-8s",
                                        "Id", "City","Date", "Days", "Price", "Vehicle");
                    System.out.println("----------------------------------------------------------------------");
                    System.out.printf("%-4s %-27s %-11s %-6s %-10s %-8s",
                            one.id, one.city, one.date, one.days, one.price, one.vehicle);

                }
        }
                if (!found){
                    System.out.println("data not found under this area");
                }
    }

    public static void Avg(List<One> total) {

        double totalPrice = 0;

        for (One one:total){
            totalPrice += one.price;

        }
        double avgPrice = totalPrice / total.size();
        System.out.println(String.format("average=%.2f", avgPrice));

    }

    public static void Exit(List<One> total, Path filePath) {
    SaveFile(total, filePath);
    }

    public static void SaveFile (List<One> total, Path filePath ) {
        try (BufferedWriter input = Files.newBufferedWriter(filePath)) {
            for (One one : total) {
                input.write(String.format("%d;%s;%s;%d;%.2f;%s%n",
                        one.id, one.city, one.days, one.price, one.vehicle));
            }
        } catch (IOException e){
            System.out.println("wrong command");
        }
    }
    static class One {
        private int id;
        private String city;
        private String date;
        private int days;
        private double price;
        private String vehicle;

        public One(int id, String city, String date, int days, double price, String vehicle) {
            this.id = id;
            this.city = city;
            this.date = date;
            this.days = days;
            this.price = price;
            this.vehicle = vehicle;
        }

        public int checkId(){
            return  id;
        }

        public  void setCity(String city){
            this.city = city;
        }

        public void setDate (String date){
            this.date = date;

        }

        public void setDays(int days){
            this.days = days;

        }

        public void setVehicle (String vehicle){
            this.vehicle = vehicle;
        }
    }


}
