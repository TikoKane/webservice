package sn.isi.m2gl.web.rest;

import sn.isi.m2gl.domain.Covid;
import sn.isi.m2gl.service.CovidService;
import sn.isi.m2gl.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link sn.isi.m2gl.domain.Covid}.
 */
@RestController
@RequestMapping("/api")
public class CovidResource {

    private final Logger log = LoggerFactory.getLogger(CovidResource.class);

    private static final String ENTITY_NAME = "covid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CovidService covidService;

    public CovidResource(CovidService covidService) {
        this.covidService = covidService;
    }

    /**
     * {@code POST  /covids} : Create a new covid.
     *
     * @param covid the covid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new covid, or with status {@code 400 (Bad Request)} if the covid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/covids")
    public ResponseEntity<Covid> createCovid(@Valid @RequestBody Covid covid) throws URISyntaxException {
        log.debug("REST request to save Covid : {}", covid);
        if (covid.getId() != null) {
            throw new BadRequestAlertException("A new covid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Covid result = covidService.save(covid);
        return ResponseEntity.created(new URI("/api/covids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /covids} : Updates an existing covid.
     *
     * @param covid the covid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated covid,
     * or with status {@code 400 (Bad Request)} if the covid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the covid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/covids")
    public ResponseEntity<Covid> updateCovid(@Valid @RequestBody Covid covid) throws URISyntaxException {
        log.debug("REST request to update Covid : {}", covid);
        if (covid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Covid result = covidService.save(covid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, covid.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /covids} : get all the covids.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of covids in body.
     */
    @GetMapping("/covids")
    public ResponseEntity<List<Covid>> getAllCovids(Pageable pageable) {
        log.debug("REST request to get a page of Covids");
        Page<Covid> page = covidService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /covids/:id} : get the "id" covid.
     *
     * @param id the id of the covid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the covid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/covids/{id}")
    public ResponseEntity<Covid> getCovid(@PathVariable Long id) {
        log.debug("REST request to get Covid : {}", id);
        Optional<Covid> covid = covidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(covid);
    }

    /**
     * {@code DELETE  /covids/:id} : delete the "id" covid.
     *
     * @param id the id of the covid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/covids/{id}")
    public ResponseEntity<Void> deleteCovid(@PathVariable Long id) {
        log.debug("REST request to delete Covid : {}", id);
        covidService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/covids?query=:query} : search for the covid corresponding
     * to the query.
     *
     * @param query the query of the covid search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/covids")
    public ResponseEntity<List<Covid>> searchCovids(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Covids for query {}", query);
        Page<Covid> page = covidService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
