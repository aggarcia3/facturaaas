package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;

public interface UsuarioDAO extends GenericoDAO<Usuario, Long> {
    public Usuario buscarPorLogin(String login);
    public List<Usuario> buscarPorNombre(String patron);
}
