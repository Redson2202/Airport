/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.models.Passenger;
import cores.utils.Response;
import cores.utils.Status;
import cores.models.storage.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

public class PassengerController {

    public static Response RegistrarPasajero(long id, String name, String lastname, int year, int month, int day, int phonecode, long phone, String country) {
        try {
            Storage storage = Storage.getInstance();
            String idString, phonecodeString, phoneString;
            // Validar Id
            try {
                idString = String.valueOf(id);
                if (id < 0 | idString.length() > 15) {
                    return new Response("ID debe ser mayor o igual a 0 y no tener mas de 15 digitos", Status.BAD_REQUEST);
                }
                if (storage.getPassengerById(id) != null) {
                    return new Response("Ya existe un pasajero con ese ID", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("ID debe ser numerico", Status.BAD_REQUEST);
            }
            // validar nombre y apellido 
            if (name == null || name.trim().isEmpty() || lastname == null || lastname.trim().isEmpty()) {
                return new Response("Nombre y Apellido no puede estar vacios", Status.BAD_REQUEST);
            }
            //Validar fecha
            if (year < 100) {
                try {
                    LocalDate parsed1Date = LocalDate.of(year, month, day);
                } catch (DateTimeException e) {
                    return new Response("Fecha de nacimiento invalida. Use el formato yyyy-MM-dd", Status.BAD_REQUEST);
                }
            }
            LocalDate parsedDate = LocalDate.of(year, month, day);
            //validar Codigo telefono
            try {
                phonecodeString = String.valueOf(phonecode);
                if (phonecode < 0 || phonecodeString.length() > 3) {
                    return new Response("Codigo telefonico invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("El codigo telefonico debe ser numerico", Status.BAD_REQUEST);
            }
            //Validar telefono
            try {
                phoneString = String.valueOf(phone);
                if (phone < 0 || phoneString.length() > 11) {
                    return new Response("Numero de telefono invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Telefono debe ser numerico", Status.BAD_REQUEST);
            }

            //validar campos
            String yearString = String.valueOf(year), monthString = String.valueOf(month), dayString = String.valueOf(day);

            if (country == null || country.trim().isEmpty()) {
                return new Response("No puede quedar informacion vacia. Porfavor llene todos los espacios.", Status.BAD_REQUEST);
            }
            //Crear objeto passenger y almacenarlo
            Passenger newPassenger = new Passenger(id, name, lastname, parsedDate, phonecode, phone, country);
            storage.addPassenger(newPassenger);
            return new Response("Pasajero registrado de manera exitosa", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
