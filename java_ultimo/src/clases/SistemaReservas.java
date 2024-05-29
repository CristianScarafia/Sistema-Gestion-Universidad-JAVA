import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SistemaReservas implements Serializable {
    private List<Aula> aulas;
    private List<Asignatura> asignaturas;
    private List<CursoExtension> cursos;
    private List<Evento> eventos;

    public SistemaReservas() {
        this.aulas = new ArrayList<>();
        this.asignaturas = new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\datos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                System.out.println("Procesando línea: " + Arrays.toString(datos));  // Mensaje de depuración
                switch (datos[0]) {
                    case "AULA":
                        cargarAula(datos);
                        break;
                    case "ASIGNATURA":
                        cargarAsignatura(datos);
                        break;
                    case "CURSO":
                        cargarCurso(datos);
                        break;
                    case "EVENTO":
                        cargarEvento(datos);
                        break;
                    default:
                        System.out.println("Tipo desconocido: " + datos[0]);
                }
            }
            aulas.sort(Comparator.comparingInt(Aula::getNumero));
            System.out.println("Datos cargados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarAula(String[] datos) {
        try {
            int numero = Integer.parseInt(datos[1]);
            int capacidad = Integer.parseInt(datos[2]);
            Aula aula = new Aula(numero, capacidad);
            aulas.add(aula);
            System.out.println("Aula cargada: " + aula.getNumero() + ", Capacidad: " + aula.getCapacidad());
        } catch (Exception e) {
            System.out.println("Error al cargar aula: " + e.getMessage());
        }
    }

    private void cargarAsignatura(String[] datos) {
        try {
            String codigo = datos[1];
            String nombre = datos[2];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = sdf.parse(datos[3]);
            Date fechaFin = sdf.parse(datos[4]);
            String diaSemana = datos[5];
            String horarioInicio = datos[6];
            String horarioFin = datos[7];
            int alumnosInscriptos = Integer.parseInt(datos[8]);
            Asignatura asignatura = new Asignatura(codigo, nombre, fechaInicio, fechaFin, diaSemana, horarioInicio, horarioFin, alumnosInscriptos);
            asignaturas.add(asignatura);
            System.out.println("Asignatura cargada: " + asignatura.getCodigo() + ", Nombre: " + asignatura.getNombre());
        } catch (Exception e) {
            System.out.println("Error al cargar asignatura: " + e.getMessage());
        }
    }

    private void cargarCurso(String[] datos) {
        try {
            String codigo = datos[1];
            String descripcion = datos[2];
            int alumnosInscriptos = Integer.parseInt(datos[3]);
            int cantidadClases = Integer.parseInt(datos[4]);
            double costoPorAlumno = Double.parseDouble(datos[5]);
            CursoExtension curso = new CursoExtension(codigo, descripcion, alumnosInscriptos, cantidadClases, costoPorAlumno);
            cursos.add(curso);
            System.out.println("Curso cargado: " + curso.getCodigo() + ", Descripción: " + curso.getDescripcion());
        } catch (Exception e) {
            System.out.println("Error al cargar curso: " + e.getMessage());
        }
    }

    private void cargarEvento(String[] datos) {
        try {
            String codigo = datos[1];
            String descripcion = datos[2];
            int maxParticipantes = Integer.parseInt(datos[3]);
            boolean esExterno = Boolean.parseBoolean(datos[4]);
            String organizacion = datos.length > 5 ? datos[5] : "";
            double costoAlquiler = datos.length > 6 ? Double.parseDouble(datos[6]) : 0.0;
            Evento evento = new Evento(codigo, descripcion, maxParticipantes, esExterno, organizacion, costoAlquiler);
            eventos.add(evento);
            System.out.println("Evento cargado: " + evento.getCodigo() + ", Descripción: " + evento.getDescripcion());
        } catch (Exception e) {
            System.out.println("Error al cargar evento: " + e.getMessage());
        }
    }

    public void cargarEstado(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            this.aulas = (List<Aula>) ois.readObject();
            this.asignaturas = (List<Asignatura>) ois.readObject();
            this.cursos = (List<CursoExtension>) ois.readObject();
            this.eventos = (List<Evento>) ois.readObject();
            System.out.println("Estado del sistema cargado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar el estado del sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listarAulas() {
        for (Aula aula : aulas) {
            System.out.println("Aula: " + aula.getNumero() + ", Capacidad: " + aula.getCapacidad());
        }
    }

    public void listarAsignaturas() {
        for (Asignatura asignatura : asignaturas) {
            System.out.println("Asignatura: " + asignatura.getCodigo() + ", Nombre: " + asignatura.getNombre());
        }
    }

    public void listarCursos() {
        for (CursoExtension curso : cursos) {
            System.out.println("Curso: " + curso.getCodigo() + ", Descripción: " + curso.getDescripcion());
        }
    }

    public void listarEventos() {
        for (Evento evento : eventos) {
            System.out.println("Evento: " + evento.getCodigo() + ", Descripción: " + evento.getDescripcion());
        }
    }

    public void listarReservas() {
        boolean hayReservas = false;
        for (Aula aula : aulas) {
            List<Reserva> reservas = aula.getReservas();
            if (!reservas.isEmpty()) {
                System.out.println("Reservas para el aula " + aula.getNumero() + ":");
                for (Reserva reserva : reservas) {
                    System.out.println(" - Reserva " + reserva.getCodigo() + ": " + reserva.getTipo() + " (" + reserva.getCodigoEntidad() + ") el " + reserva.getFecha() + " en el horario " + reserva.getRangoHorario());
                    hayReservas = true;
                }
            }
        }
        if (!hayReservas) {
            System.out.println("No hay reservas registradas.");
        }
    }

    public void consultarAulasPorPiso(int piso) {
        for (Aula aula : aulas) {
            if (aula.getNumero() / 100 == piso) {
                System.out.println("Aula: " + aula.getNumero() + ", Capacidad: " + aula.getCapacidad());
            }
        }
    }

    public void consultarAulasPorCodigoEntidad(String codigo) {
        for (Aula aula : aulas) {
            for (Reserva reserva : aula.getReservas()) {
                if (reserva.getCodigoEntidad().equals(codigo)) {
                    System.out.println("Aula: " + aula.getNumero() + ", Capacidad: " + aula.getCapacidad());
                    break;
                }
            }
        }
    }

    // Método para verificar si un aula está disponible en un horario específico
    private boolean esAulaDisponible(Aula aula, Date fecha, String horario) {
        for (Reserva reserva : aula.getReservas()) {
            if (reserva.getFecha().equals(fecha) && reserva.getRangoHorario().equals(horario)) {
                return false;
            }
        }
        return true;
    }

    // Método para registrar reserva de asignatura
    public void registrarReservaAsignatura(String codigoAsignatura) {
        Asignatura asignatura = asignaturas.stream().filter(a -> a.getCodigo().equals(codigoAsignatura)).findFirst().orElse(null);
        if (asignatura == null) {
            System.out.println("Asignatura no encontrada");
            return;
        }
        boolean reservaRegistrada = false;
        for (Aula aula : aulas) {
            if (aula.getCapacidad() >= asignatura.getAlumnosInscriptos()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(asignatura.getFechaInicio());
                while (!cal.getTime().after(asignatura.getFechaFin())) {
                    if (cal.get(Calendar.DAY_OF_WEEK) == getDiaSemana(asignatura.getDiaSemana())) {
                        String horario = asignatura.getHorarioInicio() + "-" + asignatura.getHorarioFin();
                        if (esAulaDisponible(aula, cal.getTime(), horario)) {
                            Reserva reserva = new Reserva(cal.getTime(), horario, "Asignatura", codigoAsignatura);
                            aula.addReserva(reserva);
                            System.out.println("Reserva registrada: " + reserva.getCodigo() + " para la asignatura " + codigoAsignatura + " en el aula " + aula.getNumero() + " el " + reserva.getFecha());
                            reservaRegistrada = true;
                        } else {
                            System.out.println("No se pudo registrar la reserva para la asignatura " + codigoAsignatura + " en el aula " + aula.getNumero() + " el " + cal.getTime() + " debido a conflicto de horario.");
                        }
                    }
                    cal.add(Calendar.DATE, 7);
                }
                if (reservaRegistrada) return;
            }
        }
        if (!reservaRegistrada) {
            System.out.println("No se encontró aula disponible para la asignatura");
        }
    }

    // Método para registrar reserva de curso
    public void registrarReservaCurso(String codigoCurso, Date fechaInicio, String rangoHorario) {
        CursoExtension curso = cursos.stream().filter(c -> c.getCodigo().equals(codigoCurso)).findFirst().orElse(null);
        if (curso == null) {
            System.out.println("Curso no encontrado");
            return;
        }
        boolean reservaRegistrada = false;
        for (Aula aula : aulas) {
            if (aula.getCapacidad() >= curso.getAlumnosInscriptos()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaInicio);
                for (int i = 0; i < curso.getCantidadClases(); i++) {
                    if (esAulaDisponible(aula, cal.getTime(), rangoHorario)) {
                        Reserva reserva = new Reserva(cal.getTime(), rangoHorario, "Curso", codigoCurso);
                        aula.addReserva(reserva);
                        System.out.println("Reserva registrada: " + reserva.getCodigo() + " para el curso " + codigoCurso + " en el aula " + aula.getNumero() + " el " + reserva.getFecha());
                        reservaRegistrada = true;
                    } else {
                        System.out.println("No se pudo registrar la reserva para el curso " + codigoCurso + " en el aula " + aula.getNumero() + " el " + cal.getTime() + " debido a conflicto de horario.");
                    }
                    cal.add(Calendar.DATE, 7);  // Añade una semana para la próxima clase
                }
                if (reservaRegistrada) return;
            }
        }
        if (!reservaRegistrada) {
            System.out.println("No se encontró aula disponible para el curso");
        }
    }

    // Método para registrar reserva de evento
    public void registrarReservaEvento(String codigoEvento, String horario, boolean esExterno, String organizacion, double costoAlquiler) {
        Evento evento = eventos.stream().filter(e -> e.getCodigo().equals(codigoEvento)).findFirst().orElse(null);
        if (evento == null) {
            System.out.println("Evento no encontrado");
            return;
        }
        boolean reservaRegistrada = false;
        for (Aula aula : aulas) {
            if (aula.getCapacidad() >= evento.getMaxParticipantes()) {
                if (esAulaDisponible(aula, new Date(), horario)) {
                    Reserva reserva = new Reserva(new Date(), horario, "Evento", codigoEvento);
                    aula.addReserva(reserva);
                    System.out.println("Reserva registrada: " + reserva.getCodigo() + " para el evento " + codigoEvento + " en el aula " + aula.getNumero());
                    if (esExterno) {
                        System.out.println("Organización: " + organizacion + ", Costo de alquiler: " + costoAlquiler);
                    }
                    reservaRegistrada = true;
                    break; // Sale del bucle si se ha registrado la reserva
                } else {
                    System.out.println("No se pudo registrar la reserva para el evento " + codigoEvento + " en el aula " + aula.getNumero() + " debido a conflicto de horario.");
                }
            }
        }
        if (!reservaRegistrada) {
            System.out.println("No se encontró aula disponible para el evento");
        }
    }

    // Método auxiliar para convertir día de la semana a constante de Calendar
    private int getDiaSemana(String dia) {
        switch (dia.toLowerCase()) {
            case "lunes":
                return Calendar.MONDAY;
            case "martes":
                return Calendar.TUESDAY;
            case "miércoles":
            case "miercoles":
                return Calendar.WEDNESDAY;
            case "jueves":
                return Calendar.THURSDAY;
            case "viernes":
                return Calendar.FRIDAY;
            case "sábado":
            case "sabado":
                return Calendar.SATURDAY;
            default:
                return -1;
        }
    }

    public void cancelarReserva(int numeroAula, int codigoReserva) {
        Aula aula = aulas.stream().filter(a -> a.getNumero() == numeroAula).findFirst().orElse(null);
        if (aula == null) {
            System.out.println("Aula no encontrada");
            return;
        }
        Reserva reserva = aula.getReservas().stream().filter(r -> r.getCodigo() == codigoReserva).findFirst().orElse(null);
        if (reserva == null) {
            System.out.println("Reserva no encontrada");
            return;
        }
        aula.getReservas().remove(reserva);
        System.out.println("Reserva cancelada");
    }

    public void generarReporteRecaudacion() {
        double totalRecaudado = 0;
        Map<Integer, Double> recaudacionPorPiso = new HashMap<>();
        for (Aula aula : aulas) {
            double recaudadoAula = 0;
            for (Reserva reserva : aula.getReservas()) {
                if (reserva.getTipo().equals("Evento") && ((Evento) buscarEntidadPorCodigo(reserva.getCodigoEntidad(), "Evento")).isEsExterno()) {
                    recaudadoAula += ((Evento) buscarEntidadPorCodigo(reserva.getCodigoEntidad(), "Evento")).getCostoAlquiler();
                } else if (reserva.getTipo().equals("Curso")) {
                    recaudadoAula += ((CursoExtension) buscarEntidadPorCodigo(reserva.getCodigoEntidad(), "Curso")).getCostoPorAlumno();
                }
            }
            totalRecaudado += recaudadoAula;
            int piso = aula.getNumero() / 100;
            recaudacionPorPiso.put(piso, recaudacionPorPiso.getOrDefault(piso, 0.0) + recaudadoAula);
            System.out.println("Aula " + aula.getNumero() + " recaudó: " + recaudadoAula);
        }
        for (Map.Entry<Integer, Double> entry : recaudacionPorPiso.entrySet()) {
            System.out.println("Piso " + entry.getKey() + " recaudó: " + entry.getValue());
        }
        System.out.println("Total recaudado por la institución: " + totalRecaudado);
    }

    private Object buscarEntidadPorCodigo(String codigo, String tipo) {
        switch (tipo) {
            case "Asignatura":
                return asignaturas.stream().filter(a -> a.getCodigo().equals(codigo)).findFirst().orElse(null);
            case "Curso":
                return cursos.stream().filter(c -> c.getCodigo().equals(codigo)).findFirst().orElse(null);
            case "Evento":
                return eventos.stream().filter(e -> e.getCodigo().equals(codigo)).findFirst().orElse(null);
            default:
                return null;
        }
    }

    public void generarReporteReservas() {
        aulas.sort((a1, a2) -> a2.getReservas().size() - a1.getReservas().size());
        int totalReservas = 0;
        for (Aula aula : aulas) {
            System.out.println("Aula " + aula.getNumero() + " tiene " + aula.getReservas().size() + " reservas");
            totalReservas += aula.getReservas().size();
        }
        System.out.println("Promedio de reservas por aula: " + (totalReservas / (double) aulas.size()));
    }

    public void guardarEstado(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this.aulas);
            oos.writeObject(this.asignaturas);
            oos.writeObject(this.cursos);
            oos.writeObject(this.eventos);
            System.out.println("Estado del sistema guardado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar el estado del sistema: " + e.getMessage());
        }
    }
}