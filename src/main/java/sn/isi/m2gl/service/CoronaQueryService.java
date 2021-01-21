package sn.isi.m2gl.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import sn.isi.m2gl.domain.Corona;
import sn.isi.m2gl.domain.*; // for static metamodels
import sn.isi.m2gl.repository.CoronaRepository;
import sn.isi.m2gl.repository.search.CoronaSearchRepository;
import sn.isi.m2gl.service.dto.CoronaCriteria;

/**
 * Service for executing complex queries for {@link Corona} entities in the database.
 * The main input is a {@link CoronaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Corona} or a {@link Page} of {@link Corona} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoronaQueryService extends QueryService<Corona> {

    private final Logger log = LoggerFactory.getLogger(CoronaQueryService.class);

    private final CoronaRepository coronaRepository;

    private final CoronaSearchRepository coronaSearchRepository;

    public CoronaQueryService(CoronaRepository coronaRepository, CoronaSearchRepository coronaSearchRepository) {
        this.coronaRepository = coronaRepository;
        this.coronaSearchRepository = coronaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Corona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Corona> findByCriteria(CoronaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Corona> specification = createSpecification(criteria);
        return coronaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Corona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Corona> findByCriteria(CoronaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Corona> specification = createSpecification(criteria);
        return coronaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoronaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Corona> specification = createSpecification(criteria);
        return coronaRepository.count(specification);
    }

    /**
     * Function to convert {@link CoronaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Corona> createSpecification(CoronaCriteria criteria) {
        Specification<Corona> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Corona_.id));
            }
            if (criteria.getNombrecasparjour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombrecasparjour(), Corona_.nombrecasparjour));
            }
            if (criteria.getCaspositif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaspositif(), Corona_.caspositif));
            }
            if (criteria.getCascommunautaire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCascommunautaire(), Corona_.cascommunautaire));
            }
            if (criteria.getCasgrave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCasgrave(), Corona_.casgrave));
            }
            if (criteria.getGuerison() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuerison(), Corona_.guerison));
            }
            if (criteria.getDeces() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeces(), Corona_.deces));
            }
            if (criteria.getCascontact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCascontact(), Corona_.cascontact));
            }
            if (criteria.getCasimporte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCasimporte(), Corona_.casimporte));
            }
        }
        return specification;
    }
}
