package sistemareservas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SistemaReservas {
    private Map<Integer, Aula> aulas;
    private List<Asignatura> asignaturas;
    private List<CursoExtension> cursos;
    private List<Evento> eventos;

    public SistemaReservas() {
        this.aulas = new TreeMap<>();
        this.asignaturas = new LinkedList<>();
        this.cursos = new LinkedList<>();
        this.eventos = new LinkedList<>();
    }

    public Map<Integer, Aula> getAulas() {
        return aulas;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public List<CursoExtension> getCursos() {
        return cursos;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void cargarAulasDesdeArchivo(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    int numero = Integer.parseInt(data[0]);
                    int capacidad = Integer.parseInt(data[1]);
                    Aula aula = new Aula(numero, capacidad);
                    aulas.put(numero, aula);
                } else {
                    System.out.println("Formato incorrecto en línea: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarAsignaturasDesdeArchivo(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    try {
                        String codigo = data[0];
                        String nombre = data[1];
                        String fechaInicio = data[2];
                        String fechaFin = data[3];
                        DiaSemana diaSemana = DiaSemana.valueOf(data[4].toUpperCase());
                        String horaInicio = data[5];
                        String horaFin = data[6];
                        int alumnosInscriptos = Integer.parseInt(data[7]);
                        Asignatura asignatura = new Asignatura(codigo, nombre, fechaInicio, fechaFin, diaSemana, horaInicio, horaFin, alumnosInscriptos);
                        asignaturas.add(asignatura);
                        registrarReservaAsignatura(asignatura);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error en datos de asignatura: " + e.getMessage());
                    }
                } else {
                    System.out.println("Formato incorrecto en línea: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarCursosDesdeArchivo(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    try {
                        String codigo = data[0];
                        String descripcion = data[1];
                        int alumnosInscriptos = Integer.parseInt(data[2]);
                        int cantidadClases = Integer.parseInt(data[3]);
                        double costoPorAlumno = Double.parseDouble(data[4]);
                        String fechaInicio = data[5];
                        String horaInicio = data[6];
                        String horaFin = data[7];
                        CursoExtension curso = new CursoExtension(codigo, descripcion, alumnosInscriptos, cantidadClases, costoPorAlumno);
                        cursos.add(curso);
                        registrarReservaCurso(curso, fechaInicio, horaInicio, horaFin);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error en datos de curso: " + e.getMessage());
                    }
                } else {
                    System.out.println("Formato incorrecto en línea: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEventosDesdeArchivo(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 9) {
                    try {
                        String codigo = data[0];
                        String descripcion = data[1];
                        String fecha = data[2];
                        String horaInicio = data[3];
                        String horaFin = data[4];
                        int maxParticipantes = Integer.parseInt(data[5]);
                        boolean esExterno = Boolean.parseBoolean(data[6]);
                        String organizacion = esExterno ? data[7] : "";
                        double costoAlquiler = esExterno ? Double.parseDouble(data[8]) : 0.0;
                        Evento evento = new Evento(codigo, descripcion, fecha, horaInicio, horaFin, maxParticipantes, esExterno, organizacion, costoAlquiler);
                        eventos.add(evento);
                        registrarReservaEvento(evento);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error en datos de evento: " + e.getMessage());
                    }
                } else {
                    System.out.println("Formato incorrecto en línea: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registrarReservaAsignatura(Asignatura asignatura) {
        LocalDate fechaInicio = LocalDate.parse(asignatura.getFechaInicio(), DateTimeFormatter.ISO_DATE);
        LocalDate fechaFin = LocalDate.parse(asignatura.getFechaFin(), DateTimeFormatter.ISO_DATE);

        for (Aula aula : aulas.values()) {
            if (aula.getCapacidad() >= asignatura.getAlumnosInscriptos()) {
                LocalDate fecha = fechaInicio;
                while (!fecha.isAfter(fechaFin)) {
                    if (fecha.getDayOfWeek().toString().equalsIgnoreCase(asignatura.getDiaSemana().toString())) {
                        Reserva reserva = new Reserva(fecha.toString(), asignatura.getHoraInicio(), asignatura.getHoraFin(), "Asignatura", asignatura.getCodigo());
                        if (!aula.agregarReserva(reserva)) {
                            return false;
                        }
                    }
                    fecha = fecha.plusDays(1);
                }
                return true;
            }
        }
        return false;
    }

    public boolean registrarReservaCurso(CursoExtension curso, String fechaInicioStr, String horaInicio, String horaFin) {
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, DateTimeFormatter.ISO_DATE);

        for (Aula aula : aulas.values()) {
            if (aula.getCapacidad() >= curso.getAlumnosInscriptos()) {
                LocalDate fecha = fechaInicio;
                for (int i = 0; i < curso.getCantidadClases(); i++) {
                    Reserva reserva = new Reserva(fecha.toString(), horaInicio, horaFin, "CursoExtension", curso.getCodigo());
                    if (!aula.agregarReserva(reserva)) {
                        return false;
                    }
                    fecha = fecha.plusWeeks(1);
                }
                return true;
            }
        }
        return false;
    }

    public boolean registrarReservaEvento(Evento evento) {
        for (Aula aula : aulas.values()) {
            if (aula.getCapacidad() >= evento.getMaxParticipantes()) {
                Reserva reserva = new Reserva(evento.getFecha(), evento.getHoraInicio(), evento.getHoraFin(), "Evento", evento.getCodigo());
                if (aula.agregarReserva(reserva)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cancelarReserva(int numeroAula, int codigoReserva) {
        Aula aula = aulas.get(numeroAula);
        if (aula != null) {
            return aula.cancelarReserva(codigoReserva);
        }
        return false;
    }

    public Asignatura buscarAsignaturaPorCodigo(String codigo) {
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getCodigo().equals(codigo)) {
                return asignatura;
            }
        }
        return null;
    }

    public CursoExtension buscarCursoPorCodigo(String codigo) {
        for (CursoExtension curso : cursos) {
            if (curso.getCodigo().equals(codigo)) {
                return curso;
            }
        }
        return null;
    }

    public Evento buscarEventoPorCodigo(String codigo) {
        for (Evento evento : eventos) {
            if (evento.getCodigo().equals(codigo)) {
                return evento;
            }
        }
        return null;
    }
}
