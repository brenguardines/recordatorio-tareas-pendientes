
# Recordatorio de Tareas Pendientes

## Descripción

Este proyecto es una aplicación desarrollada con Spring Boot que permite enviar correos electrónicos automáticos recordando tareas pendientes. El objetivo es que todos los días, a una hora determinada, se verifiquen las tareas no completadas y se envíe un correo con los pendientes del día.

---

## Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Mail
- Scheduler (tareas programadas con `@Scheduled`)
- MySQL 8.0
- Maven

### IDE
- IntelliJ IDEA

---

## Requisitos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven instalado (o usar wrapper)

---

## Instrucciones de configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/brenguardines/recordatorio-tareas-pendientes.git
cd recordatorio-tareas-pendientes
```

### 2. Configurar la base de datos

Asegurate de tener MySQL instalado y corriendo. Luego, creá la base de datos ejecutando:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS tareas_pendientes;"
```

### 3. Configurar `application.properties`

En el archivo src/main/resources/application.properties:

```properties
spring.application.name=recordatorio-tarea-pendientes

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tuMail
spring.mail.password=tuContraseñaDelMail
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

spring.datasource.url=jdbc:mysql://localhost:3306/tareas_pendientes
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
```
⚠️ Reemplazá las credenciales por las tuyas. Si usás Gmail, asegurate de tener habilitado el acceso con app password.

### 4. Modificar el destinatario del correo

En la clase `RecordatorioTareasService` modificá la siguiente línea con el correo del destinatario que debe recibir los recordatorios diarios:
 
```java
// Dirección de correo a la que se enviará el recordatorio
String destinatario = "MailDelUsuarioDestinatario";
```

## Ejecución del proyecto

Desde la terminal o IntelliJ IDEA podés ejecutar:

```bash
mvn clean install
mvn spring-boot:run
```
O ejecutar directamente la clase principal
`RecordatorioTareasPendientesApplication`.

## Funcionamiento del recordatorio

El servicio `RecordatorioTareasService` contiene una tarea programada (`@Scheduled`) que se ejecuta todos los días a las 08:30 AM.

```java
@Scheduled(cron = "0 30 8 * * ?") // 08:30 AM todos los días
```
Se consultan las tareas no completadas con fecha límite menor o igual a hoy, y se envía un correo en formato HTML al destinatario.

## Estructura general del proyecto

```
📦 recordatorio-tareas-pendientes
 ┣ 📂models
 ┃ ┣ 📂entities
 ┃ ┃ ┗ 📜 TareaPendiente.java
 ┃ ┗ 📂repositories
 ┃   ┗ 📜 TareaPendienteRepository.java
 ┣ 📂services
 ┃ ┗ 📜 RecordatorioTareasService.java
 ┣ 📜 RecordatorioTareasPendientesApplication.java
 ┗ 📜 application.properties → ubicado en src/main/resources

```

👩‍💻 Desarrollado por [Brenda Guardines](https://www.linkedin.com/in/brenda-guardines)
