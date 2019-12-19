package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Implementa el contrato que debe de cumplir un DAO de Pagos.
 *
 * @author Alejandro González García
 */
@Stateless
public class PagoDAOJPA extends GenericoDAOJPA<Pago, Long> implements PagoDAO {
    @Override
    public List<Pago> buscarPorCliente(final Cliente cliente) {
        final TypedQuery<Pago> query = em.createQuery(
            "SELECT p FROM Pago AS p WHERE p.factura.cliente.id = :idCliente", Pago.class
        );
        query.setParameter("idCliente", cliente.getId());

        return query.getResultList();
    }
}
