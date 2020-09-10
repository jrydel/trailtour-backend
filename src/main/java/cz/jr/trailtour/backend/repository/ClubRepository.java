package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/21/20, 1:33 PM
 */
@Repository
public class ClubRepository extends BaseRepository {

    public ClubRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Map<String, Object> get(String database, long id) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectObject("SELECT " +
                        "a.name AS club_name, " +
                        "b.position AS position, " +
                        "b.points AS points, " +
                        "b.trailtour_position AS trailtour_position, " +
                        "b.trailtour_points AS trailtour_points," +
                        "(SELECT COUNT(c.id) FROM " + database + ".athlete c WHERE c.club_name = club_name) AS athletes_count " +
                        "FROM " + database + ".club a " +
                        "LEFT JOIN " + database + ".club_ladder b ON b.club_name = a.name AND b.timestamp = ? WHERE a.id = ?",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate),
                        id
                },
                rs -> {
                    String clubName = rs.getString("a.name");

                    Integer position = rs.getObject("b.position", Integer.class);
                    Double points = rs.getObject("b.points", Double.class);
                    Integer trailtourPosition = rs.getObject("b.trailtour_position", Integer.class);
                    Double trailtourPoints = rs.getObject("b.trailtour_points", Double.class);

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", id);
                    map.put("name", clubName);
                    map.put("position", position);
                    map.put("points", points);
                    map.put("trailtourPosition", trailtourPosition);
                    map.put("trailtourPoints", trailtourPoints);

                    return map;
                });
    }

    public List<Map<String, Object>> getAll(String database) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        select("SELECT a.id, a.name, b.position, b.points, b.trailtour_position, b.trailtour_points FROM " + database + ".club a " +
                        "LEFT JOIN " + database + ".club_ladder b ON b.club_name = a.name AND b.timestamp = ?",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate)
                },
                rs -> {
                    while (rs.next()) {
                        long clubId = rs.getLong("a.id");
                        String clubName = rs.getString("a.name");

                        Integer position = rs.getObject("b.position", Integer.class);
                        Double points = rs.getObject("b.points", Double.class);
                        Integer trailtourPosition = rs.getObject("b.trailtour_position", Integer.class);
                        Double trailtourPoints = rs.getObject("b.trailtour_points", Double.class);

                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put("id", clubId);
                        map.put("name", clubName);
                        map.put("position", position);
                        map.put("points", points);
                        map.put("trailtourPosition", trailtourPosition);
                        map.put("trailtourPoints", trailtourPoints);

                        result.add(map);
                    }
                    return null;
                });

        return result;
    }

    public List<Map<String, Object>> getLadder(String database) throws SQLException {
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT " +
                        "a.id AS club_id, " +
                        "a.name AS club_name, " +
                        "b.position AS position, " +
                        "b.points AS points, " +
                        "b.trailtour_position AS trailtour_position, " +
                        "b.trailtour_points AS trailtour_points " +
                        "FROM " + database + ".club a " +
                        "JOIN " + database + ".club_ladder b ON b.club_name = a.name AND b.timestamp = ?",
                new Object[]{java.sql.Timestamp.valueOf(lastUpdate)},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getAllAthletes(String database, long id) throws SQLException {
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT " +
                        "b.id AS athlete_id, " +
                        "b.name AS athlete_name, " +
                        "b.gender AS athlete_gender, " +
                        "c.trailtour_points, " +
                        "c.trailtour_position, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result d WHERE d.athlete_id = b.id AND d.timestamp = ? AND d.trailtour_points IS NOT NULL) AS trailtour_stages_count " +
                        "FROM trailtour.club a " +
                        "LEFT JOIN trailtour.athlete b ON a.name = b.club_name " +
                        "LEFT JOIN trailtour.athlete_ladder c ON c.athlete_id = b.id AND c.timestamp = ? " +
                        "WHERE a.id = ?",
                new Object[]{java.sql.Timestamp.valueOf(lastUpdate), java.sql.Timestamp.valueOf(lastUpdate), id},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getFulltext(String database, String match) throws SQLException {
        return selectList("SELECT a.id, a.name FROM " + database + ".club a WHERE a.id LIKE ? OR a.name LIKE ? LIMIT 10", new Object[]{"%" + match + "%", "%" + match + "%"}, rs -> {
            long clubId = rs.getLong("a.id");
            String clubName = rs.getString("a.name");

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", clubId);
            map.put("name", clubName);
            return map;
        });
    }
}
