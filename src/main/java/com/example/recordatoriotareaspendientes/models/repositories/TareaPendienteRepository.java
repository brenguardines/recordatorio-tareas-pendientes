package com.example.recordatoriotareaspendientes.models.repositories;

import com.example.recordatoriotareaspendientes.models.entities.TareaPendiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TareaPendienteRepository extends JpaRepository<TareaPendiente, Long> {
  List<TareaPendiente> findByCompletadaFalseAndFechaLimiteBefore(LocalDate fecha);
}
