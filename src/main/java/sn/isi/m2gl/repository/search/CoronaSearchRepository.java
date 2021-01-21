package sn.isi.m2gl.repository.search;

import sn.isi.m2gl.domain.Corona;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Corona} entity.
 */
public interface CoronaSearchRepository extends ElasticsearchRepository<Corona, Long> {
}
