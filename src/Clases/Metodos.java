package Clases;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import Interfaz.VentanaAgregarTarea;
public class Metodos {
  
   public static boolean esDNIValido(String dni) {
       // El DNI debe tener longitud 9 y contener solo números en los primeros 8 caracteres
       if (dni.length() != 9 || !dni.substring(0, 8).matches("\\d+")) {
           return false;
       }
       // El último carácter del DNI debe ser una letra
       char letra = Character.toUpperCase(dni.charAt(8));
       String digitos = dni.substring(0, 8);
       String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
       int numero = Integer.parseInt(digitos) % 23;
       return letra == letras.charAt(numero);
   }
   public static String generarPassword() {
       Random random = new Random();
       String caracteres[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
           "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C",
           "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
           "Y", "Z" };

       StringBuilder password = new StringBuilder();

       for (int i = 0; i < 8; i++) {
           int indiceAleatorio = random.nextInt(caracteres.length);
           password.append(caracteres[indiceAleatorio]);
       }

       return password.toString();
   }
   public static void fijarPrioridad(String fechaFin) {
       String prioridad = "";
       LocalDate fechaInicio = LocalDate.now();
       LocalDate fecha = LocalDate.parse(fechaFin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       long diferenciaDias = ChronoUnit.DAYS.between(fechaInicio, fecha);
       if (diferenciaDias <= 0) {
           prioridad = "Urgente";
       } else if (diferenciaDias <= 3) {
           prioridad = "Importante";
       } else {
           prioridad = "No urgente";
       }
       // Asignar la prioridad calculada a la variable prioridad
       VentanaAgregarTarea.prioridad = prioridad;
   }
}

