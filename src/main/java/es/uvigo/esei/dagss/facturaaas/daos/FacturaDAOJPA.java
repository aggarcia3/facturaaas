package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alejandro González García
 */
@Stateless
public class FacturaDAOJPA extends GenericoDAOJPA<Factura, Integer> implements FacturaDAO {
    @Override
    public List<Factura> buscarPorCliente(Cliente cliente) {
        final TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura AS f WHERE f.cliente.id = :idCliente", Factura.class);
        query.setParameter("idCliente", cliente.getId());

        return query.getResultList();
    }
}
