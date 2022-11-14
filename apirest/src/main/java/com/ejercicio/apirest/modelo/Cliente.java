package com.ejercicio.apirest.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "cliente")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Cliente extends Persona {

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String contraseña;

    @NotNull
    @Column(nullable = false)
    private Boolean estado;

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "contraseña='" + contraseña + '\'' +
                ", estado=" + estado +
                '}';
    }
}
