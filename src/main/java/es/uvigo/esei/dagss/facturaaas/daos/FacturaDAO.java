package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;

/**
 * Especifica el contrato que debe de cumplir un DAO de Facturas.
 *
 * @author Alejandro González García
 */
public interface FacturaDAO extends GenericoDAO<Factura, Long> {
    /**
     * Recupera las facturas asociadas a un determinado cliente.
     *
     * @param cliente El cliente cuyas facturas recuperar.
     * @return Una lista con las facturas a nombre del cliente.
     */
    public List<Factura> buscarPorCliente(Cliente cliente);

    /**
     * Recupera las facturas asociadas a cualquiera de los clientes de un
     * usuario.
     *
     * @param usuario El usuario cuyas facturas recuperar.
     * @return La lista de facturas recuperadas.
     */
    public List<Factura> buscarPorUsuario(Usuario usuario);
}
