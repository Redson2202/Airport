/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.models.storage;

import cores.models.Location;
import cores.models.Passenger;
import cores.models.Plane;
import java.util.HashMap;
import java.util.Map;

public class Storage {

    private static Storage instance;
    private Map<Long, Passenger> passengers;
    private Map<String,Plane> planes;
    private Map<String,Location> locations;
    private Storage(){
        passengers = new HashMap<>();
    }
    public static Storage getInstance(){
        if(instance==null){
            instance = new Storage();
        }
        return instance;
    }
    
    //Añadir pasagero
    public boolean addPassenger(Passenger passenger){
        if(passengers.containsKey(passenger.getId())){
            return false; //Ya existe el pasagero
        }
        passengers.put(passenger.getId(),passenger);
        return true; 
    }
    
    //Obtener pasajero por ID
    public Passenger  getPassengerById(long id){
        return passengers.get(id);
    }
    
    //Obtener todos los pasajeros
    public Map<Long, Passenger> getAllPassengers(){
        return new HashMap<>(passengers); //para evitar errores :p
    }
    
    // Añadir Avion
    public boolean addPlane(Plane plane){
        if(planes.containsKey(plane.getId())){
            return false; // Ya existe este avion
        }
        planes.put(plane.getId(), plane);
        return true;
    }
    
    //Obtener Avion por ID
    public Plane getPlaneById(String id){
        return planes.get(id);
    }
    
    //Obtener todos los Aviones
    public Map<String, Plane> getAllPlanes(){
        return new HashMap<>(planes); // evitar errores :p
    }
    
    public boolean addLocation(Location location){
        if(locations.containsKey(location.getAirportId())){
            return false; // ya existe este aeropuerto
        }
        locations.put(location.getAirportId(), location);
        return true;
    }
}
