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
import javax.validation.constraints.NotNull;

/**
 *
 * @author alex
 */
@Entity
@Table(name="PAGO")
public class Pago implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @ManyToOne
    @JoinColumn(name="FACTURA_ID")
    @NotNull
    private Factura factura;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoPago estado;
    @NotNull
    private Double importe;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date fechaVencimiento;

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
