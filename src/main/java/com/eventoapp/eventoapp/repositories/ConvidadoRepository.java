package com.eventoapp.eventoapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.eventoapp.models.Convidado;

@Repository
public interface ConvidadoRepository extends JpaRepository<Convidado, UUID> {

}
