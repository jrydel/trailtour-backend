package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
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
        return selectObject("SELECT a.name, a.gender, b.id, b.name, c.position, c.points, c.trailtour_position, c.trailtour_points FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".club b ON b.name = a.club_name " +
                        "LEFT JOIN " + database + ".athlete_ladder c ON c.athlete_id = a.id AND c.timestamp = ? WHERE a.id = ?",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate),
                        id
                },
                rs -> {
                    String athleteName = rs.getString("a.name");
                    String athleteGender = rs.getString("a.gender");

                    long clubId = rs.getLong("b.id");
                    String clubName = rs.getString("b.name");

                    Integer position = rs.getObject("c.position", Integer.class);
                    Double points = rs.getObject("c.points", Double.class);
                    Integer trailtourPosition = rs.getObject("c.trailtour_position", Integer.class);
                    Double trailtourPoints = rs.getObject("c.trailtour_points", Double.class);

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", id);
                    map.put("name", athleteName);
                    map.put("club_id", clubId);
                    map.put("club_name", clubName);
                    map.put("gender", athleteGender);
                    map.put("position", position);
                    map.put("points", points);
                    map.put("trailtour_position", trailtourPosition);
                    map.put("trailtour_points", trailtourPoints);

                    return map;
                });
    }

    public Map<String, List<Map<String, Object>>> getAll(String database) throws SQLException {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        select("SELECT a.id, a.name, a.club_name, a.gender, b.position, b.points, b.trailtour_position, b.trailtour_points FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".athlete_ladder b ON b.athlete_id = a.id AND b.timestamp = ?",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate)
                },
                rs -> {
                    while (rs.next()) {
                        long athleteId = rs.getLong("a.id");
                        String athleteName = rs.getString("a.name");
                        String ahtleteClub = rs.getString("a.club_name");
                        String athleteGender = rs.getString("a.gender");

                        Integer position = rs.getObject("b.position", Integer.class);
                        Double points = rs.getObject("b.points", Double.class);
                        Integer trailtourPosition = rs.getObject("b.trailtour_position", Integer.class);
                        Double trailtourPoints = rs.getObject("b.trailtour_points", Double.class);

                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put("id", athleteId);
                        map.put("name", athleteName);
                        map.put("club", ahtleteClub);
                        map.put("gender", athleteGender);
                        map.put("position", position);
                        map.put("points", points);
                        map.put("trailtourPosition", trailtourPosition);
                        map.put("trailtourPoints", trailtourPoints);

                        result.computeIfAbsent(athleteGender, k -> new ArrayList<>()).add(map);
                    }
                    return null;
                });

        return result;
    }

    public List<Map<String, Object>> getFulltext(String database, String match) throws SQLException {
        return selectList("SELECT a.id, a.name, a.gender, a.club_name FROM " + database + ".athlete a WHERE a.id LIKE ? OR a.name LIKE ? LIMIT 10", new Object[]{"%" + match + "%", "%" + match + "%"}, rs -> {
            long athleteId = rs.getLong("a.id");
            String athleteName = rs.getString("a.name");
            String ahtleteClub = rs.getString("a.club_name");
            String athleteGender = rs.getString("a.gender");

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", athleteId);
            map.put("name", athleteName);
            map.put("club", ahtleteClub);
            map.put("gemder", athleteGender);
            return map;
        });
    }

    public List<Map<String, Object>> getResults(String database, long id) throws SQLException {
        return selectList(
                "SELECT stage_number, stage_name, activity_id, activity_time, activity_date, position, points, trailtour_position, trailtour_points FROM " + database + ".athlete_data WHERE athlete_id = ?",
                new Object[]{id,},
                rs -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("stage_number", rs.getObject("stage_number"));
                    result.put("stage_name", rs.getObject("stage_name"));
                    result.put("activity_id", rs.getObject("activity_id"));
                    result.put("activity_time", rs.getObject("activity_time"));
                    result.put("activity_date", rs.getObject("activity_date"));
                    result.put("position", rs.getObject("position"));
                    result.put("points", rs.getObject("points"));
                    result.put("trailtour_position", rs.getObject("trailtour_position"));
                    result.put("trailtour_points", rs.getObject("trailtour_points"));
                    return result;
                });
    }

}
