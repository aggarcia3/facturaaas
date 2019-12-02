/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.entidades;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author alex
 */
public class Pago implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="FACTURA_ID")
    private Factura factura;
    
    @Enumerated(EnumType.STRING)
    private EstadoPago estado;
    private double importe;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;

    public Pago(Factura factura, EstadoPago estado, double importe, Date fechaVencimiento) {
        this.factura = factura;
        this.estado = estado;
        this.importe = importe;
        this.fechaVencimiento = fechaVencimiento;
    }

    public long getId() {
        return id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    
    
    
    
}
