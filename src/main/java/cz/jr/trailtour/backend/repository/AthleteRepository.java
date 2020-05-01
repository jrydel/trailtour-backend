package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.entity.Club;
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

    public List<Athlete> getAll() throws SQLException {
        return selectList("SELECT a.id, a.name, a.gender, b.id, b.name FROM trailtour.athlete a LEFT JOIN trailtour.club b ON a.club_id = b.id", new Object[]{}, rs -> {
            Athlete athlete = new Athlete();
            athlete.setId(rs.getLong("a.id"));
            athlete.setName(rs.getString("a.name"));
            athlete.setGender(Athlete.Gender.valueOf(rs.getString("a.gender")));

            long clubId = rs.getLong("b.id");
            if (!rs.wasNull()) {
                Club club = new Club();
                club.setId(clubId);
                club.setName(rs.getString("b.name"));
                athlete.setClub(club);
            }

            return athlete;
        });
    }
}
