package sn.isi.m2gl.repository;

import sn.isi.m2gl.domain.Covid;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Covid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CovidRepository extends JpaRepository<Covid, Long> {
}
