package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.Activity;
import cz.jr.trailtour.backend.repository.entities.ActivityResult;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.entities.Result;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.repository.entities.feed.FeedResult;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:15 PM
 */
@Repository
public class ResultRepository extends BaseRepository {

    public ResultRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public List<FeedResult> getFeed(String database, int limit) throws SQLException {
        return selectList(
                "SELECT " +
                        "a.id, " +
                        "a.position, " +
                        "a.time, " +
                        "a.created, " +
                        "b.id, " +
                        "b.name, " +
                        "b.club_name, " +
                        "c.number, " +
                        "c.name " +
                        "FROM " + database + ".activity a " + "JOIN " + database + ".athlete b ON a.athlete_id = b.id JOIN " + database + ".stage c ON c.number = a.stage_number " +
                        "ORDER BY a.created DESC LIMIT ?", new Object[]{limit}, rs -> {
                    FeedResult result = new FeedResult();

                    Activity activity = new Activity();
                    activity.setId(rs.getLong("a.id"));
                    activity.setPosition(rs.getInt("a.position"));
                    activity.setTime(rs.getInt("a.time"));
                    activity.setCreated(rs.getTimestamp("a.created").toLocalDateTime());
                    result.setActivity(activity);

                    Stage stage = new Stage();
                    stage.setNumber(rs.getInt("c.number"));
                    stage.setName(rs.getString("c.name"));
                    result.setStage(stage);

                    Athlete athlete = new Athlete();
                    athlete.setId(rs.getLong("b.id"));
                    athlete.setName(rs.getString("b.name"));
                    athlete.setClub(rs.getString("b.club_name"));
                    result.setAthlete(athlete);

                    return result;
                });
    }

    public List<Result> getResults(String database, int stageNumber) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT " +
                        "a.position, " +
                        "a.points, " +
                        "b.position, " +
                        "b.points, " +
                        "b.time, " +
                        "c.id, " +
                        "c.date, " +
                        "c.position, " +
                        "c.time, " +
                        "d.id, " +
                        "d.name, " +
                        "d.gender, " +
                        "d.club_name " +
                        "FROM " + database + ".athlete_result a " +
                        "LEFT JOIN " + database + ".athlete_result_trailtour b ON b.athlete_id = a.athlete_id AND b.stage_number = a.stage_number AND b.timestamp = a.timestamp " +
                        "JOIN " + database + ".activity c ON c.athlete_id = a.athlete_id AND c.stage_number = a.stage_number AND c.date = (SELECT MAX(e.date) FROM " + database + ".activity e WHERE e.athlete_id = c.athlete_id AND e.stage_number = c.stage_number) " +
                        "JOIN " + database + ".athlete d ON d.id = a.athlete_id " +
                        "WHERE a.timestamp = ? AND a.stage_number = ? AND d.status = ?",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate),
                        stageNumber,
                        "enabled"
                },
                rs -> {
                    Result result = new Result();

                    Activity activity = new Activity();
                    activity.setId(rs.getLong("c.id"));
                    activity.setDate(rs.getDate("c.date").toLocalDate());
                    activity.setPosition(rs.getInt("c.position"));
                    activity.setTime(rs.getInt("c.time"));
                    result.setActivity(activity);

                    Athlete athlete = new Athlete();
                    athlete.setId(rs.getLong("d.id"));
                    athlete.setName(rs.getString("d.name"));
                    athlete.setGender(rs.getString("d.gender"));
                    athlete.setClub(rs.getString("d.club_name"));
                    result.setAthlete(athlete);

                    ActivityResult activityResult = new ActivityResult();
                    activityResult.setPosition(rs.getObject("a.position", Integer.class));
                    activityResult.setPoints(rs.getObject("a.points", Double.class));
                    activityResult.setTrailtourPosition(rs.getObject("b.position", Integer.class));
                    activityResult.setTrailtourPoints(rs.getObject("b.points", Double.class));
                    activityResult.setTrailtourTime(rs.getObject("b.time", Integer.class));
                    result.setActivityResult(activityResult);

                    return result;
                });
    }

    public List<AthleteResult> getAthleteResults(String database, long athleteId) throws SQLException {
        LocalDateTime lastResultUpdate = getLastResultUpdate(database);
        return selectList(
                "SELECT " +
                        "a.id, " +
                        "a.date, " +
                        "a.position, " +
                        "a.time, " +
                        "b.number, " +
                        "b.name, " +
                        "c.position, " +
                        "c.points, " +
                        "d.position, " +
                        "d.points, " +
                        "d.time " +
                        "FROM " + database + ".activity a " +
                        "JOIN " + database + ".stage b ON b.number = a.stage_number " +
                        "LEFT JOIN " + database + ".athlete_result c ON c.athlete_id = a.athlete_id AND c.timestamp = ? AND c.stage_number = a.stage_number " +
                        "LEFT JOIN " + database + ".athlete_result_trailtour d ON d.athlete_id = a.athlete_id AND d.timestamp = ? AND d.stage_number = a.stage_number " +
                        "WHERE a.athlete_id = ? AND a.date = (SELECT MAX(x.date) FROM " + database + ".activity x WHERE x.stage_number = a.stage_number AND x.athlete_id = a.athlete_id) ",
                new Object[]{
                        java.sql.Timestamp.valueOf(lastResultUpdate),
                        java.sql.Timestamp.valueOf(lastResultUpdate),
                        athleteId
                }, rs -> {

                    AthleteResult result = new AthleteResult();

                    Activity activity = new Activity();
                    activity.setId(rs.getLong("a.id"));
                    activity.setDate(rs.getDate("a.date").toLocalDate());
                    activity.setPosition(rs.getInt("a.position"));
                    activity.setTime(rs.getInt("a.time"));
                    result.setActivity(activity);

                    Stage stage = new Stage();
                    stage.setNumber(rs.getInt("b.number"));
                    stage.setName(rs.getString("b.name"));
                    result.setStage(stage);

                    ActivityResult activityResult = new ActivityResult();
                    activityResult.setPosition(rs.getObject("c.position", Integer.class));
                    activityResult.setPoints(rs.getObject("c.points", Double.class));
                    activityResult.setTrailtourPosition(rs.getObject("d.position", Integer.class));
                    activityResult.setTrailtourPoints(rs.getObject("d.points", Double.class));
                    activityResult.setTrailtourTime(rs.getObject("d.time", Integer.class));
                    result.setActivityResult(activityResult);

                    return result;
                });
    }

    public int getResultsCount(String database, String gender, int stageNumber) throws SQLException {
        return selectObject("SELECT COUNT(*) as count FROM " + database + ".athlete_result a JOIN " + database + ".athlete b ON a.athlete_id = b.id WHERE b.gender = ? AND a.stage_number = ? AND a.timestamp = (SELECT MAX(timestamp) FROM " + database + ".athlete_result)", new Object[]{gender, stageNumber}, rs -> rs.getInt("count"));
    }
}
