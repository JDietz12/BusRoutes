/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. _Jackson Dietz_
*/

import java.util.ArrayList;

// a class for a circular, doubly-linked list representing a bus route
public class BusRoute {
    private final String name; // name of the route
    private BusStop start; // starting bus stop (head of the linked list)

    public BusRoute(String name){
        this.name = name;
        start = null;
    }

    // return route name
    public String getName(){
        return name;
    }

    // return starting bus stop
    public BusStop getStart(){
        return start;
    }

    public void addStop(String name){
    //creates new BusStop node for the given name
    BusStop stop = new BusStop(name, this.name,null,null);
    if (start == null) {

    start = stop; // sets the start of the list to stop
        start.setNext(start); // to crete circular list setNext to start
        start.setPrevious(start); // to crete circular list setPrevious to start

    }   // If the route is empty, do not add the new stop

    else{
    BusStop lastStop = start.getPrevious(); //stores the stop before the start in lastStop
    lastStop.setNext(stop); // makes the previously last stop the second
    // to last stop with the new stop object being the last stop
    stop.setPrevious(lastStop); // interchanges the last statement but the
    // other way around (since this data structure is doubly linked)
    stop.setNext(start); //sets stops pointer to the start
    start.setPrevious(stop); // interchanges the last statement but the
    // other way around (since this data structure is doubly linked)
    }
    }



    public void addStop(String name, String prevStop){
    if (start == null) {
    return;}   // If the route is empty, do not add the new stop

    //creates new BusStop node for the given name
    BusStop stop = new BusStop(name, this.name, null, null);
    //holds the current stop for the list traversal
    BusStop currentStop = start; //store the currentStop for traversal

    while (!currentStop.getName().equals(prevStop)){ //traversal
        currentStop = currentStop.getNext(); //increment

        if (currentStop.equals(start)) { //At the end of the loop if we don't find prevStop
            return; //return nothing/break

        }
    }
    //insert "stop" if we find prevStop
    //current is equal to previous stop at this point in the code

        stop.setNext(currentStop.getNext()); //set stop.next to the stop after current (previous)
        stop.setPrevious(currentStop); // set stop.previous to the current
        currentStop.getNext().setPrevious(stop); //  Sets the previous pointer of the stop following prevStop to the new stop
        currentStop.setNext(stop); // Sets the next pointer of prevStop to the new stop

    }



    public BusStop removeStop(String name){
        if (start == null) {
            return null;
        }   // If the route is empty we return null

        BusStop currentStop = start;//store the currentStop for traversal
        while(!(currentStop.getName().equals(name))){ //traversal
            currentStop = currentStop.getNext(); //increment

            if(currentStop == start){ //At the end of the loop if we don't find prevStop
                return null;  //return null
            }
        }

       // store the previous and next stops with respect to currentStop
        BusStop previousStop = currentStop.getPrevious();
        BusStop nextStop = currentStop.getNext();

        // Update next and previous pointers to remove the current stop
        previousStop.setNext(nextStop);
        nextStop.setPrevious(previousStop);

        // If the current stop is the start of the route, update the start to the next stop
        if (currentStop.equals(start)) {
            start = nextStop;
        }

        // Disconnect the removed stop from the list
        currentStop.setNext(null);
        currentStop.setPrevious(null);

        return currentStop; //returns the stop that was removed



    }



    public BusStop getNextStop(String stopName) {
        if (start == null) {
            return null;
        }   // If the route is empty we return null

        BusStop currentStop = start; //store the currentStop for traversal
        while (!currentStop.getName().equals(stopName)) { //traversal
            currentStop = currentStop.getNext(); //increment

            if (currentStop.equals(start)) { //At the end of the loop if we don't find prevStop
                return null; //return null
            }
        }
        return currentStop.getNext(); // return the next stop after stopName


    }




    public boolean stopsAt(String stopName){    //circular ??????????
        if (start == null) {
            return false;
        }   // If the route is empty we return null

        BusStop currentStop = start; //store the currentStop for traversal
        while (!(currentStop.getName().equals(stopName))){ //Traversal
            currentStop = currentStop.getNext(); //Increment

            if(currentStop.equals(start)){  //At the end of the loop if we don't find prevStop
                return false;} //return false
        }
        return true; // return true because the route stops at stopName
    }




    public ArrayList<String> getTransferPoints(BusRoute otherRoute){
        ArrayList<String> ListTP = new ArrayList<>(); //create an ArrayList to store the Transfer Points
        if (start == null || otherRoute.start == null) { //checks if either list is empty
            return ListTP; // Return an empty list if either route is empty
        }

    BusStop currentStop = start;


        while (currentStop.getNext() != start) { //Traverse
            // Check if the current stop is also in the other route
            if (otherRoute.stopsAt(currentStop.getName())) {
                ListTP.add(currentStop.getName()); //In the case that they share the stop add it to ListTP
            }

            currentStop = currentStop.getNext(); //Increment
        }

        // Check the last stop after exiting the loop
        if (otherRoute.stopsAt(currentStop.getName())) {
            ListTP.add(currentStop.getName()); //In the case that they share the stop add it to ListTP
        }

        return ListTP;


    }



    // represents route using name of the route
    @Override
    public String toString() {
        return "Bus Route " + name;
    }

    // displays the route (forwards) from start to the last stop before start again
    public void displayRoute(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        }
        else {
            res += start.toString();
            BusStop c = start.getNext();
            while (c != start) {
                res += " -> " + c.toString();
                c = c.getNext();
            }
        }
        System.out.println(res);
    }

    // displays the route (backwards) from start to the last stop before start again to the second stop
    public void displayRouteBackwards(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        }
        else {
            res += start.toString();
            BusStop c = start.getPrevious();
            while (c != start) {
                res += " -> " + c.toString();
                c = c.getPrevious();
            }
        }
        System.out.println(res);
    }
}
