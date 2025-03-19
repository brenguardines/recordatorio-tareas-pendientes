package com.example.recordatoriotareaspendientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //Habilitamos las tareas programadas con Spring Boot
public class RecordatorioTareasPendientesApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecordatorioTareasPendientesApplication.class, args);
  }
}
