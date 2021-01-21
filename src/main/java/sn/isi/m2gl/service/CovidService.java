package sn.isi.m2gl.service;

import sn.isi.m2gl.domain.Covid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Covid}.
 */
public interface CovidService {

    /**
     * Save a covid.
     *
     * @param covid the entity to save.
     * @return the persisted entity.
     */
    Covid save(Covid covid);

    /**
     * Get all the covids.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Covid> findAll(Pageable pageable);


    /**
     * Get the "id" covid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Covid> findOne(Long id);

    /**
     * Delete the "id" covid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the covid corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Covid> search(String query, Pageable pageable);
}
