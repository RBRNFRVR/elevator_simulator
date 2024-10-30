import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    static Scanner scnr = new Scanner(System.in);
    static int currentFloor = 1;
    static final int highestFloor = 15;
    static final int lowestFloor = 1;
    static ArrayList<Passenger> passengersList = new ArrayList<>();
    static ArrayList<Passenger> toRemove = new ArrayList<>();

    static class Passenger {
        int passengerId;
        int floor;

        public Passenger(int passengerId, int floor) {
            this.passengerId = passengerId;
            this.floor = floor;
        }
        @Override
        public String toString() {
            return "Passenger: " + passengerId + " for floor: " + floor;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Elevator Simulator...");
        System.out.println("How many passengers will be entering?(Maximum number is 5) ");
        int passengers = scnr.nextInt();

        passengerLimit(passengers);
        handlePassengers(passengers);
        processPassengers();
        handleRedo();
    }

    public static void passengerLimit(int passengers) {
        if(passengers > 5) {
            System.out.println("Too many passengers exceeding weight limit please exit, Elevator system closed");
            System.exit(0);
        }
        else if(passengers < 1) {
            System.out.println("No Passengers Entered!, Elevator sits idle or returns to floor 1");
            System.exit(0);
        }
    }

    public static void processPassengers() {
        if(!passengersList.isEmpty()) {
            int lastVisitedFloor = 0;
            for (Passenger passenger : passengersList) {
                if(passenger.floor != lastVisitedFloor) {
                    floorMovement(passenger.floor);
                    lastVisitedFloor = passenger.floor;
                }
            }
            passengersList.removeAll(toRemove);
            toRemove.clear();
        }
    }

    public static void handlePassengers(int passengers) {
        int chosenFloor;
        for(int i = 1; i <= passengers; i++) {
            do{
                System.out.println("Wish floor does passenger " + i + " wish to visit? " + "(Currently on " + currentFloor + " highest floor available: " + highestFloor + ")");
                chosenFloor = scnr.nextInt();

                if(chosenFloor == currentFloor) {
                    System.out.println("We are already on that floor");
                } else if (chosenFloor< lowestFloor || chosenFloor > highestFloor) {
                    System.out.println("Invalid floor selected, please choose a floor between " + lowestFloor + " and " + highestFloor);
                }
            }while(chosenFloor == currentFloor || chosenFloor < lowestFloor || chosenFloor > highestFloor);
            passengersList.add(new Passenger(i, chosenFloor));
        }
        Collections.sort(passengersList, (p1, p2) -> Integer.compare(p1.floor, p2.floor));
        System.out.println("Floors chosen: " + passengersList);

    }

    public static void floorMovement(int targetFloor){
        System.out.println("...Closing Doors...");
        while(targetFloor != currentFloor){

             currentFloor += (targetFloor > currentFloor) ? 1 : -1;

             if(currentFloor != targetFloor){
                 System.out.println("Passing Floor: " + currentFloor);
             }
             try{
                 Thread.sleep(2000);
             }catch(Exception e){
                 System.out.println("Interrupted because>>" + e);
             }
        }

        System.out.println("Arrived on floor " + currentFloor);
        System.out.println("...Doors Opening...");
        handleExit();
    }

    public static void handleExit(){
        for(Passenger passenger : passengersList) {
            if(passenger.floor == currentFloor) {
                System.out.println(passenger.toString() + " is leaving");
                toRemove.add(passenger);
            }
        }

    }

    public static void handleRedo(){
        System.out.println("Do you wish to ride again? Y or N");
        String redoResponse = scnr.next();

        if(redoResponse.equalsIgnoreCase("Y")){
            System.out.println("How many passengers this time?");
            int newPassengers = scnr.nextInt();
            passengerLimit(newPassengers);
            handlePassengers(newPassengers);
            processPassengers();
            handleRedo();
        }
        else if(redoResponse.equalsIgnoreCase("N")){
            System.out.println("Elevator returning to floor 1, Thanks for riding with us!");
        }
        else{
            System.out.println("Incorrect Input Elevator returning to floor 1");
        }
    }

}





