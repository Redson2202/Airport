/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.models.Passenger;
import cores.models.storage.Storage;
import cores.utils.Response;
import cores.utils.Status;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author User
 */
public class UpdatePassenger {

    public static Response updatePassenger(Passenger updatedPassenger, long PassengerId) {
        try {
            Storage storage = Storage.getInstance();
            Passenger passenger = storage.getPassengerById(PassengerId);

            //Validando si el pasajero existe/valido
            if (passenger == null) {
                return new Response("Pasajero no encontrado", Status.NOT_FOUND);
            }
            //Validando que el nombre no este vacio
            if (updatedPassenger.getFirstname() == null || updatedPassenger.getLastname() == null) {
                return new Response("El nombre/Apellido no pueden estar vacios", Status.BAD_REQUEST);
            }
            Passenger existingpassenger = storage.getPassengerById(updatedPassenger.getId());
            //Actualizar la informacion
            existingpassenger.setFirstname(updatedPassenger.getFirstname());
            existingpassenger.setBirthDate(updatedPassenger.getBirthDate());
            existingpassenger.setLastname(updatedPassenger.getLastname());
            existingpassenger.setCountry(updatedPassenger.getCountry());
            existingpassenger.setPhone(updatedPassenger.getPhone());
            existingpassenger.setCountryPhoneCode(updatedPassenger.getCountryPhoneCode());
            return new Response("Informacion actualizada de manera exitosa", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
