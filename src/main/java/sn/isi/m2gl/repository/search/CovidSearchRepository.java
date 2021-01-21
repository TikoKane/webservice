package sn.isi.m2gl.repository.search;

import sn.isi.m2gl.domain.Covid;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Covid} entity.
 */
public interface CovidSearchRepository extends ElasticsearchRepository<Covid, Long> {
}
