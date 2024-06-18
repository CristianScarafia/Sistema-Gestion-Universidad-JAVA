package sistemareservas;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Aula implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private int capacidad;
    private List<Reserva> reservas;

    public Aula(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.reservas = new LinkedList<>();
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

    public boolean agregarReserva(Reserva reserva) {
        return reservas.add(reserva);
    }

    public boolean cancelarReserva(int codigoReserva) {
        return reservas.removeIf(r -> r.getCodigo() == codigoReserva);
    }

    @Override
    public String toString() {
        return "Aula [numero=" + numero + ", capacidad=" + capacidad + "]";
    }
}
