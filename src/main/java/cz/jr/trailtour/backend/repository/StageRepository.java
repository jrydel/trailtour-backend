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

    public List<Stage> getAll() throws SQLException {
        return selectList("SELECT a.id, a.country, a.number, a.name, a.type, a.distance, a.elevation, a.latitude, a.longitude FROM trailtour.stage", new Object[]{}, rs -> {
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
            return stage;
        });
    }

    public int save(Stage stage) throws SQLException {
        return execute(generateUpsert("trailtour.stage", new Param[]{
                new Param("id"),
                new UpsertParam("country"),
                new UpsertParam("number"),
                new UpsertParam("name"),
                new UpsertParam("type"),
                new UpsertParam("distance"),
                new UpsertParam("elevation"),
                new UpsertParam("latitude"),
                new UpsertParam("longitude")
        }), new Object[]{stage.getId(), stage.getCountry(), stage.getName(), stage.getType(), stage.getDistance(), stage.getElevation(), stage.getLatitude(), stage.getLongitude()});
    }
}
