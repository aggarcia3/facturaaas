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
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoPago;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
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
    private ClienteDAO daoCliente;
    
    @Inject
    private FormaPagoDAO daoFormaPago;
    
    @Inject
    private AutenticacionController autenticacionController;
    
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
        this.clientes = refrescarClientes();
        this.formasDePago = daoFormaPago.buscarActivas();
        this.esNuevo = false;
        
        



        
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
    }
    
    public void doEditar(Factura factura){
        this.esNuevo = false;
        this.facturaActual = factura;
    }
    
    public void doGuardarEditado()
    {
        if (this.esNuevo) {
            dao.crear(facturaActual);
        } else {
            dao.actualizar(facturaActual);
        }
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
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
