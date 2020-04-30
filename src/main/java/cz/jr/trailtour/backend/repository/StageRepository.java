package cz.jr.trailtour.backend.repository;

import cz.jr.trailtour.backend.repository.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:21 PM
 */
@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

}
