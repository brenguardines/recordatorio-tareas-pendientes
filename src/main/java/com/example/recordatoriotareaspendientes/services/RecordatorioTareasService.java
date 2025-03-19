package com.example.recordatoriotareaspendientes.services;

import com.example.recordatoriotareaspendientes.models.entities.TareaPendiente;
import com.example.recordatoriotareaspendientes.models.repositories.TareaPendienteRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecordatorioTareasService {
  private final JavaMailSender mailSender;
  private final TareaPendienteRepository tareaPendienteRepository;

  @Scheduled(cron = "0 30 8 * * ?") //Se ejecuta todos los dias a las 8:30 AM
  public void enviarRecordatorioDiario(){
    LocalDate hoy = LocalDate.now();
    List<TareaPendiente> tareaPendientes = tareaPendienteRepository.findByCompletadaFalseAndFechaLimiteBefore(hoy.plusDays(1));

    if(!tareaPendientes.isEmpty()){
      String destinatario ="MailDelUsuarioDestinatario";
      String asunto = "Tareas Pendientes para Hoy";
      String contenido = generarContenidoHTML(tareaPendientes);

      enviarCorreo(destinatario, asunto, contenido);
    }
  }

  private String generarContenidoHTML(List<TareaPendiente> tareas){
    String listaTareas = tareas.stream()
        .map(tarea -> "<li>" + tarea.getDescripcion() + " (Fecha limite: " + tarea.getFechaLimite() + ")</li>")
        .collect(Collectors.joining());

    return """
        <h2>Tareas Pendientes</h2>
        <p>Estas son las tareas que tenes para hoy:</p>
        <ul>%s</ul> 
        """.formatted(listaTareas);
  }

  private void enviarCorreo(String destinatario, String asunto, String contenido){
    try{
      MimeMessage mensaje = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

      helper.setTo(destinatario);
      helper.setSubject(asunto);
      helper.setText(contenido, true);

      mailSender.send(mensaje);
      System.out.println("Correo enviado a: " + destinatario);
    } catch (Exception e){
      System.err.println("Error al enviar el correo: " + e.getMessage());
    }
  }
}
