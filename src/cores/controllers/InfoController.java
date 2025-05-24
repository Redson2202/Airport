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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author User
 */
public class InfoController {
    public static List<Flight> getAllflightsOrderedByDepartureDate(){
        List<Flight> flights = Storage.getAllFlights();
        Collections.sort(flights,new Comparator<Flight>(){
        public int compare(Flight f1, Flight f2){
            return f1.getDepartureDate().compareTo(f2.getDepartureDate());
        }
    });
        return flights;
    }
    
    public static List<Passenger> getAllPassengersOrderedById(){
        return Storage.getAllPassengers().stream().sorted(Comparator.comparing(Passenger::getId)).collect(Collectors.toList());
    }
    
    public static List<Location> getAllLocationsOrderedById(){
        return Storage.getAllLocations().stream().sorted(Comparator.comparing(Location::getAirportId)).collect(Collectors.toList());
    }
    
    public static List<Plane> getAllPlanesOrderedById(){
        return Storage.getAllPlanes().stream().sorted(Comparator.comparing(Plane::getId)).collect(Collectors.toList());
    }
    
}
   