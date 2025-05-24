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

    public static Response RegistrarVuelo(String id, Plane plane, Location departureId, Location scaleId, Location arriveId, LocalDateTime departureDate, int hoursdurationarrival, int hoursdurationscale, int minutesdurationarrival, int minutesdurationscale) {
        try {
            Storage storage = Storage.getInstance();
            String did = null, sid = null, aid = null, avid;
            //Validar ID del vuelo
            if (id == null || !id.matches("[A-Z]{3}\\d{3}")) {
                return new Response("El ID del vuelo debe seguir el formato XXXYYY donde cada X simboliza una letra y cada Y un numero (E.123XYZ)", Status.BAD_REQUEST);
            }
            // Validar que no hayan duplicados
            if (storage.getFlightById(id) != null) {
                return new Response("Ya existe un vuelo registrado con este ID", Status.BAD_REQUEST);
            }
            //Validar avion
            Plane planeid = storage.getPlaneById(id);
            if (planeid == null) {
                return new Response("El ID no pertence a ningun avion registrado", Status.NOT_FOUND);
            }

            //Validar Ubicaciones
            Location departure = storage.getAirportById(did);
            Location scale = storage.getAirportById(sid);
            Location arrive = storage.getAirportById(aid);
            if (departure == null || arrive == null) {
                return new Response("La ubicacion del aeropuerto de Salida/Llegada no pudo ser encontradam, ingrese un ID valido", Status.NOT_FOUND);
            }
            //Validar duracion
            if (scale == null) {
                if (hoursdurationarrival < 0 || minutesdurationarrival < 0 || minutesdurationarrival >= 60 || hoursdurationscale > 0 || minutesdurationscale > 0) {
                    return new Response("Duracion invalida: Horas o minutos de salida/llegada son incorrectos (en caso no de tener escala dejelos en 0)", Status.BAD_REQUEST);
                }
            }
            if (scale != null) {
                if (hoursdurationarrival < 0 || minutesdurationarrival < 0 || minutesdurationarrival >= 60 || hoursdurationscale < 0 || minutesdurationscale < 0 || minutesdurationscale >= 60) {
                    return new Response("Duracion invalida: Horas o minutos de salida/escala/llegada son incorrectos", Status.BAD_REQUEST);
                }
            }
            //validar fecha
            if (departureDate == null || departureDate.isBefore(LocalDateTime.now())) {
                return new Response("La fecha de salida debe ser posterior a la actual", Status.BAD_REQUEST);
            }
            //Añadir y almacenar el vuelo
            if (scale != null) {
                Flight flightsc = new Flight(id, plane, departureId, scaleId, arriveId, departureDate, hoursdurationarrival, minutesdurationarrival, hoursdurationscale, minutesdurationscale);
                storage.addFlight(flightsc);
            } else {
                Flight flight = new Flight(id, plane, departureId, arriveId, departureDate, hoursdurationarrival, minutesdurationarrival);
                storage.addFlight(flight);
            }
            return new Response("Vuelo registrado correctamente", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
