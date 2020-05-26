package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.entities.Ladder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:44 PM
 */
@Repository
public class AthleteRepository extends BaseRepository {

    public AthleteRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public List<Athlete> getAll(String database) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectList("SELECT a.id, a.name, a.gender, a.club_name, b.position, b.points, c.position, c.points FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".athlete_ladder b ON b.athlete_id = a.id AND b.timestamp = ? " +
                        "LEFT JOIN " + database + ".athlete_ladder_trailtour c ON c.athlete_id = a.id AND c.timestamp = b.timestamp",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate)
                },
                rs -> {
                    Athlete athlete = new Athlete();
                    athlete.setId(rs.getLong("a.id"));
                    athlete.setName(rs.getString("a.name"));
                    athlete.setGender(rs.getString("a.gender"));
                    athlete.setClub(rs.getString("a.club_name"));


                    Ladder ladder = new Ladder();
                    ladder.setPosition(rs.getObject("b.position", Integer.class));
                    ladder.setPoints(rs.getObject("b.points", Double.class));
                    ladder.setTrailtourPosition(rs.getObject("c.position", Integer.class));
                    ladder.setTrailtourPoints(rs.getObject("c.points", Double.class));
                    athlete.setLadder(ladder);

                    return athlete;
                });
    }

    public Athlete get(String database, long id) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectObject("SELECT a.name, a.gender, a.club_name, b.position, b.points, c.position, c.points FROM " + database + ".athlete a " +
                        "LEFT JOIN " + database + ".athlete_ladder b ON b.athlete_id = a.id AND b.timestamp = ? " +
                        "LEFT JOIN " + database + ".athlete_ladder_trailtour c ON c.athlete_id = a.id AND c.timestamp = b.timestamp " +
                        "WHERE a.id = ?",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate),
                        id
                },
                rs -> {
                    Athlete athlete = new Athlete();
                    athlete.setId(id);
                    athlete.setName(rs.getString("a.name"));
                    athlete.setGender(rs.getString("a.gender"));
                    athlete.setClub(rs.getString("a.club_name"));

                    Ladder ladder = new Ladder();
                    ladder.setPosition(rs.getObject("b.position", Integer.class));
                    ladder.setPoints(rs.getObject("b.points", Double.class));
                    ladder.setTrailtourPosition(rs.getObject("c.position", Integer.class));
                    ladder.setTrailtourPoints(rs.getObject("c.points", Double.class));
                    athlete.setLadder(ladder);

                    return athlete;
                });
    }

}
