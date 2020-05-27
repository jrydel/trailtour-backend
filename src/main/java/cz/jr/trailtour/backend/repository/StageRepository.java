package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.entities.stage.StageData;
import cz.jr.trailtour.backend.repository.entities.stage.StageInfo;
import cz.jr.trailtour.backend.repository.mysql.Param;
import cz.jr.trailtour.backend.repository.mysql.UpsertParam;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:21 PM
 */
@Repository
public class StageRepository extends BaseRepository {

    public StageRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public StageData get(String database, int number) throws SQLException {
        LocalDateTime update = getLastResultUpdate(database);

        StageData stageData = selectObject("SELECT a.name, a.type, a.distance, a.elevation, a.url, a.strava_url, a.strava_data, a.mapycz_url, COUNT(*) AS activities FROM " + database + ".stage a " +
                        "LEFT JOIN " + database + ".athlete_result b ON a.number = b.stage_number " +
                        "WHERE a.number = ? AND b.timestamp = ? GROUP by a.number",
                new Object[]{number, Timestamp.valueOf(update)},
                rs -> {
                    StageData stage = new StageData();
                    stage.setNumber(number);
                    stage.setName(rs.getString("a.name"));
                    stage.setType(rs.getString("a.type"));
                    stage.setDistance(rs.getInt("a.distance"));
                    stage.setElevation(rs.getInt("a.elevation"));
                    stage.setTrailtourUrl(rs.getString("a.url"));
                    stage.setStravaUrl(rs.getString("a.strava_url"));
                    stage.setStravaData(rs.getString("a.strava_data"));
                    stage.setMapyczUrl(rs.getString("a.mapycz_url"));
                    stage.setActivities(rs.getInt("activities"));
                    return stage;
                });

        List<StageInfo> stageInfoList = selectList("SELECT author, content, created FROM " + database + ".stage_info WHERE stage_number = ?", new Object[]{number}, rs -> {
            StageInfo stageInfo = new StageInfo();
            stageInfo.setAuthor(rs.getString("author"));
            stageInfo.setContent(rs.getString("content"));
            stageInfo.setCreated(rs.getTimestamp("created").toLocalDateTime());
            return stageInfo;
        });

        stageData.getStageInfoList().addAll(stageInfoList);

        return stageData;
    }

    public List<Stage> getAll(String database) throws SQLException {
        LocalDateTime update = getLastResultUpdate(database);

        return selectList("SELECT a.number, a.name, a.type,a.distance, a.elevation, a.url, a.strava_url, a.mapycz_url, COUNT(*) AS activities FROM " + database + ".stage a LEFT JOIN " + database + ".athlete_result b ON a.number = b.stage_number WHERE b.timestamp = ? GROUP by a.number", new Object[]{java.sql.Timestamp.valueOf(update)}, rs -> {
            Stage stage = new Stage();
            stage.setNumber(rs.getInt("a.number"));
            stage.setName(rs.getString("a.name"));
            stage.setTrailtourUrl(rs.getString("a.url"));
            stage.setDistance(rs.getInt("a.distance"));
            stage.setElevation(rs.getInt("a.elevation"));
            stage.setType(rs.getString("a.type"));
            stage.setStravaUrl(rs.getString("a.strava_url"));
            stage.setMapyczUrl(rs.getString("a.mapycz_url"));
            stage.setActivities(rs.getInt("activities"));
            return stage;
        });
    }

    public List<StageData> getAllData(String database) throws SQLException {
        LocalDateTime update = getLastResultUpdate(database);

        return selectList("SELECT a.number, a.name, a.url, a.distance, a.elevation, a.type, a.strava_url, a.strava_data, a.mapycz_url, COUNT(*) AS activities FROM " + database + ".stage a LEFT JOIN " + database + ".athlete_result b ON a.number = b.stage_number WHERE b.timestamp = ? GROUP by a.number", new Object[]{java.sql.Timestamp.valueOf(update)}, rs -> {
            StageData stage = new StageData();
            stage.setNumber(rs.getInt("a.number"));
            stage.setName(rs.getString("a.name"));
            stage.setTrailtourUrl(rs.getString("a.url"));
            stage.setDistance(rs.getInt("a.distance"));
            stage.setElevation(rs.getInt("a.elevation"));
            stage.setType(rs.getString("a.type"));
            stage.setStravaUrl(rs.getString("a.strava_url"));
            stage.setStravaData(rs.getString("a.strava_data"));
            stage.setMapyczUrl(rs.getString("a.mapycz_url"));
            stage.setActivities(rs.getInt("activities"));
            return stage;
        });
    }

    public int save(String database, Stage stage) throws SQLException {
        return execute(generateUpsert(database + ".stage", new Param[]{
                new Param("number"),
                new Param("url"),
                new UpsertParam("name"),
                new UpsertParam("distance"),
                new UpsertParam("elevation"),
                new UpsertParam("type")
        }), new Object[]{
                stage.getNumber(),
                stage.getTrailtourUrl(),
                stage.getName(),
                stage.getDistance(),
                stage.getElevation(),
                stage.getType()
        });
    }

    public int saveInfo(String database, StageInfo stageInfo) throws SQLException {
        return execute("INSERT INTO " + database + ".stage_info (author, content) VALUES (?, ?)", new Object[]{stageInfo.getAuthor(), stageInfo.getContent()});
    }
}
