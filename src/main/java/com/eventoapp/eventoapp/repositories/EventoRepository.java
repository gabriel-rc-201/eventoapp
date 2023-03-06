package com.eventoapp.eventoapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.eventoapp.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {

}
