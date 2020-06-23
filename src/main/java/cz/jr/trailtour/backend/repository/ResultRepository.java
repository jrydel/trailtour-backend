package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.Activity;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.entities.feed.FeedResult;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:15 PM
 */
@Repository
public class ResultRepository extends BaseRepository {

    public ResultRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Map<String, Object> getFeed(String database, int limit, int offset) throws SQLException {
        int count = selectObject("SELECT COUNT(*) AS count FROM " + database + ".activity", new Object[]{}, rs -> rs.getInt("count"));
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        List<FeedResult> results = selectList(
                "SELECT " +
                        "a.id, " +
                        "a.position, " +
                        "a.time, " +
                        "a.created, " +
                        "b.id, " +
                        "b.name, " +
                        "b.club_name, " +
                        "c.number, " +
                        "c.name " +
                        "FROM " + database + ".activity a " +
                        "JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "JOIN " + database + ".stage c ON c.number = a.stage_number " +
                        "ORDER BY a.created DESC LIMIT ? OFFSET ?", new Object[]{limit, offset}, rs -> {
                    FeedResult result = new FeedResult();

                    Activity activity = new Activity();
                    activity.setId(rs.getLong("a.id"));
                    activity.setPosition(rs.getInt("a.position"));
                    activity.setTime(rs.getInt("a.time"));
                    activity.setCreated(rs.getTimestamp("a.created").toLocalDateTime());
                    result.setActivity(activity);

                    Stage stage = new Stage();
                    stage.setNumber(rs.getInt("c.number"));
                    stage.setName(rs.getString("c.name"));
                    result.setStage(stage);

                    Athlete athlete = new Athlete();
                    athlete.setId(rs.getLong("b.id"));
                    athlete.setName(rs.getString("b.name"));
                    athlete.setClub(rs.getString("b.club_name"));
                    result.setAthlete(athlete);

                    return result;
                });

        Map<String, Object> map = new HashMap<>();
        map.put("lastUpdate", lastUpdate.format(DateTimeFormatter.ISO_DATE_TIME));
        map.put("count", count);
        map.put("data", results);
        return map;
    }


}
