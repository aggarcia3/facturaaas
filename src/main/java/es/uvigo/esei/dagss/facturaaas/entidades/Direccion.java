package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Direccion implements Serializable {
    private String domicilio;
    private String localidad;
    private String codPostal;
    private String provincia;

    public Direccion() {
    }

    public Direccion(String domicilio, String localidad, String codPostal, String provincia) {
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.codPostal = codPostal;
        this.provincia = provincia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.domicilio);
        hash = 37 * hash + Objects.hashCode(this.localidad);
        hash = 37 * hash + Objects.hashCode(this.codPostal);
        hash = 37 * hash + Objects.hashCode(this.provincia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Direccion other = (Direccion) obj;
        if (!Objects.equals(this.domicilio, other.domicilio)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        if (!Objects.equals(this.codPostal, other.codPostal)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Direccion{" + "domicilio=" + domicilio + ", localidad=" + localidad + ", codPostal=" + codPostal + ", provincia=" + provincia + '}';
    }
    
    
}


