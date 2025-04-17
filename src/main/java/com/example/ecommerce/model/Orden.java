package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;

    private double total;

    @ManyToOne
    private Usuario usuario;

    @OneToOne(mappedBy = "orden")
    private DetalleOrden detalleOrdenes;

    public Orden() {
    }

    public Orden(double total, Date fechaRecibida, Date fechaCreacion, String numero, Integer id) {
        this.total = total;
        this.fechaRecibida = fechaRecibida;
        this.fechaCreacion = fechaCreacion;
        this.numero = numero;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Orden{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaRecibida=" + fechaRecibida +
                ", total=" + total +
                '}';
    }

    public DetalleOrden getDetalleOrdenes() {
        return detalleOrdenes;
    }

    public void setDetalleOrdenes(DetalleOrden detalleOrdenes) {
        this.detalleOrdenes = detalleOrdenes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
