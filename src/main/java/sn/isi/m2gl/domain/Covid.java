package sn.isi.m2gl.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Covid.
 */
@Entity
@Table(name = "covid")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "covid")
public class Covid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombretest")
    private String nombretest;

    @NotNull
    @Column(name = "positifcas", nullable = false)
    private String positifcas;

    @Column(name = "date")
    private LocalDate date;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombretest() {
        return nombretest;
    }

    public Covid nombretest(String nombretest) {
        this.nombretest = nombretest;
        return this;
    }

    public void setNombretest(String nombretest) {
        this.nombretest = nombretest;
    }

    public String getPositifcas() {
        return positifcas;
    }

    public Covid positifcas(String positifcas) {
        this.positifcas = positifcas;
        return this;
    }

    public void setPositifcas(String positifcas) {
        this.positifcas = positifcas;
    }

    public LocalDate getDate() {
        return date;
    }

    public Covid date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Covid)) {
            return false;
        }
        return id != null && id.equals(((Covid) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Covid{" +
            "id=" + getId() +
            ", nombretest='" + getNombretest() + "'" +
            ", positifcas='" + getPositifcas() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
