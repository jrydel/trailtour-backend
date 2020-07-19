package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.entities.stage.StageInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
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

    public Stage get(String database, int number) throws SQLException {
        return selectObject("SELECT name, distance, type, elevation, trailtour_url, strava_url, mapycz_url FROM " + database + ".stage WHERE number = ?",
                new Object[]{number},
                rs -> {
                    Stage temp = new Stage();
                    temp.setNumber(number);
                    temp.setName(rs.getString("name"));
                    temp.setType(rs.getString("type"));
                    temp.setDistance(rs.getInt("distance"));
                    temp.setElevation(rs.getInt("elevation"));
                    temp.setTrailtourUrl(rs.getString("trailtour_url"));
                    temp.setStravaUrl(rs.getString("strava_url"));
                    temp.setMapyczUrl(rs.getString("mapycz_url"));
                    return temp;
                });
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
        return selectObject("SELECT JSON_EXTRACT(strava_data , '$.latlng[0][0]') AS latitude, JSON_EXTRACT(strava_data , '$.latlng[0][1]') AS longitude FROM " + database + ".stage WHERE number = ?", new Object[]{number},
                rs -> {
                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("latitude", rs.getObject("latitude"));
                    result.put("longitude", rs.getObject("longitude"));
                    return result;
                });
    }

    public List<StageInfo> getInfos(String database, int number) throws SQLException {
        return selectList("SELECT author, title, content, created FROM " + database + ".stage_info WHERE stage_number = ?", new Object[]{number}, rs -> {
            StageInfo stageInfo = new StageInfo();
            stageInfo.setAuthor(rs.getString("author"));
            stageInfo.setTitle(rs.getString("title"));
            stageInfo.setContent(rs.getString("content"));
            stageInfo.setCreated(rs.getTimestamp("created").toLocalDateTime());
            return stageInfo;
        });
    }

    public List<Stage> getAll(String database) throws SQLException {
        return selectList("SELECT number, name, distance, elevation, type, trailtour_url, strava_url, mapycz_url FROM " + database + ".stage", new Object[]{}, rs -> {
            Stage stage = new Stage();
            stage.setNumber(rs.getInt("number"));
            stage.setName(rs.getString("name"));
            stage.setDistance(rs.getInt("distance"));
            stage.setElevation(rs.getInt("elevation"));
            stage.setType(rs.getString("type"));
            stage.setTrailtourUrl(rs.getString("trailtour_url"));
            stage.setStravaUrl(rs.getString("strava_url"));
            stage.setMapyczUrl(rs.getString("mapycz_url"));
            return stage;
        });
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
        return selectList("SELECT number, name, JSON_EXTRACT(strava_data , '$.latlng[0][0]') AS latitude, JSON_EXTRACT(strava_data , '$.latlng[0][1]') AS longitude FROM " + database + ".stage", new Object[]{},
                rs -> {
                    Map<String, Object> temp = new LinkedHashMap<>();
                    temp.put("stage_number", rs.getObject("number"));
                    temp.put("stage_name", rs.getObject("name"));
                    temp.put("latitude", rs.getObject("latitude"));
                    temp.put("longitude", rs.getObject("longitude"));
                    return temp;
                });
    }

    public List<Map<String, Object>> getResults(String database, int number) throws SQLException {
        return selectList(
                "SELECT athlete_id, athlete_name, athlete_gender, club_id, club_name, activity_id, activity_time, position, points, trailtour_position, trailtour_points, trailtour_time FROM " + database + ".athlete_data WHERE stage_number = ?",
                new Object[]{number},
                rs -> {
                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("athlete_id", rs.getObject("athlete_id"));
                    result.put("athlete_name", rs.getObject("athlete_name"));
                    result.put("athlete_gender", rs.getObject("athlete_gender"));
                    result.put("club_id", rs.getObject("club_id"));
                    result.put("club_name", rs.getObject("club_name"));
                    result.put("activity_id", rs.getObject("activity_id"));
                    result.put("activity_time", rs.getObject("activity_time"));
                    result.put("position", rs.getObject("position"));
                    result.put("points", rs.getObject("points"));
                    result.put("trailtour_position", rs.getObject("trailtour_position"));
                    result.put("trailtour_points", rs.getObject("trailtour_points"));
                    result.put("trailtour_time", rs.getObject("trailtour_time"));
                    return result;
                });
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
}
