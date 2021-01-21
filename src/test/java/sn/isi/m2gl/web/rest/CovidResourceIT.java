package sn.isi.m2gl.web.rest;

import sn.isi.m2gl.DbfirstexoApp;
import sn.isi.m2gl.domain.Covid;
import sn.isi.m2gl.repository.CovidRepository;
import sn.isi.m2gl.repository.search.CovidSearchRepository;
import sn.isi.m2gl.service.CovidService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CovidResource} REST controller.
 */
@SpringBootTest(classes = DbfirstexoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CovidResourceIT {

    private static final String DEFAULT_NOMBRETEST = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRETEST = "BBBBBBBBBB";

    private static final String DEFAULT_POSITIFCAS = "AAAAAAAAAA";
    private static final String UPDATED_POSITIFCAS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CovidRepository covidRepository;

    @Autowired
    private CovidService covidService;

    /**
     * This repository is mocked in the sn.isi.m2gl.repository.search test package.
     *
     * @see sn.isi.m2gl.repository.search.CovidSearchRepositoryMockConfiguration
     */
    @Autowired
    private CovidSearchRepository mockCovidSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCovidMockMvc;

    private Covid covid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Covid createEntity(EntityManager em) {
        Covid covid = new Covid()
            .nombretest(DEFAULT_NOMBRETEST)
            .positifcas(DEFAULT_POSITIFCAS)
            .date(DEFAULT_DATE);
        return covid;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Covid createUpdatedEntity(EntityManager em) {
        Covid covid = new Covid()
            .nombretest(UPDATED_NOMBRETEST)
            .positifcas(UPDATED_POSITIFCAS)
            .date(UPDATED_DATE);
        return covid;
    }

    @BeforeEach
    public void initTest() {
        covid = createEntity(em);
    }

    @Test
    @Transactional
    public void createCovid() throws Exception {
        int databaseSizeBeforeCreate = covidRepository.findAll().size();
        // Create the Covid
        restCovidMockMvc.perform(post("/api/covids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covid)))
            .andExpect(status().isCreated());

        // Validate the Covid in the database
        List<Covid> covidList = covidRepository.findAll();
        assertThat(covidList).hasSize(databaseSizeBeforeCreate + 1);
        Covid testCovid = covidList.get(covidList.size() - 1);
        assertThat(testCovid.getNombretest()).isEqualTo(DEFAULT_NOMBRETEST);
        assertThat(testCovid.getPositifcas()).isEqualTo(DEFAULT_POSITIFCAS);
        assertThat(testCovid.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the Covid in Elasticsearch
        verify(mockCovidSearchRepository, times(1)).save(testCovid);
    }

    @Test
    @Transactional
    public void createCovidWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = covidRepository.findAll().size();

        // Create the Covid with an existing ID
        covid.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCovidMockMvc.perform(post("/api/covids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covid)))
            .andExpect(status().isBadRequest());

        // Validate the Covid in the database
        List<Covid> covidList = covidRepository.findAll();
        assertThat(covidList).hasSize(databaseSizeBeforeCreate);

        // Validate the Covid in Elasticsearch
        verify(mockCovidSearchRepository, times(0)).save(covid);
    }


    @Test
    @Transactional
    public void checkPositifcasIsRequired() throws Exception {
        int databaseSizeBeforeTest = covidRepository.findAll().size();
        // set the field null
        covid.setPositifcas(null);

        // Create the Covid, which fails.


        restCovidMockMvc.perform(post("/api/covids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covid)))
            .andExpect(status().isBadRequest());

        List<Covid> covidList = covidRepository.findAll();
        assertThat(covidList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCovids() throws Exception {
        // Initialize the database
        covidRepository.saveAndFlush(covid);

        // Get all the covidList
        restCovidMockMvc.perform(get("/api/covids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(covid.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombretest").value(hasItem(DEFAULT_NOMBRETEST)))
            .andExpect(jsonPath("$.[*].positifcas").value(hasItem(DEFAULT_POSITIFCAS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCovid() throws Exception {
        // Initialize the database
        covidRepository.saveAndFlush(covid);

        // Get the covid
        restCovidMockMvc.perform(get("/api/covids/{id}", covid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(covid.getId().intValue()))
            .andExpect(jsonPath("$.nombretest").value(DEFAULT_NOMBRETEST))
            .andExpect(jsonPath("$.positifcas").value(DEFAULT_POSITIFCAS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCovid() throws Exception {
        // Get the covid
        restCovidMockMvc.perform(get("/api/covids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCovid() throws Exception {
        // Initialize the database
        covidService.save(covid);

        int databaseSizeBeforeUpdate = covidRepository.findAll().size();

        // Update the covid
        Covid updatedCovid = covidRepository.findById(covid.getId()).get();
        // Disconnect from session so that the updates on updatedCovid are not directly saved in db
        em.detach(updatedCovid);
        updatedCovid
            .nombretest(UPDATED_NOMBRETEST)
            .positifcas(UPDATED_POSITIFCAS)
            .date(UPDATED_DATE);

        restCovidMockMvc.perform(put("/api/covids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCovid)))
            .andExpect(status().isOk());

        // Validate the Covid in the database
        List<Covid> covidList = covidRepository.findAll();
        assertThat(covidList).hasSize(databaseSizeBeforeUpdate);
        Covid testCovid = covidList.get(covidList.size() - 1);
        assertThat(testCovid.getNombretest()).isEqualTo(UPDATED_NOMBRETEST);
        assertThat(testCovid.getPositifcas()).isEqualTo(UPDATED_POSITIFCAS);
        assertThat(testCovid.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the Covid in Elasticsearch
        verify(mockCovidSearchRepository, times(2)).save(testCovid);
    }

    @Test
    @Transactional
    public void updateNonExistingCovid() throws Exception {
        int databaseSizeBeforeUpdate = covidRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCovidMockMvc.perform(put("/api/covids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covid)))
            .andExpect(status().isBadRequest());

        // Validate the Covid in the database
        List<Covid> covidList = covidRepository.findAll();
        assertThat(covidList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Covid in Elasticsearch
        verify(mockCovidSearchRepository, times(0)).save(covid);
    }

    @Test
    @Transactional
    public void deleteCovid() throws Exception {
        // Initialize the database
        covidService.save(covid);

        int databaseSizeBeforeDelete = covidRepository.findAll().size();

        // Delete the covid
        restCovidMockMvc.perform(delete("/api/covids/{id}", covid.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Covid> covidList = covidRepository.findAll();
        assertThat(covidList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Covid in Elasticsearch
        verify(mockCovidSearchRepository, times(1)).deleteById(covid.getId());
    }

    @Test
    @Transactional
    public void searchCovid() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        covidService.save(covid);
        when(mockCovidSearchRepository.search(queryStringQuery("id:" + covid.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(covid), PageRequest.of(0, 1), 1));

        // Search the covid
        restCovidMockMvc.perform(get("/api/_search/covids?query=id:" + covid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(covid.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombretest").value(hasItem(DEFAULT_NOMBRETEST)))
            .andExpect(jsonPath("$.[*].positifcas").value(hasItem(DEFAULT_POSITIFCAS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
}
