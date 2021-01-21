package sn.isi.m2gl.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Corona.
 */
@Entity
@Table(name = "corona")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "corona")
public class Corona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombrecasparjour", nullable = false)
    private String nombrecasparjour;

    @NotNull
    @Column(name = "caspositif", nullable = false)
    private String caspositif;

    @NotNull
    @Column(name = "cascommunautaire", nullable = false)
    private String cascommunautaire;

    @NotNull
    @Column(name = "casgrave", nullable = false)
    private String casgrave;

    @Column(name = "guerison")
    private String guerison;

    @Column(name = "deces")
    private String deces;

    @Column(name = "cascontact")
    private String cascontact;

    @Column(name = "casimporte")
    private String casimporte;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrecasparjour() {
        return nombrecasparjour;
    }

    public Corona nombrecasparjour(String nombrecasparjour) {
        this.nombrecasparjour = nombrecasparjour;
        return this;
    }

    public void setNombrecasparjour(String nombrecasparjour) {
        this.nombrecasparjour = nombrecasparjour;
    }

    public String getCaspositif() {
        return caspositif;
    }

    public Corona caspositif(String caspositif) {
        this.caspositif = caspositif;
        return this;
    }

    public void setCaspositif(String caspositif) {
        this.caspositif = caspositif;
    }

    public String getCascommunautaire() {
        return cascommunautaire;
    }

    public Corona cascommunautaire(String cascommunautaire) {
        this.cascommunautaire = cascommunautaire;
        return this;
    }

    public void setCascommunautaire(String cascommunautaire) {
        this.cascommunautaire = cascommunautaire;
    }

    public String getCasgrave() {
        return casgrave;
    }

    public Corona casgrave(String casgrave) {
        this.casgrave = casgrave;
        return this;
    }

    public void setCasgrave(String casgrave) {
        this.casgrave = casgrave;
    }

    public String getGuerison() {
        return guerison;
    }

    public Corona guerison(String guerison) {
        this.guerison = guerison;
        return this;
    }

    public void setGuerison(String guerison) {
        this.guerison = guerison;
    }

    public String getDeces() {
        return deces;
    }

    public Corona deces(String deces) {
        this.deces = deces;
        return this;
    }

    public void setDeces(String deces) {
        this.deces = deces;
    }

    public String getCascontact() {
        return cascontact;
    }

    public Corona cascontact(String cascontact) {
        this.cascontact = cascontact;
        return this;
    }

    public void setCascontact(String cascontact) {
        this.cascontact = cascontact;
    }

    public String getCasimporte() {
        return casimporte;
    }

    public Corona casimporte(String casimporte) {
        this.casimporte = casimporte;
        return this;
    }

    public void setCasimporte(String casimporte) {
        this.casimporte = casimporte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corona)) {
            return false;
        }
        return id != null && id.equals(((Corona) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Corona{" +
            "id=" + getId() +
            ", nombrecasparjour='" + getNombrecasparjour() + "'" +
            ", caspositif='" + getCaspositif() + "'" +
            ", cascommunautaire='" + getCascommunautaire() + "'" +
            ", casgrave='" + getCasgrave() + "'" +
            ", guerison='" + getGuerison() + "'" +
            ", deces='" + getDeces() + "'" +
            ", cascontact='" + getCascontact() + "'" +
            ", casimporte='" + getCasimporte() + "'" +
            "}";
    }
}
