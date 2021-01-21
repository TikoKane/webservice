package sn.isi.m2gl.web.rest;

import sn.isi.m2gl.domain.Corona;
import sn.isi.m2gl.service.CoronaService;
import sn.isi.m2gl.web.rest.errors.BadRequestAlertException;
import sn.isi.m2gl.service.dto.CoronaCriteria;
import sn.isi.m2gl.service.CoronaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link sn.isi.m2gl.domain.Corona}.
 */
@RestController
@RequestMapping("/api")
public class CoronaResource {

    private final Logger log = LoggerFactory.getLogger(CoronaResource.class);

    private static final String ENTITY_NAME = "corona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoronaService coronaService;

    private final CoronaQueryService coronaQueryService;

    public CoronaResource(CoronaService coronaService, CoronaQueryService coronaQueryService) {
        this.coronaService = coronaService;
        this.coronaQueryService = coronaQueryService;
    }

    /**
     * {@code POST  /coronas} : Create a new corona.
     *
     * @param corona the corona to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corona, or with status {@code 400 (Bad Request)} if the corona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coronas")
    public ResponseEntity<Corona> createCorona(@Valid @RequestBody Corona corona) throws URISyntaxException {
        log.debug("REST request to save Corona : {}", corona);
        if (corona.getId() != null) {
            throw new BadRequestAlertException("A new corona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Corona result = coronaService.save(corona);
        return ResponseEntity.created(new URI("/api/coronas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coronas} : Updates an existing corona.
     *
     * @param corona the corona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corona,
     * or with status {@code 400 (Bad Request)} if the corona is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coronas")
    public ResponseEntity<Corona> updateCorona(@Valid @RequestBody Corona corona) throws URISyntaxException {
        log.debug("REST request to update Corona : {}", corona);
        if (corona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Corona result = coronaService.save(corona);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, corona.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coronas} : get all the coronas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coronas in body.
     */
    @GetMapping("/coronas")
    public ResponseEntity<List<Corona>> getAllCoronas(CoronaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Coronas by criteria: {}", criteria);
        Page<Corona> page = coronaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coronas/count} : count all the coronas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/coronas/count")
    public ResponseEntity<Long> countCoronas(CoronaCriteria criteria) {
        log.debug("REST request to count Coronas by criteria: {}", criteria);
        return ResponseEntity.ok().body(coronaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /coronas/:id} : get the "id" corona.
     *
     * @param id the id of the corona to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corona, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coronas/{id}")
    public ResponseEntity<Corona> getCorona(@PathVariable Long id) {
        log.debug("REST request to get Corona : {}", id);
        Optional<Corona> corona = coronaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(corona);
    }

    /**
     * {@code DELETE  /coronas/:id} : delete the "id" corona.
     *
     * @param id the id of the corona to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coronas/{id}")
    public ResponseEntity<Void> deleteCorona(@PathVariable Long id) {
        log.debug("REST request to delete Corona : {}", id);
        coronaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/coronas?query=:query} : search for the corona corresponding
     * to the query.
     *
     * @param query the query of the corona search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/coronas")
    public ResponseEntity<List<Corona>> searchCoronas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Coronas for query {}", query);
        Page<Corona> page = coronaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
