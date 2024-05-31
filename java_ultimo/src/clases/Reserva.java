import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private static int nextId = 1;
    private int codigo;
    private Date fecha;
    private String rangoHorario;
    private String tipo;
    private String codigoEntidad;


    public Reserva(Date fecha, String rangoHorario, String tipo, String codigoEntidad) {
        this.codigo = nextId++;
        this.fecha = fecha;
        this.rangoHorario = rangoHorario;
        this.tipo = tipo;
        this.codigoEntidad = codigoEntidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getRangoHorario() {
        return rangoHorario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }
}