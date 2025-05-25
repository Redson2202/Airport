/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;
import cores.models.Passenger;
import cores.models.Flight;
import cores.models.Location;
import cores.models.Plane;
import cores.models.storage.Storage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 *
 * @author User
 */
public class InfoController {
    public static ArrayList<Flight> getAllflightsOrderedByDepartureDate(){
        return (ArrayList<Flight>) Storage.getAllFlights().stream().sorted(Comparator.comparing(Flight::getDepartureDate)).collect(Collectors.toList());
    }
    
    public static ArrayList<Passenger> getAllPassengersOrderedById(){
        return (ArrayList<Passenger>) Storage.getAllPassengers().stream().sorted(Comparator.comparing(Passenger::getId)).collect(Collectors.toList());
    }
    
    public static ArrayList<Location> getAllLocationsOrderedById(){
        return (ArrayList<Location>) Storage.getAllLocations().stream().sorted(Comparator.comparing(Location::getAirportId)).collect(Collectors.toList());
    }
    
    public static ArrayList<Plane> getAllPlanesOrderedById(){
        return (ArrayList<Plane>) Storage.getAllPlanes().stream().sorted(Comparator.comparing(Plane::getId)).collect(Collectors.toList());
    }
    
    public static ArrayList<Flight> getAllPassengerFlightsByDepartureDate(long id){
        return(ArrayList<Flight>) Storage.getPassengerFlights(id).stream().sorted(Comparator.comparing(Flight::getDepartureDate)).collect(Collectors.toList());
    }
}
   