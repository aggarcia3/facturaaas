package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FormaPagoDAO;
import es.uvigo.esei.dagss.facturaaas.daos.LineaFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.PagoDAO;
import es.uvigo.esei.dagss.facturaaas.daos.TipoIVADAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoPago;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.TipoIVA;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author alex
 */
@Named(value = "facturasController")
@ViewScoped
public class FacturasController implements Serializable {

    @Getter @Setter
    private List<Factura> facturas;
    private boolean esNuevo;
    @Getter @Setter
    private List<Cliente> clientes;
    @Getter @Setter
    private Cliente clienteSeleccionado;
    @Getter @Setter
    private List<FormaPago> formasDePago;
    @Getter @Setter
    private EstadoFactura[] estadosPosibles = EstadoFactura.values();
    @Getter @Setter
    private LineaFactura lineaFacturaActual;
    @Getter @Setter
    private boolean esNuevaLinea;
    @Getter @Setter
    private List<TipoIVA> tiposIva;
    @Getter @Setter
    private Factura facturaActual;

    @Inject
    private FacturaDAO dao;

    @Inject
    private PagoDAO daoPago;

    @Inject
    private LineaFacturaDAO daoLinea;

    @Inject
    private ClienteDAO daoCliente;

    @Inject
    private FormaPagoDAO daoFormaPago;

    @Inject
    private AutenticacionController autenticacionController;

    @Inject
    private TipoIVADAO daoIva;

    @PostConstruct
    public void cargaInicial() {
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.clientes = refrescarClientes();
        this.formasDePago = daoFormaPago.buscarActivas();
        this.esNuevo = false;

        this.tiposIva = daoIva.buscarActivos();

    }

    public void doBuscarFacturasCliente() {
        this.facturas = dao.buscarPorCliente(clienteSeleccionado);
    }

    private List<Factura> refrescarLista() {
        return dao.buscarPorUsuario(autenticacionController.getUsuarioLogueado());
    }

    private List<Cliente> refrescarClientes() {
        return daoCliente.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }

    public void doNuevo() {
        this.esNuevo = true;
        this.facturaActual = new Factura();
    }

    public void doEditar(Factura factura) {
        this.esNuevo = false;
        this.facturaActual = factura;
    }

    public void doNuevaLinea() {
        this.esNuevaLinea = true;
        this.lineaFacturaActual = new LineaFactura();
    }

    public void doEditarLinea(LineaFactura lineaFactura) {
        this.esNuevaLinea = false;
        this.lineaFacturaActual = lineaFactura;
    }

    public void doEliminarLinea(LineaFactura lineaFactura) {
        facturaActual.removeLineaFactura(lineaFactura);
        daoLinea.eliminar(lineaFactura);
    }

    public void doGuardarEditadoLineaFactura() {
        if (esNuevaLinea) {
            facturaActual.addLineaFactura(lineaFacturaActual);
        }

        this.lineaFacturaActual = null;

        this.esNuevaLinea = false;
    }

    public void doCancelarEditadoLineaFactura() {
        this.lineaFacturaActual = null;
        this.esNuevaLinea = false;
    }

    public void doGuardarEditado() {
        if (esNuevo) {
            dao.crear(facturaActual);
        } else {
            daoPago.eliminarPagosPorFactura(facturaActual);

            dao.actualizar(facturaActual);
        }

        int numeroPagos = facturaActual.getFormaPago().getNumeroPagos();
        int periocidad = facturaActual.getFormaPago().getPeriodicidad();
        double importePorPago = facturaActual.getImporte() / numeroPagos;
        Date fechaEmision = facturaActual.getFechaEmision();

        Calendar c = Calendar.getInstance();
        c.setTime(fechaEmision);

        // El primer pago vence en la fecha de emisión de la factura
        Date fechaVencimientoPago = fechaEmision;

        for (int i = 0; i < numeroPagos; i++) {
            Pago p = new Pago();
            p.setEstado(EstadoPago.PENDIENTE);
            p.setFechaVencimiento(fechaVencimientoPago);
            p.setFactura(facturaActual);
            p.setImporte(importePorPago);
            daoPago.crear(p);

            // El siguiente pago vencerá periodicidad días después
            c.add(Calendar.DATE, periocidad);
            fechaVencimientoPago = c.getTime();
        }

        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
    }

    public void doEliminar(Factura f) {
        // Con JPA no es posible eliminar en cascada todas las entidades relacionadas,
        // pues ello invalida a veces restricciones de integridad (depende del orden
        // de las consultas SQL generadas).
        // Es necesario eliminar primero las entidades del lado N manualmente
        daoPago.eliminarPagosPorFactura(f);
        daoLinea.eliminarPorFactura(f);

        dao.eliminar(f);
        this.facturas = refrescarLista();
    }

    public void doCancelarEditado() {
        this.facturaActual = null;
        this.esNuevo = false;
    }
}
