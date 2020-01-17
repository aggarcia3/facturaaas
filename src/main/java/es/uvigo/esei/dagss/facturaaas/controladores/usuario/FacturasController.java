package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FormaPagoDAO;
import es.uvigo.esei.dagss.facturaaas.daos.LineaFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.TipoIVADAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.TipoIVA;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<LineaFactura> lineasDeFacturaActual;
    private List<LineaFactura> lineasDeFacturaCrear;
    private List<LineaFactura> lineasDeFacturaActualizar;
    private List<LineaFactura> lineasDeFacturaEliminar;
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
        this.lineasDeFacturaActual = null;
        this.clientes = refrescarClientes();
        this.formasDePago = daoFormaPago.buscarActivas();
        this.esNuevo = false;

        this.tiposIva = daoIva.buscarActivos();
        this.lineasDeFacturaCrear = new ArrayList<>();
        this.lineasDeFacturaActualizar = new ArrayList<>();
        this.lineasDeFacturaEliminar = new ArrayList<>();

    }

    public void doBuscarFacturasCliente() {
        this.facturas = dao.buscarPorCliente(clienteSeleccionado);
    }

    private List<Factura> refrescarLista() {

        return dao.buscarPorUsuario(this.autenticacionController.getUsuarioLogueado());
    }

    private List<Cliente> refrescarClientes() {
        return daoCliente.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }

    public void doNuevo() {
        this.esNuevo = true;
        this.facturaActual = new Factura();
        this.lineasDeFacturaActual = new ArrayList<>();

    }

    public void doEditar(Factura factura) {
        this.esNuevo = false;
        this.facturaActual = factura;

        if (factura.getLineas() == null) {
            this.lineasDeFacturaActual = new ArrayList<>();
        } else {
            this.lineasDeFacturaActual = factura.getLineas();
        }

    }

    public void doNuevaLinea() {
        this.esNuevaLinea = true;
        this.lineaFacturaActual = new LineaFactura();
        this.lineaFacturaActual.setPrecioUnitario(0.0);
        this.lineaFacturaActual.setPorcentajeDescuento(0.0f);

    }

    public void doEditarLinea(LineaFactura lineaFactura) {
        this.esNuevaLinea = false;
        this.lineaFacturaActual = lineaFactura;
    }

    public void doEliminarLinea(LineaFactura lineaFactura) {
        this.lineasDeFacturaActual.remove(lineaFactura);
        if (this.lineasDeFacturaCrear.contains(lineaFactura)) {
            this.lineasDeFacturaCrear.remove(lineaFactura);
        } else if (this.lineasDeFacturaActualizar.contains(lineaFactura)) {
            this.lineasDeFacturaActualizar.remove(lineaFactura);
        } else {
            this.lineasDeFacturaEliminar.add(lineaFactura);
        }
    }

    public void doGuardarEditadoLineaFactura() {
        if (this.esNuevaLinea) {
            this.lineasDeFacturaCrear.add(lineaFacturaActual);
            this.lineasDeFacturaActual.add(lineaFacturaActual);
        } else {
            this.lineasDeFacturaActualizar.add(lineaFacturaActual);
        }

        this.lineaFacturaActual = null;

        this.esNuevaLinea = false;
    }

    public void doCancelarEditadoLineaFactura() {
        this.lineaFacturaActual = null;
        this.esNuevaLinea = false;
    }

    public void doGuardarEditado() {
        if (this.esNuevo) {
            dao.crear(facturaActual);

        } else {
            dao.actualizar(facturaActual);
            for (LineaFactura l : this.lineasDeFacturaActualizar) {
                daoLinea.actualizar(l);
            }
            for (LineaFactura l : this.lineasDeFacturaEliminar) {
                daoLinea.eliminar(l);
            }
        }
        for (LineaFactura l : this.lineasDeFacturaCrear) {
            l.setFactura(facturaActual);
            daoLinea.crear(l);
        }
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
        this.lineasDeFacturaActual.clear();
        this.lineasDeFacturaActualizar.clear();
        this.lineasDeFacturaCrear.clear();
        this.lineasDeFacturaEliminar.clear();
    }

    public void doEliminar(Factura f) {
        dao.eliminar(f);
        this.facturas = this.refrescarLista();
    }

    public void doCancelarEditado() {
        this.facturaActual = null;
        this.esNuevo = false;
    }
}
