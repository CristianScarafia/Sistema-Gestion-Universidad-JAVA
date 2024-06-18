package sistemareservas;

import java.io.Serializable;

public class CursoExtension implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String descripcion;
    private int alumnosInscriptos;
    private int cantidadClases;
    private double costoPorAlumno;

    public CursoExtension(String codigo, String descripcion, int alumnosInscriptos, int cantidadClases, double costoPorAlumno) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        if (alumnosInscriptos <= 0) {
            throw new IllegalArgumentException("La cantidad de alumnos inscriptos debe ser mayor a 0");
        }
        if (cantidadClases <= 0) {
            throw new IllegalArgumentException("La cantidad de clases debe ser mayor a 0");
        }
        if (costoPorAlumno < 0) {
            throw new IllegalArgumentException("El costo por alumno no puede ser negativo");
        }
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.alumnosInscriptos = alumnosInscriptos;
        this.cantidadClases = cantidadClases;
        this.costoPorAlumno = costoPorAlumno;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getAlumnosInscriptos() {
        return alumnosInscriptos;
    }

    public int getCantidadClases() {
        return cantidadClases;
    }

    public double getCostoPorAlumno() {
        return costoPorAlumno;
    }

    @Override
    public String toString() {
        return "CursoExtension [codigo=" + codigo + ", descripcion=" + descripcion + ", alumnosInscriptos=" + alumnosInscriptos
                + ", cantidadClases=" + cantidadClases + ", costoPorAlumno=" + costoPorAlumno + "]";
    }
}
