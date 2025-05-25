/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.utils.Response;
import cores.utils.Status;
import cores.models.Flight;
import cores.models.storage.Storage;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class DelayFlightController {

    public static Response Delayflight(String flightid, int delayhours, int delayminutes) {
        try {
            Storage storage = Storage.getInstance();
            //Validando que el id no este vacio
            if (flightid == null || flightid.isEmpty()) {
                return new Response("Id no valido", Status.BAD_REQUEST);
            }
            //Validar que las horas/minutos de retraso sean >0
            if (delayhours < 0 || delayminutes < 0 || (delayhours == 0 & delayminutes == 0)) {
                return new Response("El tiempo de retraso debe ser mayor de 00:00", Status.BAD_REQUEST);
            }
            //Validar que el vuelo exista
            Flight flight = storage.getFlightById(flightid);
            if (flight == null) {
                return new Response("Vuelo no encontrado", Status.NOT_FOUND);
            }
            LocalDateTime orignalDeparture = flight.getDepartureDate();
            Duration delay = Duration.ofHours(delayhours).plusMinutes(delayminutes);
            LocalDateTime newDeparture = orignalDeparture.plus(delay);
            flight.setDepartureDate(newDeparture);
            return new Response("Vuelo retrasado correctamente", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
