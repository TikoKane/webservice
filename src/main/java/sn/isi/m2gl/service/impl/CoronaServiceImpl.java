package sn.isi.m2gl.service.impl;

import sn.isi.m2gl.service.CoronaService;
import sn.isi.m2gl.domain.Corona;
import sn.isi.m2gl.repository.CoronaRepository;
import sn.isi.m2gl.repository.search.CoronaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Corona}.
 */
@Service
@Transactional
public class CoronaServiceImpl implements CoronaService {

    private final Logger log = LoggerFactory.getLogger(CoronaServiceImpl.class);

    private final CoronaRepository coronaRepository;

    private final CoronaSearchRepository coronaSearchRepository;

    public CoronaServiceImpl(CoronaRepository coronaRepository, CoronaSearchRepository coronaSearchRepository) {
        this.coronaRepository = coronaRepository;
        this.coronaSearchRepository = coronaSearchRepository;
    }

    @Override
    public Corona save(Corona corona) {
        log.debug("Request to save Corona : {}", corona);
        Corona result = coronaRepository.save(corona);
        coronaSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Corona> findAll(Pageable pageable) {
        log.debug("Request to get all Coronas");
        return coronaRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Corona> findOne(Long id) {
        log.debug("Request to get Corona : {}", id);
        return coronaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Corona : {}", id);
        coronaRepository.deleteById(id);
        coronaSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Corona> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Coronas for query {}", query);
        return coronaSearchRepository.search(queryStringQuery(query), pageable);    }
}
