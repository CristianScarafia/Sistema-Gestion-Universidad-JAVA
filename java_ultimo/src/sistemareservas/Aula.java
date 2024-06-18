package sistemareservas;

import java.util.ArrayList;

public class Aula {
    private int numero;
    private int capacidad;
    private ArrayList<Reserva> reservas;

    public Aula(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.reservas = new ArrayList<>();
    }

    public boolean agregarReserva(Reserva reserva) {
        for (Reserva r : reservas) {
            if (r.getFecha().equals(reserva.getFecha()) &&
                ((r.getHoraInicio().compareTo(reserva.getHoraInicio()) <= 0 && r.getHoraFin().compareTo(reserva.getHoraInicio()) > 0) ||
                 (r.getHoraInicio().compareTo(reserva.getHoraFin()) < 0 && r.getHoraFin().compareTo(reserva.getHoraFin()) >= 0))) {
                return false; // Hay un solapamiento de horarios
            }
        }
        reservas.add(reserva);
        return true;
    }

    public boolean cancelarReserva(int codigoReserva) {
        for (Reserva r : reservas) {
            if (r.getCodigo() == codigoReserva) {
                reservas.remove(r);
                return true;
            }
        }
        return false; // Reserva no encontrada
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    @Override
    public String toString() {
        return "Aula [numero=" + numero + ", capacidad=" + capacidad + "]";
    }
}
