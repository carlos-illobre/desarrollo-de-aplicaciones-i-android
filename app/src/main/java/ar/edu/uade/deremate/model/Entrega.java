package ar.edu.uade.deremate.model;

public class Entrega {
    private String cliente;
    private String fechaEntrega;
    private String estado;
    private String direccion;

    public Entrega(String cliente, String fechaEntrega, String estado, String direccion, String codigoConfirmacion) {
        this.cliente = cliente;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.direccion = direccion;
    }

    public String getCliente() { return cliente; }
    public String getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }
    public String getDireccion() { return direccion; }
}
