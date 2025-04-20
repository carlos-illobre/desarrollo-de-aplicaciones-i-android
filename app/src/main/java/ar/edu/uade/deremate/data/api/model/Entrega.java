package ar.edu.uade.deremate.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Entrega {
    @SerializedName("id")
    private String id;

    @SerializedName("repartidorEmail")
    private String repartidorEmail;

    @SerializedName("cliente")
    private String cliente;

    @SerializedName("fechaEntrega")
    private String fechaEntrega;

    @SerializedName("estado")
    private String estado;

    @SerializedName("direccion")
    private String direccion;

    // Getters y setters

    public String getId() {
        return id;
    }

    public String getRepartidorEmail() {
        return repartidorEmail;
    }

    public String getCliente() {
        return cliente;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public String getDireccion() {
        return direccion;
    }
}