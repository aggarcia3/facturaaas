/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FormaPagoDAO;
import es.uvigo.esei.dagss.facturaaas.daos.LineaFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.TipoIVADAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoPago;
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

/**
 *
 * @author alex
 */

@Named(value = "facturasController")
@ViewScoped
public class FacturasController implements Serializable{
    
    private List<Factura> facturas;
    private boolean esNuevo;
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private List<FormaPago> formasDePago;
    private EstadoFactura[] estadosPosibles = EstadoFactura.values();
    private List<LineaFactura> lineasDeFacturaActual;
    private List<LineaFactura> lineasDeFacturaCrear;
    private List<LineaFactura> lineasDeFacturaActualizar;
    private List<LineaFactura> lineasDeFacturaEliminar;
    private LineaFactura lineaFacturaActual;
    private boolean esNuevaLinea;
    private List<TipoIVA> tiposIva;

    public List<TipoIVA> getTiposIva() {
        return tiposIva;
    }

    public void setTiposIva(List<TipoIVA> tiposIva) {
        this.tiposIva = tiposIva;
    }

    public boolean isEsNuevaLinea() {
        return esNuevaLinea;
    }

    public void setEsNuevaLinea(boolean esNuevaLinea) {
        this.esNuevaLinea = esNuevaLinea;
    }

    public LineaFactura getLineaFacturaActual() {
        return lineaFacturaActual;
    }

    public void setLineaFacturaActual(LineaFactura lineaFacturaActual) {
        this.lineaFacturaActual = lineaFacturaActual;
    }

    public List<LineaFactura> getLineasDeFacturaActual() {
        return lineasDeFacturaActual;
    }

    public void setLineasDeFacturaActual(List<LineaFactura> lineasDeFacturaActual) {
        this.lineasDeFacturaActual = lineasDeFacturaActual;
    }

    public EstadoFactura[] getEstadosPosibles() {
        return estadosPosibles;
    }

    public void setEstadosPosibles(EstadoFactura[] estadosPosibles) {
        this.estadosPosibles = estadosPosibles;
    }

    

    public List<FormaPago> getFormasDePago() {
        return formasDePago;
    }

    public void setFormasDePago(List<FormaPago> formasDePago) {
        this.formasDePago = formasDePago;
    }

    
    

    
    
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
    
    private Factura facturaActual;

    public Factura getFacturaActual() {
        return facturaActual;
    }

    public void setFacturaActual(Factura facturaActual) {
        this.facturaActual = facturaActual;
    }
    
    @PostConstruct
    public void cargaInicial(){
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
    
    public void doBuscarFacturasCliente()
    {
        this.facturas = dao.buscarPorCliente(clienteSeleccionado);
    }
 
    private List<Factura> refrescarLista(){
        
        return dao.buscarPorUsuario(this.autenticacionController.getUsuarioLogueado());
    }
    
    private List<Cliente> refrescarClientes(){
        return daoCliente.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }
    
    public void doNuevo(){
        this.esNuevo = true;
        this.facturaActual = new Factura();
        this.lineasDeFacturaActual = new ArrayList<>();
        
    }
    
    public void doEditar(Factura factura){
        this.esNuevo = false;
        this.facturaActual = factura;
        
        if(factura.getLineas() == null)
            this.lineasDeFacturaActual = new ArrayList<>();
        else
            this.lineasDeFacturaActual = factura.getLineas();
        
    }
    
    public void doNuevaLinea()
    {
        this.esNuevaLinea = true;
        this.lineaFacturaActual = new LineaFactura();
        this.lineaFacturaActual.setPrecioUnitario(0);
        this.lineaFacturaActual.setPorcentajeDescuento(0);
        
    }
    
    public void doEditarLinea(LineaFactura lineaFactura)
    {
        this.esNuevaLinea = false;
        this.lineaFacturaActual = lineaFactura;
    }
    
    public void doEliminarLinea(LineaFactura lineaFactura)
    {
        this.lineasDeFacturaActual.remove(lineaFactura);
        if(this.lineasDeFacturaCrear.contains(lineaFactura))
        {
            this.lineasDeFacturaCrear.remove(lineaFactura);
        }
        else if(this.lineasDeFacturaActualizar.contains(lineaFactura))
        {
            this.lineasDeFacturaActualizar.remove(lineaFactura);
        }
        else{
            this.lineasDeFacturaEliminar.add(lineaFactura);
        }
    }
    
    
    public void doGuardarEditadoLineaFactura(){
        if (this.esNuevaLinea) {
            this.lineasDeFacturaCrear.add(lineaFacturaActual);
            this.lineasDeFacturaActual.add(lineaFacturaActual);
        } else {
            this.lineasDeFacturaActualizar.add(lineaFacturaActual);
        }
        
        
        this.lineaFacturaActual = null;
        
        this.esNuevaLinea = false;
    }
    
    public void doCancelarEditadoLineaFactura(){
        this.lineaFacturaActual = null;
        this.esNuevaLinea = false;
    }
    
    public void doGuardarEditado()
    {
        if (this.esNuevo) {
            dao.crear(facturaActual);
            
        } else {
            dao.actualizar(facturaActual);
            for(LineaFactura l : this.lineasDeFacturaActualizar)
            {
                daoLinea.actualizar(l);
            }
            for(LineaFactura l : this.lineasDeFacturaEliminar)
            {
                daoLinea.eliminar(l);
            }
        }
        for(LineaFactura l : this.lineasDeFacturaCrear)
        {
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
    
    public void doEliminar(Factura f)
    {
        dao.eliminar(f);
        this.facturas = this.refrescarLista();
    }
    
    public void doCancelarEditado(){
        this.facturaActual = null;
        this.esNuevo = false;
    }
    
    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
