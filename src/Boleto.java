public class Boleto {
    private String cedula;
    private String nombrePasajero;
    private String ruta;
    private int cantidadBoletos;
    private double precioUnitario;
    private double precioTotal;

    // Constructor
    public Boleto(String cedula, String nombrePasajero, String ruta, int cantidadBoletos, double precioUnitario) {
        this.cedula = cedula;
        this.nombrePasajero = nombrePasajero;
        this.ruta = ruta;
        this.cantidadBoletos = cantidadBoletos;
        this.precioUnitario = precioUnitario;
        this.precioTotal = cantidadBoletos * precioUnitario;
    }

    // Getters
    public String getCedula() {
        return cedula;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public String getRuta() {
        return ruta;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // Setters
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setCantidadBoletos(int cantidadBoletos) {
        this.cantidadBoletos = cantidadBoletos;
        this.precioTotal = cantidadBoletos * precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.precioTotal = cantidadBoletos * precioUnitario;
    }

    @Override
    public String toString() {
        return String.format("Ruta: %s | Boletos: %d | Pasajero: %s | Total: $%.2f\n",
                ruta, cantidadBoletos, nombrePasajero, precioTotal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Boleto boleto = (Boleto) obj;
        return cedula.equals(boleto.cedula) && ruta.equals(boleto.ruta);
    }

    @Override
    public int hashCode() {
        return cedula.hashCode() + ruta.hashCode();
    }
}