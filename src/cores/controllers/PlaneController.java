/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cores.controllers;

import cores.models.Plane;
import cores.models.storage.Storage;
import cores.utils.Response;
import cores.utils.Status;

/**
 *
 * @author User
 */
public class PlaneController {

    public static Response registrarAvion(String id, String brand, String model, int maxCapacity, String airline) {
        try {
            Storage storage = Storage.getInstance();
            String maxcapacity = Integer.toString(maxCapacity);
            //Validar ID del avion
            if (id == null || !id.matches("[A-Z]{2}\\d{5}")) {
                return new Response("El ID del avion debe seguir el formato XXYYYYY (Donde cada X pertenece a una letra (A-Z) y cada Y un numero entre (0-9) ", Status.BAD_REQUEST);
            }

            //Validar que no exista un Avion con el mismo ID
            if (storage.getPlaneById(id) != null) {
                return new Response("Ya existe un avion con este ID", Status.BAD_REQUEST);
            }

            //Validar campos obligatorios
            if (model == null || model.trim().isEmpty() || id == null || id.trim().isEmpty() || brand == null || brand.trim().isEmpty() || maxcapacity == null || maxcapacity.trim().isEmpty() || airline == null || airline.trim().isEmpty()) {
                return new Response("Todos los campos deben estar llenos para hacer el registro", Status.BAD_REQUEST);
            }
            //Agregando el nuevo avion
            Plane plane = new Plane(id, brand, model, maxCapacity, airline);
            storage.addPlane(plane);
            return new Response("Avion registrado correctamente", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
