package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.entities.stage.StageInfo;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:21 PM
 */
@Repository
public class StageRepository extends BaseRepository {

    public StageRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Map<String, Object> get(String database, int number) throws SQLException {
        return selectObject(
                "SELECT name, distance, type, elevation, trailtour_url, strava_url, mapycz_url, rating_sum, rating_votes FROM " + database + ".stage WHERE number = ?",
                new Object[]{number},
                MysqlRepository::loadResultSet
        );
    }

    public Map<String, Object> getCounts(String database, int number) throws SQLException {
        Map<String, Object> result = new LinkedHashMap<>();

        int activityCount = selectObject(
                "SELECT COUNT(*) as activities FROM " + database + ".activity a WHERE a.stage_number = ? AND a.date = (SELECT MAX(x.date) FROM " + database + ".activity x WHERE x.stage_number = a.stage_number AND x.athlete_id = a.athlete_id)",
                new Object[]{number},
                rs -> rs.getInt("activities")
        );
        result.put("activityCount", activityCount);

        int infoCount = selectObject("SELECT COUNT(*) as infos FROM " + database + ".stage_info WHERE stage_number = ?",
                new Object[]{number},
                rs -> rs.getInt("infos")
        );
        result.put("infoCount", infoCount);

        return result;
    }

    public String getGPSData(String database, int number) throws SQLException {
        return selectObject("SELECT strava_data FROM " + database + ".stage WHERE number = ?", new Object[]{number}, rs -> rs.getString("strava_data"));
    }

    public Map<String, Object> getGPSStart(String database, int number) throws SQLException {
        return selectObject(
                "SELECT " +
                        "JSON_EXTRACT(strava_data , '$.latlng[0][0]') AS latitude, " +
                        "JSON_EXTRACT(strava_data , '$.latlng[0][1]') AS longitude " +
                        "FROM " + database + ".stage WHERE number = ?",
                new Object[]{number},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getInfos(String database, int number) throws SQLException {
        return selectList("SELECT " +
                        "author AS author, " +
                        "title AS title, " +
                        "content AS content, " +
                        "created AS created " +
                        "FROM " + database + ".stage_info WHERE stage_number = ?",
                new Object[]{number},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getAll(String database) throws SQLException {
        return selectList("SELECT " +
                        "number AS number, " +
                        "name AS name, " +
                        "distance AS distance, " +
                        "elevation AS elevation, " +
                        "type AS type, " +
                        "trailtour_url AS trailtour_url, " +
                        "strava_url AS strava_url, " +
                        "mapycz_url AS mapycz_url, " +
                        "rating_sum AS rating_sum, " +
                        "rating_votes AS rating_votes" +
                        " FROM " + database + ".stage",
                new Object[]{},
                MysqlRepository::loadResultSet
        );
    }

    public Map<Integer, Map<String, Object>> getAllCounts(String database) throws SQLException {
        Map<Integer, Map<String, Object>> result = new HashMap<>();

        select(
                "SELECT a.number, COUNT(b.id) as activities FROM " + database + ".stage a JOIN " + database + ".activity b ON a.number = b.stage_number AND b.date = (SELECT MAX(x.date) FROM " + database + ".activity x WHERE x.stage_number = b.stage_number AND x.athlete_id = b.athlete_id) GROUP BY a.number",
                new Object[]{},
                rs -> {
                    while (rs.next()) {
                        int stageNumber = rs.getInt("a.number");
                        int activityCount = rs.getInt("activities");

                        result.computeIfAbsent(stageNumber, k -> new HashMap<>()).put("activityCount", activityCount);
                    }
                    return null;
                }
        );

        select("SELECT a.number, COUNT(b.id) as infos FROM " + database + ".stage a LEFT JOIN " + database + ".stage_info b ON a.number = b.stage_number GROUP BY a.number",
                new Object[]{},
                rs -> {
                    while (rs.next()) {
                        int stageNumber = rs.getInt("a.number");
                        int infoCount = rs.getInt("infos");

                        result.computeIfAbsent(stageNumber, k -> new HashMap<>()).put("infoCount", infoCount);
                    }
                    return null;
                }
        );

        return result;
    }

    public Map<Integer, String> getAllGPSData(String database) throws SQLException {
        Map<Integer, String> result = new HashMap<>();
        select("SELECT number, strava_data FROM " + database + ".stage",
                new Object[]{},
                rs -> {
                    while (rs.next()) {
                        int stageNumber = rs.getInt("number");
                        String data = rs.getString("strava_data");
                        result.put(stageNumber, data);
                    }
                    return null;
                });
        return result;
    }

    public List<Map<String, Object>> getAllGPSStart(String database) throws SQLException {
        return selectList("SELECT " +
                        "number AS stage_number, " +
                        "name AS name, " +
                        "JSON_EXTRACT(strava_data , '$.latlng[0][0]') AS latitude, " +
                        "JSON_EXTRACT(strava_data , '$.latlng[0][1]') AS longitude " +
                        "FROM " + database + ".stage",
                new Object[]{},
                MysqlRepository::loadResultSet
        );
    }

    public List<Map<String, Object>> getResults(String database, int number, String gender) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectList("SELECT " +
                        "b.id AS athlete_id, " +
                        "b.name AS athlete_name, " +
                        "b.gender AS athlete_gender, " +
                        "c.id AS club_id, " +
                        "c.name AS club_name, " +
                        "a.position AS position, " +
                        "a.points AS points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result d WHERE d.athlete_id = b.id AND d.timestamp = ?) AS stages_count, " +
                        "a.trailtour_position AS trailtour_position, " +
                        "a.trailtour_points AS trailtour_points, " +
                        "(SELECT COUNT(*) FROM " + database + ".athlete_result e WHERE e.athlete_id = b.id AND e.timestamp = ? AND e.trailtour_points IS NOT NULL) AS trailtour_stages_count ," +
                        "f.id AS activity_id, " +
                        "f.time AS activity_time, " +
                        "f.date AS activity_date " +
                        "FROM " + database + ".athlete_result a " +
                        "JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "LEFT JOIN " + database + ".club c ON c.name = b.club_name " +
                        "LEFT JOIN " + database + ".activity f ON a.stage_number = f.stage_number AND b.id = f.athlete_id " +
                        "WHERE b.gender = ? AND a.stage_number = ? AND a.timestamp = ?",
                new Object[]{java.sql.Timestamp.valueOf(lastResultUpdate), java.sql.Timestamp.valueOf(lastResultUpdate), gender, number, java.sql.Timestamp.valueOf(lastResultUpdate)},
                MysqlRepository::loadResultSet
        );
    }

    public Map<String, Integer> getResultsCounts(String database, int stageNumber) throws SQLException {
        Map<String, Integer> result = new LinkedHashMap<>();
        select("SELECT athlete_gender, COUNT(*) as count FROM " + database + ".athlete_data WHERE stage_number = ? GROUP BY athlete_gender",
                new Object[]{stageNumber},
                rs -> {
                    while (rs.next()) {
                        String gender = rs.getString("athlete_gender");
                        int count = rs.getInt("count");

                        result.put(gender, count);
                    }
                    return null;
                });

        return result;
    }

    public List<Stage> getFulltext(String database, String match) throws SQLException {
        return selectList("SELECT a.number, a.name, a.type,a.distance, a.elevation, a.trailtour_url, a.strava_url, a.mapycz_url FROM " + database + ".stage a WHERE a.number LIKE ? OR a.name LIKE ? LIMIT 10", new Object[]{"%" + match + "%", "%" + match + "%"}, rs -> {
            Stage stage = new Stage();
            stage.setNumber(rs.getInt("a.number"));
            stage.setName(rs.getString("a.name"));
            stage.setDistance(rs.getInt("a.distance"));
            stage.setElevation(rs.getInt("a.elevation"));
            stage.setType(rs.getString("a.type"));
            stage.setTrailtourUrl(rs.getString("a.trailtour_url"));
            stage.setStravaUrl(rs.getString("a.strava_url"));
            stage.setMapyczUrl(rs.getString("a.mapycz_url"));
            return stage;
        });
    }

    public int saveInfo(String database, StageInfo stageInfo) throws SQLException {
        return execute("INSERT INTO " + database + ".stage_info (author, content) VALUES (?, ?)", new Object[]{stageInfo.getAuthor(), stageInfo.getContent()});
    }

    public int saveRating(String database, double rating, int stageNumber) throws SQLException {
        return execute("UPDATE " + database + ".stage SET rating_sum = rating_sum + ?, rating_votes = rating_votes + 1 WHERE number = ?", new Object[]{rating, stageNumber});
    }
}
