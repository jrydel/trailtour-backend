package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.entity.AthleteWeb;
import cz.jr.trailtour.backend.repository.entity.Club;
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

    public List<AthleteWeb> getAllWeb() throws SQLException {
        return selectList("SELECT a.id, a.name, a.gender, b.id, b.name FROM trailtour.athlete a LEFT JOIN trailtour.club b ON a.club_id = b.id", new Object[]{}, rs -> {
            AthleteWeb athlete = new AthleteWeb();
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
