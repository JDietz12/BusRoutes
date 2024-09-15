/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. _Jackson Dietz_
*/

import java.io.*;
import java.util.*;

// program to plan a user route using Emory shuttle system
public class TripPlanner {
    private final ArrayList<BusRoute> routes; // list of Emory shuttle routes
    private final String filePath; // directory where to find txt files of routes
    private String start; // user-defined starting point
    private String end; // user-defined ending point
    private ArrayList<BusRoute> routesStoppingAtStart; // list of routes that stop at start
    private ArrayList<BusRoute> routesStoppingAtEnd; // list of routes that stop at end
    private Trip trip; // user's suggested trip

    // important! text files Loop.txt, C.txt, A.txt, M.txt, E.txt, D.txt must be in the folder for given filePath!
    // edit the call to this constructor below in main to indicate where your files are (or leave as "" if files are in current working directory)
    public TripPlanner(String filePath){
        this.filePath = filePath;
        routes = new ArrayList<BusRoute>();
        routes.add(buildRoute("Loop"));
        routes.add(buildRoute("C"));
        routes.add(buildRoute("A"));
        routes.add(buildRoute("M"));
        routes.add(buildRoute("E"));
        routes.add(buildRoute("D"));
    }

    // loads the stops for each Emory Shuttle Route and builds BusRoute objects for each
    public BusRoute buildRoute(String name){
        BusRoute r = new BusRoute(name);
        Scanner scanner;
        try{
            scanner = new Scanner(new FileInputStream(filePath+name + ".txt"));
        }
        catch(IOException ex){
            System.err.println("*** Invalid filename: " + filePath + name + ".txt");
            return null;
        }
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            r.addStop(line);
        }
        return r;
    }

    // sets start
    public void setStart(String start){
        this.start = start;
    }

    // sets end
    public void setEnd(String end){
        this.end = end;
    }

    // returns list of routes stopping at start
    public ArrayList<BusRoute> getRoutesStoppingAtStart(){
        return routesStoppingAtStart;
    }

    // returns list of routes stopping at end
    public ArrayList<BusRoute> getRoutesStoppingAtEnd(){
        return routesStoppingAtEnd;
    }

    // returns true if start and stop are valid stops; false otherwise
    public boolean verifyInput(){
        routesStoppingAtStart = getRoutesStoppingAt(start);
        routesStoppingAtEnd = getRoutesStoppingAt(end);
        if(routesStoppingAtStart.isEmpty()){
            System.out.println("No routes found stopping at start location!");
            return false;
        }
        if(routesStoppingAtEnd.isEmpty()){
            System.out.println("No routes found stopping at end location!");
            return false;
        }
        return true;
    }

    // prints trip from start to end
    public void displayTrip(){
        trip.displayTrip();
    }

    // returns trip
    public Trip getTrip(){
        return trip;
    }

    public ArrayList<BusRoute> getRoutesStoppingAt(String stopName){

        ArrayList<BusRoute> RouteList = new  ArrayList<>();

        for (BusRoute route : routes) {
            if (route.stopsAt(stopName)) {
                RouteList.add(route);
            }
        }

        return null;

    }





    public void buildTrip(){


        routesStoppingAtStart = getRoutesStoppingAt(start); // stores the route at start point
        routesStoppingAtEnd = getRoutesStoppingAt(end);  // stores the route at end point


        BusRoute routeForTrip = null; //Initialize bus route
        BusRoute route1 = null; //Initialize bus route
        BusRoute route2 = null; //Initialize bus route
        ArrayList<String> transferPoints = new ArrayList<>(); //Holds transfer points
        BusStop startingStop = null; //Holds starting stop... to be mutilated
        BusStop endingStop = null; //Holds end stop... to be mutilated
        boolean canTakeOneRoute = false; // boolean tracker
        Trip builtTrip = new Trip();//stores the trip


        for(int i=0; i<routesStoppingAtEnd.size(); i++){  // Iterates through to see if the given user can take just 1 route
            if(routesStoppingAtStart.contains(routesStoppingAtEnd.get(i))){
                canTakeOneRoute = true;
                routeForTrip= routesStoppingAtEnd.get(i);
                break;
            }
        }

        if(canTakeOneRoute){  // If the loop above is successful then we build a route for the user using 1 route
            BusStop current = routeForTrip.getStart().getNext();
            while(!current.getName().equals(start)){
                if(current.getNext().getName().equals(start)){
                    startingStop = current.getNext();
                }
                current = current.getNext();
            }

            while(!current.getName().equals(end)){  //iterates through the list
                builtTrip.addStop(current.getName(), current.getRoute());
                if(current.getNext().getName().equals(end)){
                    builtTrip.addStop(current.getNext().getName(),current.getRoute()); //adds stops to the trip object
                    this.trip = builtTrip;
                    return;
                }
                current = current.getNext();
            }
        } else{

            //in the case that their a multiple routes

            //traverse the routes and store transfer points
            for(int i = 0; i < routesStoppingAtStart.size();i++){
                for(int j = 0; j< routesStoppingAtEnd.size(); j++){
                    if(!routesStoppingAtStart.get(i).getTransferPoints(routesStoppingAtEnd.get(j)).isEmpty()){
                        route1 = routesStoppingAtStart.get(i);
                        route2 = routesStoppingAtEnd.get(j);
                        break;
                    }
                }
            }


            transferPoints = route1.getTransferPoints((route2));
            endingStop = route2.getNextStop(end).getPrevious(); //holds the ending stop of transfer
            startingStop = route1.getNextStop(start).getPrevious(); //holds start of transfer


           //This sequence of nested loops builds a trip using different routes (transfers are implemented)
            if(transferPoints.isEmpty()){
                String transferPoint = transferPoints.get(0);
                BusStop current = startingStop.getPrevious();
                while(!current.getName().equals(transferPoint)){
                    builtTrip.addStop(current.getNext().getName(), current.getRoute());
                    if(current.getNext().getName().equals(transferPoint)){
                        BusStop current2 = route2.getNextStop(transferPoint).getPrevious();
                        builtTrip.addStop(current2.getName(), current2.getRoute());
                        while(!current2.getName().equals(endingStop.getName())){
                            builtTrip.addStop(current2.getNext().getName(),current.getRoute());
                            if(current2.getNext().getName().equals(endingStop.getName())){
                                this.trip = builtTrip;
                                return;
                            }
                            current2 = current2.getNext();
                        }
                    }
                }


            }

        }


    }


    public static void main(String[] args) {
        // 1. Build Emory Bus Routes
        TripPlanner tp = new TripPlanner("");

        // 2. Take in start and end points from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Emory Trip Planner!");
        // Read user input for starting and ending location names
        System.out.print("Enter your starting location: ");
        tp.setStart(scanner.nextLine());
        System.out.print("Enter your ending location: ");
        tp.setEnd(scanner.nextLine());

        // 3. Print bus routes stopping at start and end locations
        if (!tp.verifyInput()) {
            return;
        }
        System.out.println("Routes stopping at start location: " + tp.getRoutesStoppingAtStart());
        System.out.println("Routes stopping at end location: " + tp.getRoutesStoppingAtEnd());

        // 3. Build linked list for trip
        tp.buildTrip();

        // 4. print step by step directions for user
        System.out.println("Your Trip:");
        tp.displayTrip();
    }
}
