package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
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
    public List<Pago> buscarPorUsuario(final Usuario usuario) {
        final TypedQuery<Pago> query = em.createQuery(
            "SELECT p FROM Pago AS p WHERE p.factura.cliente.propietario.id = :idUsuario", Pago.class
        );
        query.setParameter("idUsuario", usuario.getId());

        return query.getResultList();
    }

    @Override
    public List<Pago> buscarPorCliente(final Cliente cliente) {
        final TypedQuery<Pago> query = em.createQuery(
            "SELECT p FROM Pago AS p WHERE p.factura.cliente.id = :idCliente", Pago.class
        );
        query.setParameter("idCliente", cliente.getId());

        return query.getResultList();
    }

    @Override
    public List<Pago> buscarPorFactura(Factura factura) {
        final TypedQuery<Pago> query = em.createQuery(
            "SELECT p FROM Pago AS p WHERE p.factura = :idFactura", Pago.class
        );
        query.setParameter("idFactura", factura.getId());

        return query.getResultList();
    }
    
    @Override
    public void eliminarPagosPorFactura(Factura factura) {
        final TypedQuery<Pago> query = em.createQuery(
            "DELETE FROM Pago WHERE factura = :idFactura", Pago.class
        );
        query.setParameter("idFactura", factura);

        query.executeUpdate();
    }
}
