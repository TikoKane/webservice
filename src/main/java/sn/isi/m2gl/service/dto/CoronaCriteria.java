package sn.isi.m2gl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link sn.isi.m2gl.domain.Corona} entity. This class is used
 * in {@link sn.isi.m2gl.web.rest.CoronaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /coronas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoronaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombrecasparjour;

    private StringFilter caspositif;

    private StringFilter cascommunautaire;

    private StringFilter casgrave;

    private StringFilter guerison;

    private StringFilter deces;

    private StringFilter cascontact;

    private StringFilter casimporte;

    public CoronaCriteria() {
    }

    public CoronaCriteria(CoronaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombrecasparjour = other.nombrecasparjour == null ? null : other.nombrecasparjour.copy();
        this.caspositif = other.caspositif == null ? null : other.caspositif.copy();
        this.cascommunautaire = other.cascommunautaire == null ? null : other.cascommunautaire.copy();
        this.casgrave = other.casgrave == null ? null : other.casgrave.copy();
        this.guerison = other.guerison == null ? null : other.guerison.copy();
        this.deces = other.deces == null ? null : other.deces.copy();
        this.cascontact = other.cascontact == null ? null : other.cascontact.copy();
        this.casimporte = other.casimporte == null ? null : other.casimporte.copy();
    }

    @Override
    public CoronaCriteria copy() {
        return new CoronaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombrecasparjour() {
        return nombrecasparjour;
    }

    public void setNombrecasparjour(StringFilter nombrecasparjour) {
        this.nombrecasparjour = nombrecasparjour;
    }

    public StringFilter getCaspositif() {
        return caspositif;
    }

    public void setCaspositif(StringFilter caspositif) {
        this.caspositif = caspositif;
    }

    public StringFilter getCascommunautaire() {
        return cascommunautaire;
    }

    public void setCascommunautaire(StringFilter cascommunautaire) {
        this.cascommunautaire = cascommunautaire;
    }

    public StringFilter getCasgrave() {
        return casgrave;
    }

    public void setCasgrave(StringFilter casgrave) {
        this.casgrave = casgrave;
    }

    public StringFilter getGuerison() {
        return guerison;
    }

    public void setGuerison(StringFilter guerison) {
        this.guerison = guerison;
    }

    public StringFilter getDeces() {
        return deces;
    }

    public void setDeces(StringFilter deces) {
        this.deces = deces;
    }

    public StringFilter getCascontact() {
        return cascontact;
    }

    public void setCascontact(StringFilter cascontact) {
        this.cascontact = cascontact;
    }

    public StringFilter getCasimporte() {
        return casimporte;
    }

    public void setCasimporte(StringFilter casimporte) {
        this.casimporte = casimporte;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CoronaCriteria that = (CoronaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombrecasparjour, that.nombrecasparjour) &&
            Objects.equals(caspositif, that.caspositif) &&
            Objects.equals(cascommunautaire, that.cascommunautaire) &&
            Objects.equals(casgrave, that.casgrave) &&
            Objects.equals(guerison, that.guerison) &&
            Objects.equals(deces, that.deces) &&
            Objects.equals(cascontact, that.cascontact) &&
            Objects.equals(casimporte, that.casimporte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombrecasparjour,
        caspositif,
        cascommunautaire,
        casgrave,
        guerison,
        deces,
        cascontact,
        casimporte
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoronaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombrecasparjour != null ? "nombrecasparjour=" + nombrecasparjour + ", " : "") +
                (caspositif != null ? "caspositif=" + caspositif + ", " : "") +
                (cascommunautaire != null ? "cascommunautaire=" + cascommunautaire + ", " : "") +
                (casgrave != null ? "casgrave=" + casgrave + ", " : "") +
                (guerison != null ? "guerison=" + guerison + ", " : "") +
                (deces != null ? "deces=" + deces + ", " : "") +
                (cascontact != null ? "cascontact=" + cascontact + ", " : "") +
                (casimporte != null ? "casimporte=" + casimporte + ", " : "") +
            "}";
    }

}
