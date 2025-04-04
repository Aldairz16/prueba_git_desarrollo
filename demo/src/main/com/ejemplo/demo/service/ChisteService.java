package com.example.demo.service;

import com.example.demo.model.Chiste;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ChisteService {
    private final List<Chiste> chistes;

    public ChisteService() {
        chistes = Collections.synchronizedList(new ArrayList<>());
        chistes.add(new Chiste(1, "¿Qué le dice un 0 a un 8? Bonito cinturón.", "Juan"));
        chistes.add(new Chiste(2, "¿Qué hace una abeja en el gimnasio? ¡Zum-ba!", "Marcos"));
        chistes.add(new Chiste(3, "¿Cuál es el colmo de un electricista? Que su hijo sea un apagado.", "Luis"));
        chistes.add(new Chiste(4, "¿Por qué estás hablando con la pared? ¡Porque la mesa no me responde!", "Anonimo"));
    }

    public List<Chiste> todos() {
        return new ArrayList<>(chistes); // Retorna una copia para evitar modificaciones externas
    }

    public Chiste obtenerChisteAleatorio() {
        return chistes.get((int) (Math.random() * chistes.size()));
    }

    public void agregarChiste(Chiste chiste) {
        chistes.add(chiste);
    }

    public Optional<Chiste> obtenerChiste(int id) {
        return chistes.stream().filter(chiste -> chiste.getId() == id).findFirst();
    }

    public void actualizarChiste(int id, Chiste nuevoChiste) {
        synchronized (chistes) {
            for (int i = 0; i < chistes.size(); i++) {
                if (chistes.get(i).getId() == id) {
                    chistes.set(i, nuevoChiste);
                    return;
                }
            }
        }
    }

    public void eliminarChiste(int id) {
        synchronized (chistes) {
            chistes.removeIf(chiste -> chiste.getId() == id);
        }
    }
}