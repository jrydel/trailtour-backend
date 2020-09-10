package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:44 PM
 */
@Repository
public class AthleteRepository extends BaseRepository {

    public AthleteRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Map<String, Object> get(String database, long id) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectObject("SELECT " +
                        "a.id AS athlete_id, " +
                        "a.name AS athlete_name, " +
                        "a.gender AS athlete_gender, " +
                        "b.id AS club_id, " +
                        "b.name AS club_name, " +
                        "c.position AS position, " +
                        "c.points AS points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result d WHERE d.athlete_id = a.id AND d.timestamp = ? AND d.points IS NOT NULL) AS stages_count, " +
                        "c.trailtour_position AS trailtour_position, " +
                        "c.trailtour_points AS trailtour_points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result e WHERE e.athlete_id = a.id AND e.timestamp = ? AND e.trailtour_points IS NOT NULL) AS trailtour_stages_count " +
                        "FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".club b ON b.name = a.club_name " +
                        "LEFT JOIN " + database + ".athlete_ladder c ON c.athlete_id = a.id AND c.timestamp = ? " +
                        "WHERE a.id = ?",
                new Object[]{java.sql.Timestamp.valueOf(lastResultUpdate), java.sql.Timestamp.valueOf(lastResultUpdate), java.sql.Timestamp.valueOf(lastResultUpdate), id},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getAll(String database, String gender) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectList("SELECT " +
                        "a.id AS athlete_id, " +
                        "a.name AS athlete_name, " +
                        "a.gender AS athlete_gender, " +
                        "b.id AS club_id, " +
                        "b.name AS club_name, " +
                        "c.position AS position, " +
                        "c.points AS points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result d WHERE d.athlete_id = a.id AND d.timestamp = ?) AS stages_count, " +
                        "c.trailtour_position AS trailtour_position, " +
                        "c.trailtour_points AS trailtour_points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result e WHERE e.athlete_id = a.id AND e.timestamp = ? AND e.trailtour_points IS NOT NULL) AS trailtour_stages_count " +
                        "FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".club b ON b.name = a.club_name " +
                        "LEFT JOIN " + database + ".athlete_ladder c ON c.athlete_id = a.id AND c.timestamp = ? " +
                        "WHERE a.gender = ?",
                new Object[]{java.sql.Timestamp.valueOf(lastResultUpdate), java.sql.Timestamp.valueOf(lastResultUpdate), java.sql.Timestamp.valueOf(lastResultUpdate), gender},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getResults(String database, long id) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT " +
                        "d.number AS stage_number, " +
                        "d.name AS stage_name, " +
                        "b.id AS athlete_id, " +
                        "b.name AS athlete_name, " +
                        "b.gender AS athlete_gender, " +
                        "c.id AS club_id, " +
                        "c.name AS club_name, " +
                        "a.position AS position, " +
                        "a.points AS points, " +
                        "a.trailtour_position AS trailtour_position, " +
                        "a.trailtour_points AS trailtour_points, " +
                        "a.trailtour_time AS trailtour_time, " +
                        "e.id AS activity_id, " +
                        "e.time AS activity_time, " +
                        "e.date AS activity_date " +
                        "FROM " + database + ".athlete_result a " +
                        "JOIN " + database + ".athlete b ON a.athlete_id = b.id AND b.status = ? AND b.id = ? " +
                        "LEFT JOIN " + database + ".club c ON c.name = b.club_name AND c.status = ? " +
                        "JOIN " + database + ".stage d ON d.number = a.stage_number " +
                        "LEFT JOIN " + database + ".activity e ON e.athlete_id = b.id AND e.stage_number = d.number " +
                        "WHERE a.timestamp = ?",
                new Object[]{"enabled", id, "enabled", java.sql.Timestamp.valueOf(lastResultUpdate)},
                MysqlRepository::loadResultSet
        );
    }

    public Map<Integer, Map<String, Object>> getKomResults(String database) throws SQLException {
        Map<Integer, Map<String, Object>> result = new LinkedHashMap<>();
        select("SELECT " +
                        "stage_number AS stage_number, " +
                        "athlete_id AS athlete_id, " +
                        "athlete_name AS athlete_name, " +
                        "athlete_gender AS athlete_gender, " +
                        "club_id AS club_id, " +
                        "club_name AS club_name, " +
                        "activity_id AS activity_id, " +
                        "activity_time AS activity_time " +
                        "FROM " + database + ".athlete_data " +
                        "WHERE position = 1", new Object[]{},
                rs -> {
                    while (rs.next()) {
                        int stageNumber = rs.getInt("stage_number");
                        long athleteId = rs.getLong("athlete_id");
                        String athleteName = rs.getString("athlete_name");
                        String athleteGender = rs.getString("athlete_gender");
                        long clubId = rs.getLong("club_id");
                        String clubName = rs.getString("club_name");
                        long activityId = rs.getLong("activity_id");
                        int activityTime = rs.getInt("activity_time");

                        Map<String, Object> temp = new HashMap<>();
                        temp.put("athlete_id", athleteId);
                        temp.put("athlete_name", athleteName);
                        temp.put("club_id", clubId);
                        temp.put("club_name", clubName);
                        temp.put("activity_id", activityId);
                        temp.put("activity_time", activityTime);

                        result.computeIfAbsent(stageNumber, k -> new HashMap<>()).put(athleteGender, temp);
                    }
                    return null;
                });
        return result;
    }

    public List<Map<String, Object>> getFulltext(String database, String match) throws SQLException {
        return selectList("SELECT " +
                        "a.id AS athlete_id, " +
                        "a.name AS athlete_name, " +
                        "a.gender AS athlete_gender, " +
                        "b.id AS club_id, " +
                        "b.name AS club_name " +
                        "FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".club b ON b.name = a.club_name " +
                        "WHERE a.id LIKE ? OR a.name LIKE ? LIMIT 10",
                new Object[]{"%" + match + "%", "%" + match + "%"},
                MysqlRepository::loadResultSet
        );
    }
}
