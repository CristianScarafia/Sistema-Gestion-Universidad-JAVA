package sistemareservas;

import java.util.Scanner;

public class SistemaReservasUI {
    private static Scanner scanner = new Scanner(System.in);
    private static SistemaReservas sistema = new SistemaReservas();
    private static Reporte reporte = new Reporte(sistema);

    public static void main(String[] args) {
        cargarDatos();
        mostrarMenu();
    }

    public static void cargarDatos() {
        sistema.cargarAulasDesdeArchivo("aulas.txt");
        sistema.cargarAsignaturasDesdeArchivo("asignaturas.txt");
        sistema.cargarCursosDesdeArchivo("cursos.txt");
        sistema.cargarEventosDesdeArchivo("eventos.txt");
    }

    public static void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Sistema de Reservas ---");
            System.out.println("1. Registrar reserva de asignatura");
            System.out.println("2. Registrar reserva de curso");
            System.out.println("3. Registrar reserva de evento");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Generar reporte de monto recaudado");
            System.out.println("6. Generar reporte de reservas por aula");
            System.out.println("7. Generar reporte de monto recaudado en archivo");
            System.out.println("8. Generar reporte de reservas por aula en archivo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    registrarReservaAsignatura();
                    break;
                case 2:
                    registrarReservaCurso();
                    break;
                case 3:
                    registrarReservaEvento();
                    break;
                case 4:
                    cancelarReserva();
                    break;
                case 5:
                    reporte.generarReporteMontoRecaudado();
                    break;
                case 6:
                    reporte.generarReporteReservasPorAula();
                    break;
                case 7:
                    generarReporteMontoRecaudadoEnArchivo();
                    break;
                case 8:
                    generarReporteReservasPorAulaEnArchivo();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void registrarReservaAsignatura(String codigoAsignatura) {
        Asignatura asignatura = sistema.buscarAsignaturaPorCodigo(codigoAsignatura);
        if (asignatura != null && sistema.registrarReservaAsignatura(asignatura)) {
            System.out.println("Reserva registrada con éxito.");
        } else {
            throw new IllegalArgumentException("Error al registrar la reserva. Verifique los datos.");
        }
    }

    public static void registrarReservaCurso(String codigoCurso, String fechaInicio, String horaInicio, String horaFin) {
        CursoExtension curso = sistema.buscarCursoPorCodigo(codigoCurso);
        if (curso != null && sistema.registrarReservaCurso(curso, fechaInicio, horaInicio, horaFin)) {
            System.out.println("Reserva registrada con éxito.");
        } else {
            throw new IllegalArgumentException("Error al registrar la reserva. Verifique los datos.");
        }
    }

    public static void registrarReservaEvento(String codigoEvento, boolean esExterno, String organizacion, double costoAlquiler) {
        Evento evento = sistema.buscarEventoPorCodigo(codigoEvento);
        if (evento != null && sistema.registrarReservaEvento(evento)) {
            System.out.println("Reserva registrada con éxito.");
        } else {
            throw new IllegalArgumentException("Error al registrar la reserva. Verifique los datos.");
        }
    }

    private static void registrarReservaAsignatura() {
        System.out.print("Ingrese el código de la asignatura: ");
        String codigoAsignatura = scanner.nextLine();
        registrarReservaAsignatura(codigoAsignatura);
    }

    private static void registrarReservaCurso() {
        System.out.print("Ingrese el código del curso: ");
        String codigoCurso = scanner.nextLine();
        System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
        String fechaInicio = scanner.nextLine();
        System.out.print("Ingrese la hora de inicio (HH:MM): ");
        String horaInicio = scanner.nextLine();
        System.out.print("Ingrese la hora de fin (HH:MM): ");
        String horaFin = scanner.nextLine();
        registrarReservaCurso(codigoCurso, fechaInicio, horaInicio, horaFin);
    }

    private static void registrarReservaEvento() {
        System.out.print("Ingrese el código del evento: ");
        String codigoEvento = scanner.nextLine();
        System.out.print("¿Es un evento externo? (true/false): ");
        boolean esExterno = scanner.nextBoolean();
        scanner.nextLine(); // Consumir la nueva línea
        String organizacion = "";
        double costoAlquiler = 0.0;
        if (esExterno) {
            System.out.print("Ingrese el nombre de la organización: ");
            organizacion = scanner.nextLine();
            System.out.print("Ingrese el costo de alquiler: ");
            costoAlquiler = scanner.nextDouble();
            scanner.nextLine(); // Consumir la nueva línea
        }
        registrarReservaEvento(codigoEvento, esExterno, organizacion, costoAlquiler);
    }

    private static void cancelarReserva() {
        System.out.print("Ingrese el número del aula: ");
        int numeroAula = scanner.nextInt();
        System.out.print("Ingrese el código de la reserva: ");
        int codigoReserva = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        if (sistema.cancelarReserva(numeroAula, codigoReserva)) {
            System.out.println("Reserva cancelada con éxito.");
        } else {
            System.out.println("Error al cancelar la reserva. Verifique los datos.");
        }
    }

    private static void generarReporteMontoRecaudadoEnArchivo() {
        System.out.print("Ingrese el nombre del archivo para el reporte de monto recaudado: ");
        String filePath = scanner.nextLine();
        reporte.generarReporteMontoRecaudadoEnArchivo(filePath);
        System.out.println("Reporte de monto recaudado generado en " + filePath);
    }

    private static void generarReporteReservasPorAulaEnArchivo() {
        System.out.print("Ingrese el nombre del archivo para el reporte de reservas por aula: ");
        String filePath = scanner.nextLine();
        reporte.generarReporteReservasPorAulaEnArchivo(filePath);
        System.out.println("Reporte de reservas por aula generado en " + filePath);
    }
}
