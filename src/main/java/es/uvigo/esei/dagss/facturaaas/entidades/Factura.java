package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author alex
 */
@Entity
@Table(name="FACTURA")
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @Getter @Setter
    private String ejercicio;

    @NotNull
    @ManyToOne
    @Getter @Setter
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Getter @Setter
    private EstadoFactura estado = EstadoFactura.EMITIDA;

    @ManyToOne
    @NotNull
    @Getter @Setter
    private FormaPago formaPago;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Getter @Setter
    private Date fechaEmision;

    @NotNull
    @Getter @Setter
    private String comentarios;

    @OneToMany(mappedBy = "factura", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @Getter @Setter
    private List<LineaFactura> lineas = new ArrayList<>();

    @OneToMany(mappedBy = "factura", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @Getter @Setter
    private List<Pago> pagos = new ArrayList<>();

    public double getImporte() {
        double importe = 0;

        for (LineaFactura l : getLineas()) {
            importe += l.getImporte();
        }

        return importe;
    }

    public void addLineaFactura(LineaFactura lineaFactura) {
        lineas.add(lineaFactura);
        lineaFactura.setFactura(this);
    }

    public void removeLineaFactura(LineaFactura lineaFactura) {
        lineas.remove(lineaFactura);
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", ejercicio=" + ejercicio + ", cliente=" + cliente + ", estado=" + estado + ", formaPago=" + formaPago + ", fechaEmision=" + fechaEmision + ", comentarios=" + comentarios + '}';
    }
}
