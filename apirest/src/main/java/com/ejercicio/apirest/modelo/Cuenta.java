package com.ejercicio.apirest.modelo;

import com.ejercicio.apirest.enumerador.TipoCuenta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "cuenta")
public class Cuenta {

    @Id
    private String numeroCuenta;

    @NotNull
    @Column(nullable = false)
    private TipoCuenta tipoCuenta;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoInicial;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoActual;

    @NotNull
    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne(optional = false)
    private Cliente cliente;

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
