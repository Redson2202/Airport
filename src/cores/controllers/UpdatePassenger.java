/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.models.Passenger;
import cores.models.storage.Storage;
import cores.utils.Response;
import cores.utils.Status;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author User
 */
public class UpdatePassenger {

    public static Response updatePassenger(long PassengerId, String firstname, String lastname, int year, int month, int day, int phoneCode, long phone, String country) {
        try {
            String idString, phonecodeString, phoneString;
            Storage storage = Storage.getInstance();
            Passenger passenger = storage.getPassengerById(PassengerId);
            Passenger updatedPassenger = null;

            //Validando si el pasajero existe/valido
            if (passenger == null) {
                return new Response("Pasajero no encontrado", Status.NOT_FOUND);
            }
            //Validando que el nombre no este vacio
            if (updatedPassenger.getFirstname() == null || updatedPassenger.getLastname() == null) {
                return new Response("El nombre/Apellido no pueden estar vacios", Status.BAD_REQUEST);
            }
            //Validar Fecha
            if (year < 100) {
                try {
                    LocalDate parsed1Date = LocalDate.of(year, month, day);
                } catch (DateTimeException e) {
                    return new Response("Fecha de nacimiento invalida. Use el formato yyyy-MM-dd", Status.BAD_REQUEST);
                }
            }
            //validar Codigo telefono
            try {
                phonecodeString = String.valueOf(phoneCode);
                if (phoneCode < 0 || phonecodeString.length() > 3) {
                    return new Response("Codigo telefonico invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("El codigo telefonico debe ser numerico", Status.BAD_REQUEST);
            }
            //Validar Telefono
            try {
                phoneString = String.valueOf(phone);
                if (phone < 0 || phoneString.length() > 11) {
                    return new Response("Numero de telefono invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Telefono debe ser numerico", Status.BAD_REQUEST);
            }
            //Validar que el campo no este vacio
            if (country == null || country.trim().isEmpty()) {
                return new Response("No puede quedar informacion vacia. Porfavor llene todos los espacios.", Status.BAD_REQUEST);
            }
            LocalDate parsedDate = LocalDate.of(year, month, day);
            Passenger existingpassenger = storage.getPassengerById(updatedPassenger.getId());
            //Actualizar la informacion
            existingpassenger.setFirstname(firstname);
            existingpassenger.setLastname(lastname);
            existingpassenger.setBirthDate(parsedDate);
            existingpassenger.setCountry(country);
            existingpassenger.setPhone(phone);
            existingpassenger.setCountryPhoneCode(phoneCode);
            return new Response("Informacion actualizada de manera exitosa", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
