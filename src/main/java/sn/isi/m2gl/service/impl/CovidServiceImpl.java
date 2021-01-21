package sn.isi.m2gl.service.impl;

import sn.isi.m2gl.service.CovidService;
import sn.isi.m2gl.domain.Covid;
import sn.isi.m2gl.repository.CovidRepository;
import sn.isi.m2gl.repository.search.CovidSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Covid}.
 */
@Service
@Transactional
public class CovidServiceImpl implements CovidService {

    private final Logger log = LoggerFactory.getLogger(CovidServiceImpl.class);

    private final CovidRepository covidRepository;

    private final CovidSearchRepository covidSearchRepository;

    public CovidServiceImpl(CovidRepository covidRepository, CovidSearchRepository covidSearchRepository) {
        this.covidRepository = covidRepository;
        this.covidSearchRepository = covidSearchRepository;
    }

    @Override
    public Covid save(Covid covid) {
        log.debug("Request to save Covid : {}", covid);
        Covid result = covidRepository.save(covid);
        covidSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Covid> findAll(Pageable pageable) {
        log.debug("Request to get all Covids");
        return covidRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Covid> findOne(Long id) {
        log.debug("Request to get Covid : {}", id);
        return covidRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Covid : {}", id);
        covidRepository.deleteById(id);
        covidSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Covid> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Covids for query {}", query);
        return covidSearchRepository.search(queryStringQuery(query), pageable);    }
}
