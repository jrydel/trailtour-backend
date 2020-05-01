package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.*;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import cz.jr.trailtour.backend.repository.mysql.Param;
import cz.jr.trailtour.backend.repository.mysql.UpsertParam;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                new Param("stage_id"),
                new Param("athlete_id"),
                new UpsertParam("activity_id"),
                new UpsertParam("position"),
                new UpsertParam("date"),
                new UpsertParam("time")
        }), new Object[]{result.getStageId(), result.getAthleteId(), result.getActivityId(), result.getPosition(), result.getDate().format(DateTimeFormatter.ISO_DATE), result.getTime()});
    }

    public List<ResultWeb> getResultsWeb(long stageId) throws SQLException {
        return selectList("SELECT a.activity_id, a.date, a.time, a.position, b.id, b.name, b.gender, c.id, c.name FROM trailtour.result a JOIN trailtour.athlete b ON a.athlete_id = b.id LEFT JOIN trailtour.club c on c.id = b.club_id WHERE a.stage_id = ?", new Object[]{stageId}, rs -> {
            ResultWeb resultWeb = new ResultWeb();
            resultWeb.setStageId(stageId);
            resultWeb.setActivityId(rs.getLong("a.activity_id"));
            resultWeb.setDate(LocalDate.parse(rs.getString("a.date"), DateTimeFormatter.ISO_DATE));
            resultWeb.setTime(rs.getLong("a.time"));
            resultWeb.setPosition(rs.getInt("a.position"));

            AthleteWeb athlete = new AthleteWeb();
            athlete.setId(rs.getLong("b.id"));
            athlete.setName(rs.getString("b.name"));
            athlete.setGender(Athlete.Gender.valueOf(rs.getString("b.gender")));
            resultWeb.setAthlete(athlete);

            long clubId = rs.getLong("c.id");
            if (!rs.wasNull()) {
                Club club = new Club();
                club.setId(clubId);
                club.setName(rs.getString("c.name"));
                athlete.setClub(club);
            }

            return resultWeb;
        });
    }
}
