package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import cz.jr.trailtour.backend.repository.mysql.Param;
import cz.jr.trailtour.backend.repository.mysql.UpsertParam;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:44 PM
 */
@Repository
public class AthleteRepository extends MysqlRepository {

    public AthleteRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public void saveAthlete(Athlete athlete) throws SQLException {
        execute(generateUpsert("trailtour.athlete", new Param[]{
                new Param("id"),
                new UpsertParam("name"),
                new UpsertParam("gender")
        }), new Object[]{athlete.getId(), athlete.getName(), athlete.getGender().toString()});
    }

    public List<Athlete> getAll() throws SQLException {
        return selectList("SELECT id, club_id, name, gender FROM trailtour.athlete", new Object[]{}, rs -> {
            Athlete athlete = new Athlete();
            athlete.setId(rs.getLong("id"));
            athlete.setClubId(rs.getLong("club_id"));
            athlete.setName(rs.getString("name"));
            athlete.setGender(Athlete.Gender.valueOf(rs.getString("gender")));
            return athlete;
        });
    }
}
