package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.Stage;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import cz.jr.trailtour.backend.repository.mysql.Param;
import cz.jr.trailtour.backend.repository.mysql.UpsertParam;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:21 PM
 */
@Repository
public class StageRepository extends MysqlRepository {

    public StageRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Long getStageId(String database, int number) throws SQLException {
        return selectObject("SELECT id FROM " + database + ".stage WHERE number = ?", new Object[]{number}, rs -> rs.getObject("id", Long.class));
    }

    public Stage get(String database, long stageId) throws SQLException {
        return selectObject("SELECT a.country, a.number, a.name, a.type, a.distance, a.elevation, a.latitude, a.longitude, a.trailtour_url, a.mapycz_url, COUNT(*) AS activities FROM " + database + ".stage a LEFT JOIN " + database + ".result b ON a.id = b.stage_id WHERE a.id = ? GROUP by a.id", new Object[]{stageId}, rs -> {
            Stage stage = new Stage();
            stage.setId(stageId);
            stage.setCountry(rs.getString("a.country"));
            stage.setNumber(rs.getInt("a.number"));
            stage.setName(rs.getString("a.name"));
            stage.setType(rs.getString("a.type"));
            stage.setDistance(rs.getDouble("a.distance"));
            stage.setElevation(rs.getInt("a.elevation"));
            stage.setLatitude(rs.getDouble("a.latitude"));
            stage.setLongitude(rs.getDouble("a.longitude"));
            stage.setTrailtourUrl(rs.getString("a.trailtour_url"));
            stage.setMapyczUrl(rs.getString("a.mapycz_url"));
            stage.setActivities(rs.getInt("activities"));
            return stage;
        });
    }

    public List<Stage> getAll(String database) throws SQLException {
        return selectList("SELECT a.id, a.country, a.number, a.name, a.type, a.distance, a.elevation, a.latitude, a.longitude, a.trailtour_url, a.mapycz_url, COUNT(b.stage_id) AS activities FROM " + database + ".stage a LEFT JOIN " + database + ".result b ON a.id = b.stage_id GROUP BY a.id", new Object[]{}, rs -> {
            Stage stage = new Stage();
            stage.setId(rs.getLong("a.id"));
            stage.setCountry(rs.getString("a.country"));
            stage.setNumber(rs.getInt("a.number"));
            stage.setName(rs.getString("a.name"));
            stage.setType(rs.getString("a.type"));
            stage.setDistance(rs.getDouble("a.distance"));
            stage.setElevation(rs.getInt("a.elevation"));
            stage.setLatitude(rs.getDouble("a.latitude"));
            stage.setLongitude(rs.getDouble("a.longitude"));
            stage.setTrailtourUrl(rs.getString("a.trailtour_url"));
            stage.setMapyczUrl(rs.getString("a.mapycz_url"));
            stage.setActivities(rs.getInt("activities"));
            return stage;
        });
    }

    public int save(String database, Stage stage) throws SQLException {
        return execute(generateUpsert(database + ".stage", new Param[]{
                new Param("id"),
                new UpsertParam("country"),
                new UpsertParam("number"),
                new UpsertParam("name"),
                new UpsertParam("type"),
                new UpsertParam("distance"),
                new UpsertParam("elevation"),
                new UpsertParam("latitude"),
                new UpsertParam("longitude"),
                new UpsertParam("trailtour_url"),
                new UpsertParam("mapycz_url")
        }), new Object[]{
                stage.getId(),
                stage.getCountry(),
                stage.getNumber(),
                stage.getName(),
                stage.getType(),
                stage.getDistance(),
                stage.getElevation(),
                stage.getLatitude(),
                stage.getLongitude(),
                stage.getTrailtourUrl(),
                stage.getMapyczUrl()
        });
    }
}
