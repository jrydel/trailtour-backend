package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
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
        List<Map<String, Object>> results = selectList(
                "SELECT " +
                        "a.id AS activity_id, " +
                        "a.position AS activity_position, " +
                        "a.time AS activity_time, " +
                        "a.created AS activity_created, " +
                        "b.id AS athlete_id, " +
                        "b.name AS athlete_name, " +
                        "b.club_name AS club_name, " +
                        "c.number AS stage_number, " +
                        "c.name AS stage_name, " +
                        "d.points AS points," +
                        "d.trailtour_points AS trailtour_points, " +
                        "e.id AS club_id " +
                        "FROM " + database + ".activity a " +
                        "JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "JOIN " + database + ".stage c ON c.number = a.stage_number " +
                        "LEFT JOIN " + database + ".athlete_result d ON d.athlete_id = b.id AND d.stage_number = c.number AND d.timestamp = ? " +
                        "LEFT JOIN " + database + ".club e ON e.name = b.club_name " +
                        "ORDER BY a.created DESC LIMIT ? OFFSET ?",
                new Object[]{Timestamp.valueOf(lastUpdate), limit, offset},
                MysqlRepository::loadResultSet
        );

        Map<String, Object> map = new HashMap<>();
        map.put("lastUpdate", lastUpdate.format(DateTimeFormatter.ISO_DATE_TIME));
        map.put("count", count);
        map.put("data", results);
        return map;
    }

    public List<Map<String, Object>> getAthleteLadder(String database) throws SQLException {
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT a.id AS athlete_id, a.name AS athlete_name, a.gender AS athlete_gender, b.id AS club_id, b.name AS club_name, c.trailtour_points AS trailtour_points, c.trailtour_position AS trailtour_position FROM trailtour.athlete a " +
                        "LEFT JOIN trailtour.club b ON a.club_name = b.name AND b.status = ? " +
                        "LEFT JOIN trailtour.athlete_ladder c ON a.id = c.athlete_id AND c.timestamp = ? " +
                        "WHERE a.status = ?",
                new Object[]{"enabled", java.sql.Timestamp.valueOf(lastUpdate), "enabled"},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getClubLadder(String database) throws SQLException {
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT a.id AS club_id, a.name AS club_name, b.trailtour_points AS trailtour_points, b.trailtour_position AS trailtour_position FROM trailtour.club a " +
                        "LEFT JOIN trailtour.club_ladder b ON a.id = b.club_id AND b.timestamp = ? " +
                        "WHERE a.status = ?",
                new Object[]{java.sql.Timestamp.valueOf(lastUpdate), "enabled"},
                MysqlRepository::loadResultSet
        );
    }
}
