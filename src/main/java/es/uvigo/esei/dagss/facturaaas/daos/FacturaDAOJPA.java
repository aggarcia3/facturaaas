package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Implementa el contrato que debe de cumplir un DAO de Facturas.
 *
 * @author Alejandro González García
 */
@Stateless
public class FacturaDAOJPA extends GenericoDAOJPA<Factura, Long> implements FacturaDAO {
    @Override
    public List<Factura> buscarPorCliente(final Cliente cliente) {
        final TypedQuery<Factura> query = em.createQuery(
            "SELECT f FROM Factura AS f WHERE f.cliente.id = :idCliente", Factura.class
        );
        query.setParameter("idCliente", cliente.getId());

        return query.getResultList();
    }

    @Override
    public List<Factura> buscarPorUsuario(Usuario usuario) {
        final TypedQuery<Factura> query = em.createQuery(
            "SELECT f FROM Factura AS f WHERE f.cliente.propietario.id = :idUsuario", Factura.class
        );
        query.setParameter("idUsuario", usuario.getId());
        
        return query.getResultList();
    }
}
