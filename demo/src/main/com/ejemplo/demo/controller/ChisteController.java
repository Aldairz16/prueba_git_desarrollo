package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Chiste;
import com.example.demo.service.ChisteService;

import java.util.List;
import java.util.Optional;

@RestController
public class ChisteController {
    private final ChisteService chisteService;

    public ChisteController(ChisteService chisteService) {
        this.chisteService = chisteService;
    }

    /**
     * obtenerTodosLosChistes():
     * Mapea la solicitud HTTP GET a /chistes para obtener todos los chistes.
     */
    @GetMapping("/chistes")
    public List<Chiste> obtenerTodosLosChistes() {
        return chisteService.todos();
    }

    /**
     * obtenerChistePorId(int id):
     * Mapea la solicitud HTTP GET a /chistes/{id} para obtener un chiste por su ID.
     */
    @GetMapping("/chistes/{id}")
    public ResponseEntity<Chiste> obtenerChistePorId(@PathVariable int id) {
        Optional<Chiste> chiste = chisteService.obtenerChiste(id);
        return chiste.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * obtenerChisteAleatorio():
     * Mapea la solicitud HTTP GET a /chistes/aleatorio para obtener un chiste aleatorio.
     */
    @GetMapping("/chistes/aleatorio")
    public Chiste obtenerChisteAleatorio() {
        return chisteService.obtenerChisteAleatorio();
    }

    /**
     * agregarNuevoChiste(Chiste chiste):
     * Mapea la solicitud HTTP POST a /chistes para agregar un nuevo chiste.
     */
    @PostMapping("/chistes")
    public ResponseEntity<Void> agregarNuevoChiste(@RequestBody Chiste chiste) {
        chisteService.agregarChiste(chiste);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * actualizarChiste(int id, Chiste nuevoChiste):
     * Mapea la solicitud HTTP PUT a /chistes/{id} para actualizar un chiste existente.
     */
    @PutMapping("/chistes/{id}")
    public ResponseEntity<Void> actualizarChiste(@PathVariable int id, @RequestBody Chiste nuevoChiste) {
        Optional<Chiste> chisteExistente = chisteService.obtenerChiste(id);
        if (chisteExistente.isPresent()) {
            chisteService.actualizarChiste(id, nuevoChiste);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * eliminarChiste(int id):
     * Mapea la solicitud HTTP DELETE a /chistes/{id} para eliminar un chiste por su ID.
     */
    @DeleteMapping("/chistes/{id}")
    public ResponseEntity<Void> eliminarChiste(@PathVariable int id) {
        Optional<Chiste> chisteExistente = chisteService.obtenerChiste(id);
        if (chisteExistente.isPresent()) {
            chisteService.eliminarChiste(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}