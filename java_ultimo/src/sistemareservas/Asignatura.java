package sistemareservas;

public class Asignatura {
    private String codigo;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private DiaSemana diaSemana;
    private String horaInicio;
    private String horaFin;
    private int alumnosInscriptos;

    public Asignatura(String codigo, String nombre, String fechaInicio, String fechaFin, DiaSemana diaSemana, String horaInicio, String horaFin, int alumnosInscriptos) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (alumnosInscriptos <= 0) {
            throw new IllegalArgumentException("La cantidad de alumnos inscriptos debe ser mayor a 0");
        }
        if (diaSemana == null) {
            throw new IllegalArgumentException("El día de la semana no puede ser nulo");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.alumnosInscriptos = alumnosInscriptos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public int getAlumnosInscriptos() {
        return alumnosInscriptos;
    }

    @Override
    public String toString() {
        return "Asignatura [codigo=" + codigo + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin="
                + fechaFin + ", diaSemana=" + diaSemana + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin
                + ", alumnosInscriptos=" + alumnosInscriptos + "]";
    }
}
