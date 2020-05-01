package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.entity.Club;
import cz.jr.trailtour.backend.repository.entity.Result;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
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

    public List<Result> getResults(long stageId) throws SQLException {
        return selectList("SELECT a.activity_id, a.date, a.time, a.position, a.points_strava, b.id, b.name, b.gender, c.id, c.name FROM trailtour.result a JOIN trailtour.athlete b ON a.athlete_id = b.id LEFT JOIN trailtour.club c on c.id = b.club_id WHERE a.stage_id = ?", new Object[]{stageId}, rs -> {
            Result result = new Result();
            result.setStageId(stageId);
            result.setActivityId(rs.getLong("a.activity_id"));
            result.setDate(LocalDate.parse(rs.getString("a.date"), DateTimeFormatter.ISO_DATE));
            result.setTime(rs.getInt("a.time"));
            result.setPosition(rs.getInt("a.position"));
            result.setPointsStrava(rs.getDouble("a.points_strava"));

            Athlete athlete = new Athlete();
            athlete.setId(rs.getLong("b.id"));
            athlete.setName(rs.getString("b.name"));
            athlete.setGender(Athlete.Gender.valueOf(rs.getString("b.gender")));
            result.setAthlete(athlete);

            long clubId = rs.getLong("c.id");
            if (!rs.wasNull()) {
                Club club = new Club();
                club.setId(clubId);
                club.setName(rs.getString("c.name"));
                athlete.setClub(club);
            }

            return result;
        });
    }
}
