/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.models.storage;

import cores.models.Flight;
import cores.models.Location;
import cores.models.Passenger;
import cores.models.Plane;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static Storage instance;
    private static ArrayList<Passenger> passengers;
    private static ArrayList<Plane> planes;
    private static ArrayList<Location> locations;
    private static ArrayList<Flight> flights;

    private Storage() {
        Storage.passengers = new ArrayList();
        Storage.planes = new ArrayList();
        Storage.locations = new ArrayList();
        Storage.flights = new ArrayList();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    //Añadir pasagero
    public boolean addPassenger(Passenger passenger) {
        for (Passenger p : Storage.passengers) {
            if (p.getId() == passenger.getId()) {
                return false; // Ya existe ese pasajero
            }
        }
        Storage.passengers.add(passenger);
        return true;
    }

    //Obtener pasajero por ID
    public Passenger getPassengerById(long id) {
        for (Passenger passenger : Storage.passengers) {
            if (passenger.getId() == id) {
                return passenger;
            }
        }
        return null;
    }

    //Obtener todos los pasajeros
    public static ArrayList<Passenger> getAllPassengers() {
        return passengers;
    }

    // Añadir Avion
    public boolean addPlane(Plane plane) {
        for (Plane pl : Storage.planes) {
            if (pl.getId().equals(plane.getId())) {
                return false; // Ya existe este avion
            }
        }
        Storage.planes.add(plane);
        return true;
    }

    //Obtener Avion por ID
    public Plane getPlaneById(String id) {
        for (Plane plane : Storage.planes) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        return null;
    }

    //Obtener todos los Aviones
    public static List<Plane> getAllPlanes() {
        return planes;
    }

    // Añadir Aeropuerto
    public boolean addLocation(Location location) {
        for (Location l : Storage.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                return false; // Ya existe este avion
            }
        }
        Storage.locations.add(location);
        return true;
    }

    // Obtener aeropuerto por ID
    public Location getAirportById(String id) {
        for (Location location : Storage.locations) {
            if (location.getAirportId().equals(id)) {
                return location;
            }
        }
        return null;
    }

    //Obtener todos los aeropuertos
    public static List<Location> getAllLocations() {
        return locations;
    }

    //Añadir vuelo
    public boolean addFlight(Flight flight) {
        for (Flight f : Storage.flights) {
            if (f.getId().equals(flight.getId())) {
                return false; // Ya existe este avion
            }
        }
        Storage.flights.add(flight);
        return true;
    }

    //Obtener vuelo por id
    public Flight getFlightById(String id) {
        for (Flight flight : Storage.flights) {
            if (flight.getId().equals(id)) {
                return flight;
            }
        }
        return null;
    }

    //Obtener todos los vuelos
    public static List<Flight> getAllFlights() {
        return flights;
    }
}
