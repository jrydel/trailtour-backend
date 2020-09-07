package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result d WHERE d.athlete_id = a.id AND d.timestamp = ?) AS stages_count, " +
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

    public List<Map<String, Object>> getResults(String database, long id) throws SQLException {
        return selectList(
                "SELECT " +
                        "stage_number AS stage_number, " +
                        "stage_name AS stage_name, " +
                        "activity_id AS activity_id, " +
                        "activity_time AS activity_time, " +
                        "activity_date AS activity_date, " +
                        "position AS position, " +
                        "points AS points, " +
                        "trailtour_position AS trailtour_position, " +
                        "trailtour_points AS trailtour_points, " +
                        "trailtour_time AS trailtour_time " +
                        "FROM " + database + ".athlete_data WHERE athlete_id = ?",
                new Object[]{id},
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

    public List<Map<String, Object>> getLadder(String database, String gender) throws SQLException {
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT " +
                        "a.id AS athlete_id, " +
                        "a.name AS athlete_name, " +
                        "b.id AS club_id, " +
                        "b.name AS club_name, " +
                        "c.position AS position, " +
                        "c.points AS points, " +
                        "c.trailtour_position AS trailtour_position, " +
                        "c.trailtour_points AS trailtour_points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result d WHERE d.athlete_id = a.id AND d.trailtour_points IS NOT NULL AND d.timestamp = ?) AS stage_count " +
                        "FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".club b ON b.name = a.club_name " +
                        "LEFT JOIN " + database + ".athlete_ladder c ON c.athlete_id = a.id AND c.timestamp = ? " +
                        "WHERE a.gender = ? GROUP BY a.id",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastUpdate),
                        java.sql.Timestamp.valueOf(lastUpdate),
                        gender
                },
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getHistory(String database, List<Long> ids) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Long id : ids) {
            Map<String, Object> map = selectObject(
                    "SELECT " +
                            "a.id AS athlete_id, " +
                            "a.name AS athlete_name, " +
                            "a.gender AS athlete_gender, " +
                            "b.id AS club_id, " +
                            "b.name AS club_name " +
                            "FROM " + database + ".athlete a " +
                            "LEFT JOIN " + database + ".club b ON a.club_name = b.name WHERE a.id = ?",
                    new Object[]{id},
                    MysqlRepository::loadResultSet
            );
            List<Map<String, Object>> map2 = selectList(
                    "SELECT " +
                            "a.position AS position, " +
                            "a.points AS points, " +
                            "a.trailtour_position AS trailtour_position, " +
                            "a.trailtour_points AS trailtour_points, " +
                            "a.timestamp AS timestamp " +
                            "FROM " + database + ".athlete_ladder a " +
                            "WHERE a.athlete_id = ? AND a.timestamp = (SELECT MAX(x.timestamp) FROM " + database + ".athlete_ladder x WHERE DATE(x.timestamp) = DATE(a.timestamp) AND x.athlete_id = a.athlete_id)",
                    new Object[]{id},
                    MysqlRepository::loadResultSet
            );
            map.put("data", map2);
            result.add(map);
        }
        return result;
    }
}
