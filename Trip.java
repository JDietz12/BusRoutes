/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. _Jackson Dietz_
*/

// class representing a user trip as a doubly-linked list
public class Trip {
    private BusStop start; // starting stop of the trip

    public Trip(){
        this.start = null;
    }

    // returns the start of the trip
    public BusStop getStart(){
        return start;
    }



    public void addStop(String stopName, String routeName) {
    BusStop stop = new BusStop(stopName, routeName, null, null); // create a new busStop object to be added
    if (start == null) { // if the list is empty
        start = stop; //add stop to the list (the only element on the list
    }

    else{
    BusStop currentStop = start;   //store the currentStop for traversal
        while(currentStop.getNext() != null){ // traversal
            currentStop = currentStop.getNext(); //increment
            //Check whats up with the NULL
        }
        currentStop.setNext(stop); // add stop to the end of the list
        stop.setPrevious(currentStop); //since it's a doubly linked list add a reverse
        // pointer on stop to point to currentStop
    }
    }



    public void removeStop(String stopName, String routeName) {
        if (start == null) {
            return; // If the trip is empty do nothing
        }

        BusStop currentStop = start; //store the currentStop for traversal
        while (currentStop != null) {
            // Check if the current stop matches the given name and route
            if (currentStop.getName().equals(stopName) && currentStop.getRoute().equals(routeName)) {
                BusStop previousStop = currentStop.getPrevious(); //store element before
                BusStop nextStop = currentStop.getNext(); //store element after

                // Update next and previous pointers to remove the current stop
                if (previousStop != null) {
                    previousStop.setNext(nextStop);
                } else {
                    // If the current stop is the start of the trip, update the start to the next stop
                    start = nextStop;
                }

                if (nextStop != null) { //if next stop is null
                    nextStop.setPrevious(previousStop); // set the next stops previous stop to previous stop
                }

                // Disconnect the removed stop from the list to limit confusion
                currentStop.setNext(null);
                currentStop.setPrevious(null);

                return; // Stop found and removed, exit the method
            }

            currentStop = currentStop.getNext(); // Increment
        }
    }


    // displays the trip (forwards) from start to end
    public void displayTrip(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        }
        else {
            res += start.toString();
            BusStop c = start.getNext();
            while (c != null) {
                res += "\n " + c.toString();
                c = c.getNext();
            }
        }
        System.out.println(res);
    }

    // displays the trip (backwards) from end to start
    public void displayTripBackwards(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        }
        else {
            BusStop c = start;
            while(c.getNext() != null){
                c = c.getNext();
            }
            while (c != null) {
                res += "\n " + c.toString();
                c = c.getPrevious();
            }
        }
        System.out.println(res);
    }
}
