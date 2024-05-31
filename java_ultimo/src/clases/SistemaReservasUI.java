
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SistemaReservasUI {
    private static SistemaReservas sistema = new SistemaReservas();
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        cargarEstadoInicial();
        mostrarMenu();
    }

    private static void cargarEstadoInicial() {
        System.out.print("¿Desea cargar el estado del sistema desde un archivo? (s/n): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            sistema.cargarDatos();  // Usa cargarDatos para leer el archivo
        } else {
            cargarDatosIniciales();
        }
    }

    private static void cargarDatosIniciales() {
        sistema.cargarDatos();
    }

    private static void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Listar aulas");
            System.out.println("2. Listar asignaturas");
            System.out.println("3. Listar cursos");
            System.out.println("4. Listar eventos");
            System.out.println("5. Consultar aulas por piso");
            System.out.println("6. Consultar aulas por código de entidad");
            System.out.println("7. Registrar reserva de curso");
            System.out.println("8. Registrar reserva de evento");
            System.out.println("9. Cancelar reserva");
            System.out.println("10. Generar reporte de recaudación");
            System.out.println("11. Generar reporte de reservas");
            System.out.println("12. Listar reservas");
            System.out.println("13. Guardar estado del sistema");
            System.out.println("14. Salir");
            System.out.print("Seleccione una opción: ");
            //llll

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    sistema.listarAulas();
                    break;
                case 2:
                    sistema.listarAsignaturas();
                    break;
                case 3:
                    sistema.listarCursos();
                    break;
                case 4:
                    sistema.listarEventos();
                    break;
                case 5:
                    consultarAulasPorPiso();
                    break;
                case 6:
                    consultarAulasPorCodigoEntidad();
                    break;
                case 7:
                    registrarReservaCurso();
                    break;
                case 8:
                    registrarReservaEvento();
                    break;
                case 9:
                    cancelarReserva();
                    break;
                case 10:
                    sistema.generarReporteRecaudacion();
                    break;
                case 11:
                    sistema.generarReporteReservas();
                    break;
                case 12:
                    sistema.listarReservas();
                    break;
                case 13:
                    guardarEstadoSistema();
                    break;
                case 14:
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    private static void consultarAulasPorPiso() {
        System.out.print("Ingrese el número de piso: ");
        int piso = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        sistema.consultarAulasPorPiso(piso);
    }

    private static void consultarAulasPorCodigoEntidad() {
        System.out.print("Ingrese el código de la entidad (asignatura, curso, evento): ");
        String codigo = scanner.nextLine();
        sistema.consultarAulasPorCodigoEntidad(codigo);
    }
    
    private static void registrarReservaCurso() {
        try {
            System.out.print("Ingrese el código del curso: ");
            String codigoCurso = scanner.nextLine();
            System.out.print("Ingrese la fecha de inicio (yyyy-MM-dd): ");
            String fechaInicioStr = scanner.nextLine();  // Cambiado a String
            System.out.print("Ingrese el rango horario (hh:mm-hh:mm): ");
            String rangoHorario = scanner.nextLine();
            sistema.registrarReservaCurso(codigoCurso, fechaInicioStr, rangoHorario);
        } catch (Exception e) {
            System.out.println("Error al registrar la reserva del curso: " + e.getMessage());
        }
    }

    private static void registrarReservaEvento() {
        try {
            System.out.print("Ingrese el código del evento: ");
            String codigoEvento = scanner.nextLine();
            
            // Solicitar la fecha del evento
            System.out.print("Ingrese la fecha del evento (yyyy-MM-dd): ");
            String fechaEvento = scanner.nextLine();
            
            System.out.print("Ingrese el horario del evento (hh:mm-hh:mm): ");
            String horario = scanner.nextLine();
            
            System.out.print("¿Es un evento externo? (true/false): ");
            boolean esExterno = scanner.nextBoolean();
            scanner.nextLine(); // Consumir el salto de línea
            
            String organizacion = "";
            double costoAlquiler = 0.0;
            if (esExterno) {
                System.out.print("Ingrese el nombre de la organización: ");
                organizacion = scanner.nextLine();
                System.out.print("Ingrese el costo del alquiler: ");
                costoAlquiler = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salto de línea
            }
            
            // Llamada al método con la fecha incluida
            sistema.registrarReservaEvento(codigoEvento, fechaEvento, horario, esExterno, organizacion, costoAlquiler);
        } catch (Exception e) {
            System.out.println("Error al registrar la reserva del evento: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        System.out.print("Ingrese el número de aula: ");
        int numeroAula = scanner.nextInt();
        System.out.print("Ingrese el código de la reserva: ");
        int codigoReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        sistema.cancelarReserva(numeroAula, codigoReserva);
    }

    private static void guardarEstadoSistema() {
        System.out.print("Ingrese el nombre del archivo: ");
        String archivo = scanner.nextLine();
        sistema.guardarEstado(archivo);
    }
}