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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PassengerController {

    public static Response RegistrarPasajero(String id, String name, String lastname, String phonecode, String phone, String birthDate, String country) {
        try {
            Storage storage = Storage.getInstance();
            long idInt, phoneInt;
            int phoneCodeInt;
            // Validar Id
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0 | id.length() > 15) {
                    return new Response("ID debe ser mayor o igual a 0 y no tener mas de 15 digitos", Status.BAD_REQUEST);
                }
                if (storage.getPassengerById(idInt) != null) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            LocalDate parsedDate;
            try {
                Date date = sdf.parse(birthDate);
                parsedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                return new Response("Fecha de nacimiento invalida. Use el formato yyyy-MM-dd", Status.BAD_REQUEST);
            }
            //validar Codigo telefono
            try {
                phoneCodeInt = Integer.parseInt(phonecode);
                if (phoneCodeInt < 0 || phonecode.length() > 3) {
                    return new Response("Codigo telefonico invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("El codigo telefonico debe ser numerico", Status.BAD_REQUEST);
            }
            //Validar telefono
            try {
                phoneInt = Integer.parseInt(phone);
                if (phoneInt < 0 || phone.length() > 11) {
                    return new Response("Numero de telefono invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Telefono debe ser numerico", Status.BAD_REQUEST);
            }

            //validar campos
            if (name == null || name.trim().isEmpty() || lastname == null || lastname.trim().isEmpty() || country == null || country.trim().isEmpty() || birthDate == null || birthDate.trim().isEmpty()) {
                return new Response("No puede quedar informacion vacia. Porfavor llene todos los espacios.", Status.BAD_REQUEST);
            }
            //Crear objeto passenger y almacenarlo
            Passenger newPassenger = new Passenger(idInt, name, lastname, parsedDate, phoneCodeInt, phoneInt, country);
            storage.addPassenger(newPassenger);
            return new Response("Pasajero registrado de manera exitosa", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
