package sistemareservas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Reporte {

    private SistemaReservas sistemaReservas;

    public Reporte(SistemaReservas sistemaReservas) {
        this.sistemaReservas = sistemaReservas;
    }

    public void generarReporteMontoRecaudado() {
        double totalRecaudado = 0;
        Map<Integer, Double> recaudacionPorPiso = new TreeMap<>();

        for (Aula aula : sistemaReservas.getAulas().values()) {
            double recaudadoAula = 0;
            for (Reserva reserva : aula.getReservas()) {
                if (reserva.getTipo().equals("Evento")) {
                    Evento evento = sistemaReservas.buscarEventoPorCodigo(reserva.getIdReferencia());
                    if (evento != null && evento.esExterno()) {
                        recaudadoAula += evento.getCostoAlquiler();
                    }
                } else if (reserva.getTipo().equals("CursoExtension")) {
                    CursoExtension curso = sistemaReservas.buscarCursoPorCodigo(reserva.getIdReferencia());
                    if (curso != null) {
                        recaudadoAula += curso.getCostoPorAlumno() * curso.getAlumnosInscriptos();
                    }
                }
            }
            totalRecaudado += recaudadoAula;
            int piso = aula.getNumero() / 100;
            recaudacionPorPiso.put(piso, recaudacionPorPiso.getOrDefault(piso, 0.0) + recaudadoAula);
        }

        System.out.println("Monto recaudado total: " + totalRecaudado);
        for (Map.Entry<Integer, Double> entry : recaudacionPorPiso.entrySet()) {
            System.out.println("Monto recaudado en piso " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public void generarReporteReservasPorAula() {
        sistemaReservas.getAulas().values().stream()
            .sorted((a1, a2) -> Integer.compare(a2.getReservas().size(), a1.getReservas().size()))
            .forEach(aula -> {
                System.out.println(aula + " - Cantidad de reservas: " + aula.getReservas().size());
                aula.getReservas().forEach(reserva -> {
                    System.out.println("  " + reserva);
                });
            });
    }

    public void generarReporteMontoRecaudadoEnArchivo(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            double totalRecaudado = 0;
            Map<Integer, Double> recaudacionPorPiso = new TreeMap<>();

            for (Aula aula : sistemaReservas.getAulas().values()) {
                double recaudadoAula = 0;
                for (Reserva reserva : aula.getReservas()) {
                    if (reserva.getTipo().equals("Evento")) {
                        Evento evento = sistemaReservas.buscarEventoPorCodigo(reserva.getIdReferencia());
                        if (evento != null && evento.esExterno()) {
                            recaudadoAula += evento.getCostoAlquiler();
                        }
                    } else if (reserva.getTipo().equals("CursoExtension")) {
                        CursoExtension curso = sistemaReservas.buscarCursoPorCodigo(reserva.getIdReferencia());
                        if (curso != null) {
                            recaudadoAula += curso.getCostoPorAlumno() * curso.getAlumnosInscriptos();
                        }
                    }
                }
                totalRecaudado += recaudadoAula;
                int piso = aula.getNumero() / 100;
                recaudacionPorPiso.put(piso, recaudacionPorPiso.getOrDefault(piso, 0.0) + recaudadoAula);
            }

            writer.write("Monto recaudado total: " + totalRecaudado + "\n");
            for (Map.Entry<Integer, Double> entry : recaudacionPorPiso.entrySet()) {
                writer.write("Monto recaudado en piso " + entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generarReporteReservasPorAulaEnArchivo(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            sistemaReservas.getAulas().values().stream()
                .sorted((a1, a2) -> Integer.compare(a2.getReservas().size(), a1.getReservas().size()))
                .forEach(aula -> {
                    try {
                        writer.write(aula + " - Cantidad de reservas: " + aula.getReservas().size() + "\n");
                        for (Reserva reserva : aula.getReservas()) {
                            writer.write("  " + reserva + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
