/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.utils.Response;
import cores.utils.Status;
import cores.models.Location;
import cores.models.storage.Storage;

/**
 *
 * @author User
 */
public class LocationController {

    public static Response RegistrarAeropuerto(String id, String name, String city, String country, double latitude, double longitude) {
        try {
            Storage storage = Storage.getInstance();

            //Validar ID
            if (id == null || !id.matches("[A-Z]{3}")) {
                return new Response("El ID del aeropuerto debe tener 3 letras mayusculas", Status.BAD_REQUEST);
            }
            //Validar que no se repitan
            if (storage.getAirportById(id) != null) {
                return new Response("Ya existe un Aeropuerto con este ID", Status.BAD_REQUEST);
            }
            //Validar que la latitud y longitud esten dentro del rango
            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                return new Response("Latitud o Longitud fuera del rango valido", Status.BAD_REQUEST);
            }
            //Validar que todos los campos esten llenos
            String Latitude = Double.toString(latitude);
            String Longitude = Double.toString(longitude);
            if (name == null || name.trim().isEmpty() || city == null || city.trim().isEmpty() || country == null || country.trim().isEmpty() || Latitude == null || Latitude.trim().isEmpty() || Longitude == null || Longitude.trim().isEmpty()) {
                return new Response("Todos los campos deben ser llenados para completar el registro", Status.BAD_REQUEST);
            }
            //Agregando el nuevo aeropuerto
            Location location = new Location(id, name, city, country, latitude, longitude);
            storage.addLocation(location);
            return new Response("Aeropuerto registrado correctamente", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
