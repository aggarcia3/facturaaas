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
import lombok.Getter;
import lombok.Setter;

/**
 * Representa los datos de la entidad Pago.
 *
 * @author Alejandro González García
 */
@Entity
@Table(name="PAGO")
public class Pago implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name="FACTURA_ID")
    @NotNull
    @Getter @Setter
    private Factura factura;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Getter @Setter
    private EstadoPago estado;

    @NotNull
    @Getter @Setter
    private Double importe;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Getter @Setter
    private Date fechaVencimiento;
}
