package sistemareservas;

public class Evento {
    private String codigo;
    private String descripcion;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private int maxParticipantes;
    private boolean esExterno;
    private String organizacion;
    private double costoAlquiler;

    public Evento(String codigo, String descripcion, String fecha, String horaInicio, String horaFin, int maxParticipantes, boolean esExterno, String organizacion, double costoAlquiler) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.maxParticipantes = maxParticipantes;
        this.esExterno = esExterno;
        this.organizacion = organizacion;
        this.costoAlquiler = costoAlquiler;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
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

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public boolean esExterno() {
        return esExterno;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public double getCostoAlquiler() {
        return costoAlquiler;
    }

    @Override
    public String toString() {
        return "Evento [codigo=" + codigo + ", descripcion=" + descripcion + ", fecha=" + fecha + ", horaInicio=" + horaInicio
                + ", horaFin=" + horaFin + ", maxParticipantes=" + maxParticipantes + ", esExterno=" + esExterno
                + ", organizacion=" + organizacion + ", costoAlquiler=" + costoAlquiler + "]";
    }
}
