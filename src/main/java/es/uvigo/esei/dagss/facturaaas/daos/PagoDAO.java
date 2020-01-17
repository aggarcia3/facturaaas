package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;

/**
 * Especifica el contrato que debe de cumplir un DAO de Pagos.
 *
 * @author Alejandro González García
 */
public interface PagoDAO extends GenericoDAO<Pago, Long> {
    /**
     * Recupera los pagos a nombre de cualquier cliente cuyo propietario sea un
     * determinado usuario.
     *
     * @param usuario El usuario cuyos clientes tendrán asociados los pagos
     * devueltos.
     * @return La descrita lista de pagos.
     */
    public List<Pago> buscarPorUsuario(Usuario usuario);

    /**
     * Recupera los pagos a nombre de un cliente.
     *
     * @param cliente El cliente cuyos pagos recuperar.
     * @return La lista de pagos a nombre de ese cliente.
     */
    public List<Pago> buscarPorCliente(Cliente cliente);
    
    /**
     * Recupera los pagos correspondientes a una factura
     * @param factura La factura cuyos pagos recuperar
     * @return La lista de pagos asociados a esa factura
     */
    public List<Pago> buscarPorFactura(Factura factura);
    
    /**
     * Elimina los pagos asociados a una factura
     * @param factura La factura de la que eliminar los pagos
     */
    public void eliminarPagosPorFactura(Factura factura);
}
