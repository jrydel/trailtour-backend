package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.Activity;
import cz.jr.trailtour.backend.repository.entities.ActivityResult;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.entities.Result;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.repository.entities.feed.FeedResult;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:15 PM
 */
@Repository
public class ResultRepository extends MysqlRepository {

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
        return selectList(
                "SELECT " +
                        "a.position, " +
                        "a.position_trailtour, " +
                        "a.time_trailtour, " +
                        "a.points, " +
                        "a.points_trailtour, " +
                        "b.id, " +
                        "b.name, " +
                        "b.gender, " +
                        "b.club_name, " +
                        "c.id, " +
                        "c.date, " +
                        "c.position, " +
                        "c.time " +
                        "FROM " + database + ".athlete_result a JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "JOIN " + database + ".activity c ON c.stage_number = a.stage_number AND c.athlete_id = b.id AND c.date = (SELECT MAX(date) FROM " + database + ".activity x WHERE x.stage_number = a.stage_number AND x.athlete_id = a.athlete_id) " +
                        "WHERE a.stage_number = ? AND a.timestamp = (SELECT MAX(timestamp) FROM " + database + ".athlete_result)", new Object[]{stageNumber, stageNumber}, rs -> {
                    Result result = new Result();

                    ActivityResult activityResult = new ActivityResult();
                    activityResult.setPosition(rs.getObject("a.position", Integer.class));
                    activityResult.setTrailtourPosition(rs.getObject("a.position_trailtour", Integer.class));
                    activityResult.setTrailtourTime(rs.getObject("a.time_trailtour", Integer.class));
                    activityResult.setPoints(rs.getObject("a.points", Double.class));
                    activityResult.setTrailtourPoints(rs.getObject("a.points_trailtour", Double.class));
                    result.setActivityResult(activityResult);

                    Athlete athlete = new Athlete();
                    athlete.setId(rs.getLong("b.id"));
                    athlete.setName(rs.getString("b.name"));
                    athlete.setGender(rs.getString("b.gender"));
                    athlete.setClub(rs.getString("b.club_name"));
                    result.setAthlete(athlete);

                    Long activityId = rs.getObject("c.id", Long.class);
                    if (activityId != null) {
                        Activity activity = new Activity();
                        activity.setId(activityId);
                        activity.setDate(rs.getDate("c.date").toLocalDate());
                        activity.setTime(rs.getInt("c.time"));
                        activity.setPosition(rs.getInt("c.position"));
                        result.setActivity(activity);
                    }

                    return result;
                });
    }

    public List<AthleteResult> getAthleteResults(String database, long athleteId) throws SQLException {
        return selectList(
                "SELECT " +
                        "a.position, " +
                        "a.position_trailtour, " +
                        "a.time_trailtour, " +
                        "a.points, " +
                        "a.points_trailtour, " +
                        "b.id, " +
                        "b.date, " +
                        "b.position, " +
                        "b.time, " +
                        "c.number, " +
                        "c.name " +
                        "FROM " + database + ".athlete_result a " +
                        "JOIN " + database + ".activity b ON a.stage_number = b.stage_number AND a.athlete_id = b.athlete_id AND b.date = (SELECT MAX(x.date) FROM " + database + ".activity x WHERE x.stage_number = a.stage_number AND x.athlete_id = a.athlete_id) " +
                        "JOIN " + database + ".stage c ON c.number = a.stage_number " +
                        "WHERE a.athlete_id = ? AND a.timestamp = (SELECT MAX(timestamp) FROM " + database + ".athlete_result)", new Object[]{athleteId}, rs -> {

                    AthleteResult result = new AthleteResult();

                    ActivityResult activityResult = new ActivityResult();
                    activityResult.setPosition(rs.getInt("a.position"));
                    activityResult.setTrailtourPosition(rs.getInt("a.position_trailtour"));
                    activityResult.setTrailtourTime(rs.getInt("a.time_trailtour"));
                    activityResult.setPoints(rs.getDouble("a.points"));
                    activityResult.setTrailtourPoints(rs.getDouble("a.points_trailtour"));
                    result.setActivityResult(activityResult);

                    Activity activity = new Activity();
                    activity.setId(rs.getLong("b.id"));
                    activity.setDate(rs.getDate("b.date").toLocalDate());
                    activity.setPosition(rs.getInt("b.position"));
                    activity.setTime(rs.getInt("b.time"));
                    result.setActivity(activity);

                    Stage stage = new Stage();
                    stage.setNumber(rs.getInt("c.number"));
                    stage.setName(rs.getString("c.name"));
                    result.setStage(stage);

                    return result;
                });
    }

    public int getResultsCount(String database, String gender, int stageNumber) throws SQLException {
        return selectObject("SELECT COUNT(*) as count FROM " + database + ".athlete_result a JOIN " + database + ".athlete b ON a.athlete_id = b.id WHERE b.gender = ? AND a.stage_number = ?", new Object[]{gender, stageNumber}, rs -> rs.getInt("count"));
    }
}
