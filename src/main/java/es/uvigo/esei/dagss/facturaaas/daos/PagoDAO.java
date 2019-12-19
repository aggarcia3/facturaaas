package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import java.util.List;

/**
 * Especifica el contrato que debe de cumplir un DAO de Pagos.
 *
 * @author Alejandro González García
 */
public interface PagoDAO extends GenericoDAO<Pago, Long> {
    /**
     * Recupera los pagos a nombre de un cliente.
     *
     * @param cliente El cliente cuyos pagos recuperar.
     * @return La lista de pagos a nombre de ese cliente.
     */
    public List<Pago> buscarPorCliente(Cliente cliente);
}
