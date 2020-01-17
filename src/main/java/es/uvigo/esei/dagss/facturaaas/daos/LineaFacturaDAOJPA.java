package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author alex
 */
@Stateless
public class LineaFacturaDAOJPA extends GenericoDAOJPA<LineaFactura,Long> implements LineaFacturaDAO{
    @Override
    public void eliminarPorFactura(Factura f) {
        final TypedQuery<LineaFactura> query = em.createQuery(
            "DELETE FROM LineaFactura AS l WHERE l.factura.id = :idFactura", LineaFactura.class
        );

        query.setParameter("idFactura", f.getId());
        query.executeUpdate();
    }
}
