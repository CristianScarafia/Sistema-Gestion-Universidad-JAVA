package sistemareservas;

public class Reserva {
    private static int contador = 0;
    private int codigo;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String tipo; // Asignatura, CursoExtension, Evento
    private String idReferencia; // Código de asignatura, curso o evento

    public Reserva(String fecha, String horaInicio, String horaFin, String tipo, String idReferencia) {
        this.codigo = ++contador;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipo = tipo;
        this.idReferencia = idReferencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIdReferencia() {
        return idReferencia;
    }

    @Override
    public String toString() {
        return "Reserva [codigo=" + codigo + ", fecha=" + fecha + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin
                + ", tipo=" + tipo + ", idReferencia=" + idReferencia + "]";
    }
}
