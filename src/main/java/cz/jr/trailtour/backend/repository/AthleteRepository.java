package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
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

    public List<Athlete> getAll(String database) throws SQLException {
        return selectList("SELECT a.id, a.name, a.gender, a.club_name FROM " + database + ".athlete a", new Object[]{}, rs -> {
            Athlete athlete = new Athlete();
            athlete.setId(rs.getLong("a.id"));
            athlete.setName(rs.getString("a.name"));
            athlete.setGender(rs.getString("a.gender"));
            athlete.setClub(rs.getString("a.club_name"));

            return athlete;
        });
    }

    public Athlete get(String database, long id) throws SQLException {
        return selectObject("SELECT a.name, a.gender, a.club_name FROM " + database + ".athlete a WHERE a.id = ?", new Object[]{id}, rs -> {
            Athlete athlete = new Athlete();
            athlete.setId(id);
            athlete.setName(rs.getString("a.name"));
            athlete.setGender(rs.getString("a.gender"));
            athlete.setClub(rs.getString("a.club_name"));

            return athlete;
        });
    }

}
