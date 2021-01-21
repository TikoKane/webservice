package sn.isi.m2gl.service;

import sn.isi.m2gl.domain.Corona;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Corona}.
 */
public interface CoronaService {

    /**
     * Save a corona.
     *
     * @param corona the entity to save.
     * @return the persisted entity.
     */
    Corona save(Corona corona);

    /**
     * Get all the coronas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Corona> findAll(Pageable pageable);


    /**
     * Get the "id" corona.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Corona> findOne(Long id);

    /**
     * Delete the "id" corona.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the corona corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Corona> search(String query, Pageable pageable);
}
