import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Aula implements Serializable {
    private int numero;
    private int capacidad;
    private List<Reserva> reservas;

    public Aula(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.reservas = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
    
    // Método para verificar si un aula está disponible en un horario específico
    public boolean esAulaDisponible(java.util.Date date, String rangoHorario) {
        for (Reserva reserva : reservas) {
            if (reserva.getFecha().equals(date) && reserva.getRangoHorario().equals(rangoHorario)) {
                return false;
            }
        }
        return true;
    }
}
