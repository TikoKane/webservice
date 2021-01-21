package sn.isi.m2gl.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CoronaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CoronaSearchRepositoryMockConfiguration {

    @MockBean
    private CoronaSearchRepository mockCoronaSearchRepository;

}
