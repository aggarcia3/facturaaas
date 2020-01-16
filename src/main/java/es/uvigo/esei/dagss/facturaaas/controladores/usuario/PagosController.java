package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.PagoDAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;

/**
 * Coordina la ejecución de los casos de uso relacionados con la entidad Pago.
 *
 * @author Alejandro González García
 */
@Named(value = "pagosController")
@ViewScoped
public class PagosController implements Serializable {
    @Inject
    private PagoDAO dao;
    @Inject
    private ClienteDAO clienteDao;
    @Inject
    private AutenticacionController controladorAutenticacion;

    @Getter
    private List<Pago> pagos;

    @Getter
    private Cliente clienteFiltro = null;

    @Getter
    private Pago pagoActual = null;

    @Getter
    private List<Cliente> clientesUsuario;

    /**
     * Inicializa atributos de este controlador.
     */
    @PostConstruct
    public void inicializar() {
        this.pagos = generarListaPagos();
        this.clientesUsuario = clienteDao.buscarTodosConPropietario(
            controladorAutenticacion.getUsuarioLogueado()
        );
    }

    /**
     * Registra el comienzo de la edición de un pago en el estado interno del
     * controlador.
     *
     * @param pago El pago a editar.
     */
    public void doEditar(Pago pago) {
        this.pagoActual = pago;
    }

    /**
     * Confirma los cambios realizados en un pago, cambiando el estado interno
     * del controlador.
     */
    public void doGuardarEdicion() {
        dao.actualizar(pagoActual);
        this.pagoActual = null;
    }

    /**
     * Descarta los cambios realizados a un pago, cambiando el estado interno
     * del controlador.
     */
    public void doCancelarEdicion() {
        this.pagoActual = null;
    }

    /**
     * Establece el cliente por el que filtrar pagos a mostrar, recuperando una
     * lista de pagos a mostrar actualizada en el proceso.
     *
     * @param cliente El cliente a establecer.
     */
    public void setClienteFiltro(Cliente cliente) {
        this.clienteFiltro = cliente;
        this.pagos = generarListaPagos();
    }

    /**
     * Recupera la lista de pagos a mostrar en la vista.
     *
     * @return La descrita lista.
     */
    private List<Pago> generarListaPagos() {
        return clienteFiltro == null ?
            dao.buscarPorUsuario(controladorAutenticacion.getUsuarioLogueado()) :
            dao.buscarPorCliente(clienteFiltro);
    }
}
