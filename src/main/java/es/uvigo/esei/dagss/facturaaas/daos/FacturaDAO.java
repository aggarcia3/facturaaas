package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import java.util.List;

/**
 *
 * @author Alejandro González García
 */
public interface FacturaDAO extends GenericoDAO<Factura, Integer> {
    public List<Factura> buscarPorCliente(Cliente cliente);
}
