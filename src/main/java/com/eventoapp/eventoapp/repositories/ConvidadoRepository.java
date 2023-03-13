package com.eventoapp.eventoapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;

@Repository
public interface ConvidadoRepository extends JpaRepository<Convidado, UUID> {
    Iterable<Convidado> findByEvento(Evento evento);

    void deleteAllByEvento(Evento evento);
}
