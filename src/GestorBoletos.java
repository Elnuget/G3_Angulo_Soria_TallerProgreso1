import java.util.HashMap;
import java.util.Stack;

public class GestorBoletos {
    private Stack<Boleto> compras;
    private HashMap<String, Integer> boletosVendidosPorRuta;
    private HashMap<String, Integer> boletosDisponiblesPorRuta;
    private HashMap<String, Integer> boletosVendidosPorCedula;

    private static final int CAPACIDAD_MAXIMA = 20;
    private static final int MAX_BOLETOS_POR_COMPRA = 5;
    private static final int MAX_BOLETOS_POR_CEDULA = 5;

    // Constantes de rutas
    public static final String RUTA_QUITO_GUAYAQUIL = "QUITO - GUAYAQUIL";
    public static final String RUTA_QUITO_CUENCA = "QUITO - CUENCA";
    public static final String RUTA_QUITO_LOJA = "QUITO - LOJA";

    // Constantes de precios
    public static final double PRECIO_QUITO_GUAYAQUIL = 10.50;
    public static final double PRECIO_QUITO_CUENCA = 12.75;
    public static final double PRECIO_QUITO_LOJA = 15.00;

    public GestorBoletos() {
        compras = new Stack<>();
        boletosVendidosPorRuta = new HashMap<>();
        boletosDisponiblesPorRuta = new HashMap<>();
        boletosVendidosPorCedula = new HashMap<>();

        // Inicializar rutas
        inicializarRutas();
    }

    private void inicializarRutas() {
        boletosVendidosPorRuta.put(RUTA_QUITO_GUAYAQUIL, 0);
        boletosVendidosPorRuta.put(RUTA_QUITO_CUENCA, 0);
        boletosVendidosPorRuta.put(RUTA_QUITO_LOJA, 0);

        boletosDisponiblesPorRuta.put(RUTA_QUITO_GUAYAQUIL, CAPACIDAD_MAXIMA);
        boletosDisponiblesPorRuta.put(RUTA_QUITO_CUENCA, CAPACIDAD_MAXIMA);
        boletosDisponiblesPorRuta.put(RUTA_QUITO_LOJA, CAPACIDAD_MAXIMA);
    }

    public boolean estaVacia() {
        return compras.isEmpty();
    }

    public void agregarCompra(Boleto boleto) {
        compras.push(boleto);

        // Actualizar contadores
        String ruta = boleto.getRuta();
        int cantidad = boleto.getCantidadBoletos();

        boletosVendidosPorRuta.put(ruta, boletosVendidosPorRuta.get(ruta) + cantidad);
        boletosDisponiblesPorRuta.put(ruta, boletosDisponiblesPorRuta.get(ruta) - cantidad);

        // Actualizar contador por cédula
        String cedula = boleto.getCedula();
        boletosVendidosPorCedula.put(cedula, boletosVendidosPorCedula.getOrDefault(cedula, 0) + cantidad);
    }

    public Boleto eliminarUltimaCompra() throws IllegalStateException {
        if (estaVacia()) {
            throw new IllegalStateException("No hay compras para eliminar");
        }

        Boleto boleto = compras.pop();

        // Revertir contadores
        String ruta = boleto.getRuta();
        int cantidad = boleto.getCantidadBoletos();

        boletosVendidosPorRuta.put(ruta, boletosVendidosPorRuta.get(ruta) - cantidad);
        boletosDisponiblesPorRuta.put(ruta, boletosDisponiblesPorRuta.get(ruta) + cantidad);

        String cedula = boleto.getCedula();
        boletosVendidosPorCedula.put(cedula, boletosVendidosPorCedula.get(cedula) - cantidad);

        return boleto;
    }

    // Validaciones
    public boolean validarCantidadBoletos(int cantidad) {
        return cantidad > 0 && cantidad <= MAX_BOLETOS_POR_COMPRA;
    }

    public boolean validarDisponibilidad(String ruta, int cantidad) {
        return boletosDisponiblesPorRuta.get(ruta) >= cantidad;
    }

    public boolean validarLimitePorCedula(String cedula, int cantidadNueva) {
        int boletosActuales = boletosVendidosPorCedula.getOrDefault(cedula, 0);
        return (boletosActuales + cantidadNueva) <= MAX_BOLETOS_POR_CEDULA;
    }

    public String validarCompra(String cedula, String ruta, int cantidad) {
        if (!validarCantidadBoletos(cantidad)) {
            return "La cantidad debe estar entre 1 y " + MAX_BOLETOS_POR_COMPRA + " boletos.";
        }

        if (!validarDisponibilidad(ruta, cantidad)) {
            return "No hay suficientes boletos disponibles para esta ruta.\nDisponibles: " +
                    boletosDisponiblesPorRuta.get(ruta);
        }

        if (!validarLimitePorCedula(cedula, cantidad)) {
            int boletosActuales = boletosVendidosPorCedula.getOrDefault(cedula, 0);
            int disponibles = MAX_BOLETOS_POR_CEDULA - boletosActuales;
            return "Límite excedido. Esta cédula ya tiene " + boletosActuales +
                    " boletos.\nPuede comprar máximo " + disponibles + " más.";
        }

        return null; // Sin errores
    }

    // Getters para información
    public int getBoletosVendidos(String ruta) {
        return boletosVendidosPorRuta.get(ruta);
    }

    public int getBoletosDisponibles(String ruta) {
        return boletosDisponiblesPorRuta.get(ruta);
    }

    public double getTotalRecaudado() {
        double total = 0;
        for (Boleto boleto : compras) {
            total += boleto.getPrecioTotal();
        }
        return total;
    }

    public double getPrecioPorRuta(String ruta) {
        switch (ruta) {
            case RUTA_QUITO_GUAYAQUIL:
                return PRECIO_QUITO_GUAYAQUIL;
            case RUTA_QUITO_CUENCA:
                return PRECIO_QUITO_CUENCA;
            case RUTA_QUITO_LOJA:
                return PRECIO_QUITO_LOJA;
            default:
                return 0;
        }
    }

    public int tamano() {
        return compras.size();
    }

    public void limpiar() {
        compras.clear();
        boletosVendidosPorCedula.clear();
        inicializarRutas();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = compras.size() - 1; i >= 0; i--) {
            sb.append(compras.get(i).toString());
        }
        return "=== HISTORIAL DE COMPRAS ===\n" + sb.toString();
    }
}