package sn.isi.m2gl.repository;

import sn.isi.m2gl.domain.Corona;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Corona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoronaRepository extends JpaRepository<Corona, Long>, JpaSpecificationExecutor<Corona> {
}
