/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.models.Flight;
import cores.models.Passenger;
import cores.models.storage.Storage;
import cores.utils.Response;
import cores.utils.Status;

/**
 *
 * @author User
 */
public class AddPassengerToFlightController {

    public static Response AgregarPasejorAVuelo(long passengerId, String flightId) {
        Storage storage = Storage.getInstance();
        //Validar la existencia del pasajero
        Passenger passenger = storage.getPassengerById(passengerId);
        if (passenger == null) {
            return new Response("Pasejero no encontrado", Status.NOT_FOUND);
        }
        //Validar la existencia del vuelo
        Flight flight = storage.getFlightById(flightId);
        if (flight == null) {
            return new Response("Vuelo no encontrado", Status.NOT_FOUND);
        }
        flight.addPassenger(passenger);
        passenger.addFlight(flight);
        return new Response("Pasajero agregado al vuelo exitosamente", Status.OK);
    }
}
