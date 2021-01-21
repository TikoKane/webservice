package sn.isi.m2gl.web.rest;

import sn.isi.m2gl.DbfirstexoApp;
import sn.isi.m2gl.domain.Corona;
import sn.isi.m2gl.repository.CoronaRepository;
import sn.isi.m2gl.repository.search.CoronaSearchRepository;
import sn.isi.m2gl.service.CoronaService;
import sn.isi.m2gl.service.dto.CoronaCriteria;
import sn.isi.m2gl.service.CoronaQueryService;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoronaResource} REST controller.
 */
@SpringBootTest(classes = DbfirstexoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CoronaResourceIT {

    private static final String DEFAULT_NOMBRECASPARJOUR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRECASPARJOUR = "BBBBBBBBBB";

    private static final String DEFAULT_CASPOSITIF = "AAAAAAAAAA";
    private static final String UPDATED_CASPOSITIF = "BBBBBBBBBB";

    private static final String DEFAULT_CASCOMMUNAUTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_CASCOMMUNAUTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_CASGRAVE = "AAAAAAAAAA";
    private static final String UPDATED_CASGRAVE = "BBBBBBBBBB";

    private static final String DEFAULT_GUERISON = "AAAAAAAAAA";
    private static final String UPDATED_GUERISON = "BBBBBBBBBB";

    private static final String DEFAULT_DECES = "AAAAAAAAAA";
    private static final String UPDATED_DECES = "BBBBBBBBBB";

    private static final String DEFAULT_CASCONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CASCONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CASIMPORTE = "AAAAAAAAAA";
    private static final String UPDATED_CASIMPORTE = "BBBBBBBBBB";

    @Autowired
    private CoronaRepository coronaRepository;

    @Autowired
    private CoronaService coronaService;

    /**
     * This repository is mocked in the sn.isi.m2gl.repository.search test package.
     *
     * @see sn.isi.m2gl.repository.search.CoronaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CoronaSearchRepository mockCoronaSearchRepository;

    @Autowired
    private CoronaQueryService coronaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoronaMockMvc;

    private Corona corona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corona createEntity(EntityManager em) {
        Corona corona = new Corona()
            .nombrecasparjour(DEFAULT_NOMBRECASPARJOUR)
            .caspositif(DEFAULT_CASPOSITIF)
            .cascommunautaire(DEFAULT_CASCOMMUNAUTAIRE)
            .casgrave(DEFAULT_CASGRAVE)
            .guerison(DEFAULT_GUERISON)
            .deces(DEFAULT_DECES)
            .cascontact(DEFAULT_CASCONTACT)
            .casimporte(DEFAULT_CASIMPORTE);
        return corona;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corona createUpdatedEntity(EntityManager em) {
        Corona corona = new Corona()
            .nombrecasparjour(UPDATED_NOMBRECASPARJOUR)
            .caspositif(UPDATED_CASPOSITIF)
            .cascommunautaire(UPDATED_CASCOMMUNAUTAIRE)
            .casgrave(UPDATED_CASGRAVE)
            .guerison(UPDATED_GUERISON)
            .deces(UPDATED_DECES)
            .cascontact(UPDATED_CASCONTACT)
            .casimporte(UPDATED_CASIMPORTE);
        return corona;
    }

    @BeforeEach
    public void initTest() {
        corona = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorona() throws Exception {
        int databaseSizeBeforeCreate = coronaRepository.findAll().size();
        // Create the Corona
        restCoronaMockMvc.perform(post("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isCreated());

        // Validate the Corona in the database
        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeCreate + 1);
        Corona testCorona = coronaList.get(coronaList.size() - 1);
        assertThat(testCorona.getNombrecasparjour()).isEqualTo(DEFAULT_NOMBRECASPARJOUR);
        assertThat(testCorona.getCaspositif()).isEqualTo(DEFAULT_CASPOSITIF);
        assertThat(testCorona.getCascommunautaire()).isEqualTo(DEFAULT_CASCOMMUNAUTAIRE);
        assertThat(testCorona.getCasgrave()).isEqualTo(DEFAULT_CASGRAVE);
        assertThat(testCorona.getGuerison()).isEqualTo(DEFAULT_GUERISON);
        assertThat(testCorona.getDeces()).isEqualTo(DEFAULT_DECES);
        assertThat(testCorona.getCascontact()).isEqualTo(DEFAULT_CASCONTACT);
        assertThat(testCorona.getCasimporte()).isEqualTo(DEFAULT_CASIMPORTE);

        // Validate the Corona in Elasticsearch
        verify(mockCoronaSearchRepository, times(1)).save(testCorona);
    }

    @Test
    @Transactional
    public void createCoronaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coronaRepository.findAll().size();

        // Create the Corona with an existing ID
        corona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoronaMockMvc.perform(post("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isBadRequest());

        // Validate the Corona in the database
        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Corona in Elasticsearch
        verify(mockCoronaSearchRepository, times(0)).save(corona);
    }


    @Test
    @Transactional
    public void checkNombrecasparjourIsRequired() throws Exception {
        int databaseSizeBeforeTest = coronaRepository.findAll().size();
        // set the field null
        corona.setNombrecasparjour(null);

        // Create the Corona, which fails.


        restCoronaMockMvc.perform(post("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isBadRequest());

        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaspositifIsRequired() throws Exception {
        int databaseSizeBeforeTest = coronaRepository.findAll().size();
        // set the field null
        corona.setCaspositif(null);

        // Create the Corona, which fails.


        restCoronaMockMvc.perform(post("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isBadRequest());

        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCascommunautaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = coronaRepository.findAll().size();
        // set the field null
        corona.setCascommunautaire(null);

        // Create the Corona, which fails.


        restCoronaMockMvc.perform(post("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isBadRequest());

        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCasgraveIsRequired() throws Exception {
        int databaseSizeBeforeTest = coronaRepository.findAll().size();
        // set the field null
        corona.setCasgrave(null);

        // Create the Corona, which fails.


        restCoronaMockMvc.perform(post("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isBadRequest());

        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoronas() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList
        restCoronaMockMvc.perform(get("/api/coronas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrecasparjour").value(hasItem(DEFAULT_NOMBRECASPARJOUR)))
            .andExpect(jsonPath("$.[*].caspositif").value(hasItem(DEFAULT_CASPOSITIF)))
            .andExpect(jsonPath("$.[*].cascommunautaire").value(hasItem(DEFAULT_CASCOMMUNAUTAIRE)))
            .andExpect(jsonPath("$.[*].casgrave").value(hasItem(DEFAULT_CASGRAVE)))
            .andExpect(jsonPath("$.[*].guerison").value(hasItem(DEFAULT_GUERISON)))
            .andExpect(jsonPath("$.[*].deces").value(hasItem(DEFAULT_DECES)))
            .andExpect(jsonPath("$.[*].cascontact").value(hasItem(DEFAULT_CASCONTACT)))
            .andExpect(jsonPath("$.[*].casimporte").value(hasItem(DEFAULT_CASIMPORTE)));
    }
    
    @Test
    @Transactional
    public void getCorona() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get the corona
        restCoronaMockMvc.perform(get("/api/coronas/{id}", corona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corona.getId().intValue()))
            .andExpect(jsonPath("$.nombrecasparjour").value(DEFAULT_NOMBRECASPARJOUR))
            .andExpect(jsonPath("$.caspositif").value(DEFAULT_CASPOSITIF))
            .andExpect(jsonPath("$.cascommunautaire").value(DEFAULT_CASCOMMUNAUTAIRE))
            .andExpect(jsonPath("$.casgrave").value(DEFAULT_CASGRAVE))
            .andExpect(jsonPath("$.guerison").value(DEFAULT_GUERISON))
            .andExpect(jsonPath("$.deces").value(DEFAULT_DECES))
            .andExpect(jsonPath("$.cascontact").value(DEFAULT_CASCONTACT))
            .andExpect(jsonPath("$.casimporte").value(DEFAULT_CASIMPORTE));
    }


    @Test
    @Transactional
    public void getCoronasByIdFiltering() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        Long id = corona.getId();

        defaultCoronaShouldBeFound("id.equals=" + id);
        defaultCoronaShouldNotBeFound("id.notEquals=" + id);

        defaultCoronaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoronaShouldNotBeFound("id.greaterThan=" + id);

        defaultCoronaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoronaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoronasByNombrecasparjourIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where nombrecasparjour equals to DEFAULT_NOMBRECASPARJOUR
        defaultCoronaShouldBeFound("nombrecasparjour.equals=" + DEFAULT_NOMBRECASPARJOUR);

        // Get all the coronaList where nombrecasparjour equals to UPDATED_NOMBRECASPARJOUR
        defaultCoronaShouldNotBeFound("nombrecasparjour.equals=" + UPDATED_NOMBRECASPARJOUR);
    }

    @Test
    @Transactional
    public void getAllCoronasByNombrecasparjourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where nombrecasparjour not equals to DEFAULT_NOMBRECASPARJOUR
        defaultCoronaShouldNotBeFound("nombrecasparjour.notEquals=" + DEFAULT_NOMBRECASPARJOUR);

        // Get all the coronaList where nombrecasparjour not equals to UPDATED_NOMBRECASPARJOUR
        defaultCoronaShouldBeFound("nombrecasparjour.notEquals=" + UPDATED_NOMBRECASPARJOUR);
    }

    @Test
    @Transactional
    public void getAllCoronasByNombrecasparjourIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where nombrecasparjour in DEFAULT_NOMBRECASPARJOUR or UPDATED_NOMBRECASPARJOUR
        defaultCoronaShouldBeFound("nombrecasparjour.in=" + DEFAULT_NOMBRECASPARJOUR + "," + UPDATED_NOMBRECASPARJOUR);

        // Get all the coronaList where nombrecasparjour equals to UPDATED_NOMBRECASPARJOUR
        defaultCoronaShouldNotBeFound("nombrecasparjour.in=" + UPDATED_NOMBRECASPARJOUR);
    }

    @Test
    @Transactional
    public void getAllCoronasByNombrecasparjourIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where nombrecasparjour is not null
        defaultCoronaShouldBeFound("nombrecasparjour.specified=true");

        // Get all the coronaList where nombrecasparjour is null
        defaultCoronaShouldNotBeFound("nombrecasparjour.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByNombrecasparjourContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where nombrecasparjour contains DEFAULT_NOMBRECASPARJOUR
        defaultCoronaShouldBeFound("nombrecasparjour.contains=" + DEFAULT_NOMBRECASPARJOUR);

        // Get all the coronaList where nombrecasparjour contains UPDATED_NOMBRECASPARJOUR
        defaultCoronaShouldNotBeFound("nombrecasparjour.contains=" + UPDATED_NOMBRECASPARJOUR);
    }

    @Test
    @Transactional
    public void getAllCoronasByNombrecasparjourNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where nombrecasparjour does not contain DEFAULT_NOMBRECASPARJOUR
        defaultCoronaShouldNotBeFound("nombrecasparjour.doesNotContain=" + DEFAULT_NOMBRECASPARJOUR);

        // Get all the coronaList where nombrecasparjour does not contain UPDATED_NOMBRECASPARJOUR
        defaultCoronaShouldBeFound("nombrecasparjour.doesNotContain=" + UPDATED_NOMBRECASPARJOUR);
    }


    @Test
    @Transactional
    public void getAllCoronasByCaspositifIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where caspositif equals to DEFAULT_CASPOSITIF
        defaultCoronaShouldBeFound("caspositif.equals=" + DEFAULT_CASPOSITIF);

        // Get all the coronaList where caspositif equals to UPDATED_CASPOSITIF
        defaultCoronaShouldNotBeFound("caspositif.equals=" + UPDATED_CASPOSITIF);
    }

    @Test
    @Transactional
    public void getAllCoronasByCaspositifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where caspositif not equals to DEFAULT_CASPOSITIF
        defaultCoronaShouldNotBeFound("caspositif.notEquals=" + DEFAULT_CASPOSITIF);

        // Get all the coronaList where caspositif not equals to UPDATED_CASPOSITIF
        defaultCoronaShouldBeFound("caspositif.notEquals=" + UPDATED_CASPOSITIF);
    }

    @Test
    @Transactional
    public void getAllCoronasByCaspositifIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where caspositif in DEFAULT_CASPOSITIF or UPDATED_CASPOSITIF
        defaultCoronaShouldBeFound("caspositif.in=" + DEFAULT_CASPOSITIF + "," + UPDATED_CASPOSITIF);

        // Get all the coronaList where caspositif equals to UPDATED_CASPOSITIF
        defaultCoronaShouldNotBeFound("caspositif.in=" + UPDATED_CASPOSITIF);
    }

    @Test
    @Transactional
    public void getAllCoronasByCaspositifIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where caspositif is not null
        defaultCoronaShouldBeFound("caspositif.specified=true");

        // Get all the coronaList where caspositif is null
        defaultCoronaShouldNotBeFound("caspositif.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByCaspositifContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where caspositif contains DEFAULT_CASPOSITIF
        defaultCoronaShouldBeFound("caspositif.contains=" + DEFAULT_CASPOSITIF);

        // Get all the coronaList where caspositif contains UPDATED_CASPOSITIF
        defaultCoronaShouldNotBeFound("caspositif.contains=" + UPDATED_CASPOSITIF);
    }

    @Test
    @Transactional
    public void getAllCoronasByCaspositifNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where caspositif does not contain DEFAULT_CASPOSITIF
        defaultCoronaShouldNotBeFound("caspositif.doesNotContain=" + DEFAULT_CASPOSITIF);

        // Get all the coronaList where caspositif does not contain UPDATED_CASPOSITIF
        defaultCoronaShouldBeFound("caspositif.doesNotContain=" + UPDATED_CASPOSITIF);
    }


    @Test
    @Transactional
    public void getAllCoronasByCascommunautaireIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascommunautaire equals to DEFAULT_CASCOMMUNAUTAIRE
        defaultCoronaShouldBeFound("cascommunautaire.equals=" + DEFAULT_CASCOMMUNAUTAIRE);

        // Get all the coronaList where cascommunautaire equals to UPDATED_CASCOMMUNAUTAIRE
        defaultCoronaShouldNotBeFound("cascommunautaire.equals=" + UPDATED_CASCOMMUNAUTAIRE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascommunautaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascommunautaire not equals to DEFAULT_CASCOMMUNAUTAIRE
        defaultCoronaShouldNotBeFound("cascommunautaire.notEquals=" + DEFAULT_CASCOMMUNAUTAIRE);

        // Get all the coronaList where cascommunautaire not equals to UPDATED_CASCOMMUNAUTAIRE
        defaultCoronaShouldBeFound("cascommunautaire.notEquals=" + UPDATED_CASCOMMUNAUTAIRE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascommunautaireIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascommunautaire in DEFAULT_CASCOMMUNAUTAIRE or UPDATED_CASCOMMUNAUTAIRE
        defaultCoronaShouldBeFound("cascommunautaire.in=" + DEFAULT_CASCOMMUNAUTAIRE + "," + UPDATED_CASCOMMUNAUTAIRE);

        // Get all the coronaList where cascommunautaire equals to UPDATED_CASCOMMUNAUTAIRE
        defaultCoronaShouldNotBeFound("cascommunautaire.in=" + UPDATED_CASCOMMUNAUTAIRE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascommunautaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascommunautaire is not null
        defaultCoronaShouldBeFound("cascommunautaire.specified=true");

        // Get all the coronaList where cascommunautaire is null
        defaultCoronaShouldNotBeFound("cascommunautaire.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByCascommunautaireContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascommunautaire contains DEFAULT_CASCOMMUNAUTAIRE
        defaultCoronaShouldBeFound("cascommunautaire.contains=" + DEFAULT_CASCOMMUNAUTAIRE);

        // Get all the coronaList where cascommunautaire contains UPDATED_CASCOMMUNAUTAIRE
        defaultCoronaShouldNotBeFound("cascommunautaire.contains=" + UPDATED_CASCOMMUNAUTAIRE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascommunautaireNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascommunautaire does not contain DEFAULT_CASCOMMUNAUTAIRE
        defaultCoronaShouldNotBeFound("cascommunautaire.doesNotContain=" + DEFAULT_CASCOMMUNAUTAIRE);

        // Get all the coronaList where cascommunautaire does not contain UPDATED_CASCOMMUNAUTAIRE
        defaultCoronaShouldBeFound("cascommunautaire.doesNotContain=" + UPDATED_CASCOMMUNAUTAIRE);
    }


    @Test
    @Transactional
    public void getAllCoronasByCasgraveIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casgrave equals to DEFAULT_CASGRAVE
        defaultCoronaShouldBeFound("casgrave.equals=" + DEFAULT_CASGRAVE);

        // Get all the coronaList where casgrave equals to UPDATED_CASGRAVE
        defaultCoronaShouldNotBeFound("casgrave.equals=" + UPDATED_CASGRAVE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasgraveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casgrave not equals to DEFAULT_CASGRAVE
        defaultCoronaShouldNotBeFound("casgrave.notEquals=" + DEFAULT_CASGRAVE);

        // Get all the coronaList where casgrave not equals to UPDATED_CASGRAVE
        defaultCoronaShouldBeFound("casgrave.notEquals=" + UPDATED_CASGRAVE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasgraveIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casgrave in DEFAULT_CASGRAVE or UPDATED_CASGRAVE
        defaultCoronaShouldBeFound("casgrave.in=" + DEFAULT_CASGRAVE + "," + UPDATED_CASGRAVE);

        // Get all the coronaList where casgrave equals to UPDATED_CASGRAVE
        defaultCoronaShouldNotBeFound("casgrave.in=" + UPDATED_CASGRAVE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasgraveIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casgrave is not null
        defaultCoronaShouldBeFound("casgrave.specified=true");

        // Get all the coronaList where casgrave is null
        defaultCoronaShouldNotBeFound("casgrave.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByCasgraveContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casgrave contains DEFAULT_CASGRAVE
        defaultCoronaShouldBeFound("casgrave.contains=" + DEFAULT_CASGRAVE);

        // Get all the coronaList where casgrave contains UPDATED_CASGRAVE
        defaultCoronaShouldNotBeFound("casgrave.contains=" + UPDATED_CASGRAVE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasgraveNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casgrave does not contain DEFAULT_CASGRAVE
        defaultCoronaShouldNotBeFound("casgrave.doesNotContain=" + DEFAULT_CASGRAVE);

        // Get all the coronaList where casgrave does not contain UPDATED_CASGRAVE
        defaultCoronaShouldBeFound("casgrave.doesNotContain=" + UPDATED_CASGRAVE);
    }


    @Test
    @Transactional
    public void getAllCoronasByGuerisonIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where guerison equals to DEFAULT_GUERISON
        defaultCoronaShouldBeFound("guerison.equals=" + DEFAULT_GUERISON);

        // Get all the coronaList where guerison equals to UPDATED_GUERISON
        defaultCoronaShouldNotBeFound("guerison.equals=" + UPDATED_GUERISON);
    }

    @Test
    @Transactional
    public void getAllCoronasByGuerisonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where guerison not equals to DEFAULT_GUERISON
        defaultCoronaShouldNotBeFound("guerison.notEquals=" + DEFAULT_GUERISON);

        // Get all the coronaList where guerison not equals to UPDATED_GUERISON
        defaultCoronaShouldBeFound("guerison.notEquals=" + UPDATED_GUERISON);
    }

    @Test
    @Transactional
    public void getAllCoronasByGuerisonIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where guerison in DEFAULT_GUERISON or UPDATED_GUERISON
        defaultCoronaShouldBeFound("guerison.in=" + DEFAULT_GUERISON + "," + UPDATED_GUERISON);

        // Get all the coronaList where guerison equals to UPDATED_GUERISON
        defaultCoronaShouldNotBeFound("guerison.in=" + UPDATED_GUERISON);
    }

    @Test
    @Transactional
    public void getAllCoronasByGuerisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where guerison is not null
        defaultCoronaShouldBeFound("guerison.specified=true");

        // Get all the coronaList where guerison is null
        defaultCoronaShouldNotBeFound("guerison.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByGuerisonContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where guerison contains DEFAULT_GUERISON
        defaultCoronaShouldBeFound("guerison.contains=" + DEFAULT_GUERISON);

        // Get all the coronaList where guerison contains UPDATED_GUERISON
        defaultCoronaShouldNotBeFound("guerison.contains=" + UPDATED_GUERISON);
    }

    @Test
    @Transactional
    public void getAllCoronasByGuerisonNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where guerison does not contain DEFAULT_GUERISON
        defaultCoronaShouldNotBeFound("guerison.doesNotContain=" + DEFAULT_GUERISON);

        // Get all the coronaList where guerison does not contain UPDATED_GUERISON
        defaultCoronaShouldBeFound("guerison.doesNotContain=" + UPDATED_GUERISON);
    }


    @Test
    @Transactional
    public void getAllCoronasByDecesIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where deces equals to DEFAULT_DECES
        defaultCoronaShouldBeFound("deces.equals=" + DEFAULT_DECES);

        // Get all the coronaList where deces equals to UPDATED_DECES
        defaultCoronaShouldNotBeFound("deces.equals=" + UPDATED_DECES);
    }

    @Test
    @Transactional
    public void getAllCoronasByDecesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where deces not equals to DEFAULT_DECES
        defaultCoronaShouldNotBeFound("deces.notEquals=" + DEFAULT_DECES);

        // Get all the coronaList where deces not equals to UPDATED_DECES
        defaultCoronaShouldBeFound("deces.notEquals=" + UPDATED_DECES);
    }

    @Test
    @Transactional
    public void getAllCoronasByDecesIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where deces in DEFAULT_DECES or UPDATED_DECES
        defaultCoronaShouldBeFound("deces.in=" + DEFAULT_DECES + "," + UPDATED_DECES);

        // Get all the coronaList where deces equals to UPDATED_DECES
        defaultCoronaShouldNotBeFound("deces.in=" + UPDATED_DECES);
    }

    @Test
    @Transactional
    public void getAllCoronasByDecesIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where deces is not null
        defaultCoronaShouldBeFound("deces.specified=true");

        // Get all the coronaList where deces is null
        defaultCoronaShouldNotBeFound("deces.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByDecesContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where deces contains DEFAULT_DECES
        defaultCoronaShouldBeFound("deces.contains=" + DEFAULT_DECES);

        // Get all the coronaList where deces contains UPDATED_DECES
        defaultCoronaShouldNotBeFound("deces.contains=" + UPDATED_DECES);
    }

    @Test
    @Transactional
    public void getAllCoronasByDecesNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where deces does not contain DEFAULT_DECES
        defaultCoronaShouldNotBeFound("deces.doesNotContain=" + DEFAULT_DECES);

        // Get all the coronaList where deces does not contain UPDATED_DECES
        defaultCoronaShouldBeFound("deces.doesNotContain=" + UPDATED_DECES);
    }


    @Test
    @Transactional
    public void getAllCoronasByCascontactIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascontact equals to DEFAULT_CASCONTACT
        defaultCoronaShouldBeFound("cascontact.equals=" + DEFAULT_CASCONTACT);

        // Get all the coronaList where cascontact equals to UPDATED_CASCONTACT
        defaultCoronaShouldNotBeFound("cascontact.equals=" + UPDATED_CASCONTACT);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascontactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascontact not equals to DEFAULT_CASCONTACT
        defaultCoronaShouldNotBeFound("cascontact.notEquals=" + DEFAULT_CASCONTACT);

        // Get all the coronaList where cascontact not equals to UPDATED_CASCONTACT
        defaultCoronaShouldBeFound("cascontact.notEquals=" + UPDATED_CASCONTACT);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascontactIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascontact in DEFAULT_CASCONTACT or UPDATED_CASCONTACT
        defaultCoronaShouldBeFound("cascontact.in=" + DEFAULT_CASCONTACT + "," + UPDATED_CASCONTACT);

        // Get all the coronaList where cascontact equals to UPDATED_CASCONTACT
        defaultCoronaShouldNotBeFound("cascontact.in=" + UPDATED_CASCONTACT);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascontactIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascontact is not null
        defaultCoronaShouldBeFound("cascontact.specified=true");

        // Get all the coronaList where cascontact is null
        defaultCoronaShouldNotBeFound("cascontact.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByCascontactContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascontact contains DEFAULT_CASCONTACT
        defaultCoronaShouldBeFound("cascontact.contains=" + DEFAULT_CASCONTACT);

        // Get all the coronaList where cascontact contains UPDATED_CASCONTACT
        defaultCoronaShouldNotBeFound("cascontact.contains=" + UPDATED_CASCONTACT);
    }

    @Test
    @Transactional
    public void getAllCoronasByCascontactNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where cascontact does not contain DEFAULT_CASCONTACT
        defaultCoronaShouldNotBeFound("cascontact.doesNotContain=" + DEFAULT_CASCONTACT);

        // Get all the coronaList where cascontact does not contain UPDATED_CASCONTACT
        defaultCoronaShouldBeFound("cascontact.doesNotContain=" + UPDATED_CASCONTACT);
    }


    @Test
    @Transactional
    public void getAllCoronasByCasimporteIsEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casimporte equals to DEFAULT_CASIMPORTE
        defaultCoronaShouldBeFound("casimporte.equals=" + DEFAULT_CASIMPORTE);

        // Get all the coronaList where casimporte equals to UPDATED_CASIMPORTE
        defaultCoronaShouldNotBeFound("casimporte.equals=" + UPDATED_CASIMPORTE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasimporteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casimporte not equals to DEFAULT_CASIMPORTE
        defaultCoronaShouldNotBeFound("casimporte.notEquals=" + DEFAULT_CASIMPORTE);

        // Get all the coronaList where casimporte not equals to UPDATED_CASIMPORTE
        defaultCoronaShouldBeFound("casimporte.notEquals=" + UPDATED_CASIMPORTE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasimporteIsInShouldWork() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casimporte in DEFAULT_CASIMPORTE or UPDATED_CASIMPORTE
        defaultCoronaShouldBeFound("casimporte.in=" + DEFAULT_CASIMPORTE + "," + UPDATED_CASIMPORTE);

        // Get all the coronaList where casimporte equals to UPDATED_CASIMPORTE
        defaultCoronaShouldNotBeFound("casimporte.in=" + UPDATED_CASIMPORTE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasimporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casimporte is not null
        defaultCoronaShouldBeFound("casimporte.specified=true");

        // Get all the coronaList where casimporte is null
        defaultCoronaShouldNotBeFound("casimporte.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoronasByCasimporteContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casimporte contains DEFAULT_CASIMPORTE
        defaultCoronaShouldBeFound("casimporte.contains=" + DEFAULT_CASIMPORTE);

        // Get all the coronaList where casimporte contains UPDATED_CASIMPORTE
        defaultCoronaShouldNotBeFound("casimporte.contains=" + UPDATED_CASIMPORTE);
    }

    @Test
    @Transactional
    public void getAllCoronasByCasimporteNotContainsSomething() throws Exception {
        // Initialize the database
        coronaRepository.saveAndFlush(corona);

        // Get all the coronaList where casimporte does not contain DEFAULT_CASIMPORTE
        defaultCoronaShouldNotBeFound("casimporte.doesNotContain=" + DEFAULT_CASIMPORTE);

        // Get all the coronaList where casimporte does not contain UPDATED_CASIMPORTE
        defaultCoronaShouldBeFound("casimporte.doesNotContain=" + UPDATED_CASIMPORTE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoronaShouldBeFound(String filter) throws Exception {
        restCoronaMockMvc.perform(get("/api/coronas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrecasparjour").value(hasItem(DEFAULT_NOMBRECASPARJOUR)))
            .andExpect(jsonPath("$.[*].caspositif").value(hasItem(DEFAULT_CASPOSITIF)))
            .andExpect(jsonPath("$.[*].cascommunautaire").value(hasItem(DEFAULT_CASCOMMUNAUTAIRE)))
            .andExpect(jsonPath("$.[*].casgrave").value(hasItem(DEFAULT_CASGRAVE)))
            .andExpect(jsonPath("$.[*].guerison").value(hasItem(DEFAULT_GUERISON)))
            .andExpect(jsonPath("$.[*].deces").value(hasItem(DEFAULT_DECES)))
            .andExpect(jsonPath("$.[*].cascontact").value(hasItem(DEFAULT_CASCONTACT)))
            .andExpect(jsonPath("$.[*].casimporte").value(hasItem(DEFAULT_CASIMPORTE)));

        // Check, that the count call also returns 1
        restCoronaMockMvc.perform(get("/api/coronas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoronaShouldNotBeFound(String filter) throws Exception {
        restCoronaMockMvc.perform(get("/api/coronas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoronaMockMvc.perform(get("/api/coronas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCorona() throws Exception {
        // Get the corona
        restCoronaMockMvc.perform(get("/api/coronas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorona() throws Exception {
        // Initialize the database
        coronaService.save(corona);

        int databaseSizeBeforeUpdate = coronaRepository.findAll().size();

        // Update the corona
        Corona updatedCorona = coronaRepository.findById(corona.getId()).get();
        // Disconnect from session so that the updates on updatedCorona are not directly saved in db
        em.detach(updatedCorona);
        updatedCorona
            .nombrecasparjour(UPDATED_NOMBRECASPARJOUR)
            .caspositif(UPDATED_CASPOSITIF)
            .cascommunautaire(UPDATED_CASCOMMUNAUTAIRE)
            .casgrave(UPDATED_CASGRAVE)
            .guerison(UPDATED_GUERISON)
            .deces(UPDATED_DECES)
            .cascontact(UPDATED_CASCONTACT)
            .casimporte(UPDATED_CASIMPORTE);

        restCoronaMockMvc.perform(put("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorona)))
            .andExpect(status().isOk());

        // Validate the Corona in the database
        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeUpdate);
        Corona testCorona = coronaList.get(coronaList.size() - 1);
        assertThat(testCorona.getNombrecasparjour()).isEqualTo(UPDATED_NOMBRECASPARJOUR);
        assertThat(testCorona.getCaspositif()).isEqualTo(UPDATED_CASPOSITIF);
        assertThat(testCorona.getCascommunautaire()).isEqualTo(UPDATED_CASCOMMUNAUTAIRE);
        assertThat(testCorona.getCasgrave()).isEqualTo(UPDATED_CASGRAVE);
        assertThat(testCorona.getGuerison()).isEqualTo(UPDATED_GUERISON);
        assertThat(testCorona.getDeces()).isEqualTo(UPDATED_DECES);
        assertThat(testCorona.getCascontact()).isEqualTo(UPDATED_CASCONTACT);
        assertThat(testCorona.getCasimporte()).isEqualTo(UPDATED_CASIMPORTE);

        // Validate the Corona in Elasticsearch
        verify(mockCoronaSearchRepository, times(2)).save(testCorona);
    }

    @Test
    @Transactional
    public void updateNonExistingCorona() throws Exception {
        int databaseSizeBeforeUpdate = coronaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoronaMockMvc.perform(put("/api/coronas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corona)))
            .andExpect(status().isBadRequest());

        // Validate the Corona in the database
        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Corona in Elasticsearch
        verify(mockCoronaSearchRepository, times(0)).save(corona);
    }

    @Test
    @Transactional
    public void deleteCorona() throws Exception {
        // Initialize the database
        coronaService.save(corona);

        int databaseSizeBeforeDelete = coronaRepository.findAll().size();

        // Delete the corona
        restCoronaMockMvc.perform(delete("/api/coronas/{id}", corona.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corona> coronaList = coronaRepository.findAll();
        assertThat(coronaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Corona in Elasticsearch
        verify(mockCoronaSearchRepository, times(1)).deleteById(corona.getId());
    }

    @Test
    @Transactional
    public void searchCorona() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        coronaService.save(corona);
        when(mockCoronaSearchRepository.search(queryStringQuery("id:" + corona.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(corona), PageRequest.of(0, 1), 1));

        // Search the corona
        restCoronaMockMvc.perform(get("/api/_search/coronas?query=id:" + corona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrecasparjour").value(hasItem(DEFAULT_NOMBRECASPARJOUR)))
            .andExpect(jsonPath("$.[*].caspositif").value(hasItem(DEFAULT_CASPOSITIF)))
            .andExpect(jsonPath("$.[*].cascommunautaire").value(hasItem(DEFAULT_CASCOMMUNAUTAIRE)))
            .andExpect(jsonPath("$.[*].casgrave").value(hasItem(DEFAULT_CASGRAVE)))
            .andExpect(jsonPath("$.[*].guerison").value(hasItem(DEFAULT_GUERISON)))
            .andExpect(jsonPath("$.[*].deces").value(hasItem(DEFAULT_DECES)))
            .andExpect(jsonPath("$.[*].cascontact").value(hasItem(DEFAULT_CASCONTACT)))
            .andExpect(jsonPath("$.[*].casimporte").value(hasItem(DEFAULT_CASIMPORTE)));
    }
}
