/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;

/**
 *
 * @author alex
 */
public interface LineaFacturaDAO extends GenericoDAO<LineaFactura, Long>{
    public void eliminarPorFactura(Factura f);
}
