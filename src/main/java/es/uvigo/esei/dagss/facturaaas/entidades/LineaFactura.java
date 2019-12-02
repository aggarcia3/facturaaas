/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.entidades;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alex
 */
@Entity
@Table(name="LINEA_FACTURA")
public class LineaFactura implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String concepto;
    private int cantidad;
    private double precioUnitario;
    private float porcentajeDescuento;
    private TipoIVA tipoIva;
    @ManyToOne
    @JoinColumn(name = "FACTURA_ID")
    private Factura factura;

    public LineaFactura(String concepto, int cantidad, double precioUnitario, float porcentajeDescuento, TipoIVA tipoIva, Factura factura) {
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.porcentajeDescuento = porcentajeDescuento;
        this.tipoIva = tipoIva;
        this.factura = factura;
    }
    
     

    public Long getId() {
        return id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(float porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public TipoIVA getTipoIva() {
        return tipoIva;
    }

    public void setTipoIva(TipoIVA tipoIva) {
        this.tipoIva = tipoIva;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    
    
}
