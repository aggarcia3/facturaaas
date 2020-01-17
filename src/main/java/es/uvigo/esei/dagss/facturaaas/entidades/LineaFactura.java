package es.uvigo.esei.dagss.facturaaas.entidades;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author alex
 */
@Entity
@Table(name="LINEA_FACTURA")
public class LineaFactura implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @Getter @Setter
    private String concepto;

    @NotNull
    @Getter @Setter
    private Integer cantidad = 1;

    @NotNull
    @Getter @Setter
    private Double precioUnitario;

    @NotNull
    @Getter @Setter
    private Float porcentajeDescuento;

    @ManyToOne
    @NotNull
    @Getter @Setter
    private TipoIVA tipoIva;

    @ManyToOne
    @JoinColumn(name = "FACTURA_ID")
    @NotNull
    @Getter @Setter
    private Factura factura;

    public double getImporte() {
        double precioSinIva = (this.cantidad * this.precioUnitario);
        double precioSinIvaConDescuento = precioSinIva - (precioSinIva * this.porcentajeDescuento);
        return precioSinIvaConDescuento + (precioSinIvaConDescuento * this.tipoIva.getPorcentaje() / 100);
    }
}
