package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.Result;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import cz.jr.trailtour.backend.repository.mysql.Param;
import cz.jr.trailtour.backend.repository.mysql.UpsertParam;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:15 PM
 */
@Repository
public class ResultRepository extends MysqlRepository {

    public ResultRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public void saveResult(Result result) throws SQLException {
        execute(generateUpsert("trailtour.result", new Param[]{
                new Param("segment_id"),
                new Param("athlete_id"),
                new UpsertParam("activity_id"),
                new UpsertParam("position"),
                new UpsertParam("date"),
                new UpsertParam("time")
        }), new Object[]{result.getSegmentId(), result.getAthleteId(), result.getActivityId(), result.getPosition(), result.getDate().format(DateTimeFormatter.ISO_DATE), result.getTime()});
    }
}
