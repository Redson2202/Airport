/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.utils.Response;
import cores.models.Flight;
import cores.models.Location;
import cores.models.Plane;
import cores.utils.Status;
import cores.models.storage.Storage;
import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class FlightController {

    public static Response RegistrarVuelo(String id, String plid, String depId, String scaId, String arrId, int year, int month, int day, int hour, int minutes, int hoursdurationarrival, int hoursdurationscale, int minutesdurationarrival, int minutesdurationscale) {
        try {
            Storage storage = Storage.getInstance();
            //Validar ID del vuelo
            if (id == null || !id.matches("[A-Z]{3}\\d{3}")) {
                return new Response("El ID del vuelo debe seguir el formato XXXYYY donde cada X simboliza una letra y cada Y un numero (E.XYZ123)", Status.BAD_REQUEST);
            }
            // Validar que no hayan duplicados
            if (storage.getFlightById(id) != null) {
                return new Response("Ya existe un vuelo registrado con este ID", Status.BAD_REQUEST);
            }
            //Validar avion
            Plane planeid = storage.getPlaneById(plid);
            if (planeid == null) {
                return new Response("El ID no pertence a ningun avion registrado", Status.NOT_FOUND);
            }

            //Validar Ubicaciones
            Location departureId = storage.getAirportById(depId);
            Location scaleId = storage.getAirportById(scaId);
            Location arriveId = storage.getAirportById(arrId);
            if (departureId == null || arriveId == null) {
                return new Response("La ubicacion del aeropuerto de Salida/Llegada no pudo ser encontradam, ingrese un ID valido", Status.NOT_FOUND);
            }
            //Validar duracion
            if (scaleId == null) {
                if (hoursdurationarrival < 0 || minutesdurationarrival < 0 || minutesdurationarrival >= 60 || hoursdurationscale > 0 || minutesdurationscale < 0 || hoursdurationscale < 0||minutesdurationscale > 0) {
                    return new Response("Duracion invalida: Horas o minutos de salida/llegada son incorrectos (en caso no de tener escala dejelos en 0)", Status.BAD_REQUEST);
                }
            }
            if (scaleId != null) {
                if (hoursdurationarrival < 0 || minutesdurationarrival < 0 || minutesdurationarrival >= 60 || hoursdurationscale < 0 || minutesdurationscale < 0 || minutesdurationscale >= 60) {
                    return new Response("Duracion invalida: Horas o minutos de salida/escala/llegada son incorrectos", Status.BAD_REQUEST);
                }
            }
            //validar fecha
            LocalDateTime departureDate = LocalDateTime.of(year, month, day, hour, minutes);
            if (departureDate == null || departureDate.isBefore(LocalDateTime.now())) {
                return new Response("La fecha de salida debe ser posterior a la actual", Status.BAD_REQUEST);
            }
            //Añadir y almacenar el vuelo
            if (scaleId != null) {
                Flight flightsc = new Flight(id, planeid, departureId, scaleId, arriveId, departureDate, hoursdurationarrival, minutesdurationarrival, hoursdurationscale, minutesdurationscale);
                storage.addFlight(flightsc);
            } else {
                Flight flight = new Flight(id, planeid, departureId, arriveId, departureDate, hoursdurationarrival, minutesdurationarrival);
                storage.addFlight(flight);
            }
            return new Response("Vuelo registrado correctamente", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
