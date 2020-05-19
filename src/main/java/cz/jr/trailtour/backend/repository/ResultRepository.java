package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.*;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteStage;
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
                        "b.abuser, " +
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
                    athlete.setAbuser(rs.getBoolean("b.abuser"));
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
                        "b.abuser, " +
                        "c.id, " +
                        "c.date, " +
                        "c.time " +
                        "FROM " + database + ".athlete_result a JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "LEFT JOIN " + database + ".activity c ON c.stage_number = a.stage_number AND c.athlete_id = b.id AND c.date = (SELECT MAX(date) FROM " + database + ".activity x WHERE x.stage_number = a.stage_number AND x.athlete_id = a.athlete_id) " +
                        "WHERE a.stage_number = ? AND a.date = (SELECT MAX(date) FROM " + database + ".athlete_result WHERE stage_number = ?)", new Object[]{stageNumber, stageNumber}, rs -> {
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
                    athlete.setAbuser(rs.getBoolean("b.abuser"));
                    result.setAthlete(athlete);

                    Long activityId = rs.getObject("c.id", Long.class);
                    if (activityId != null) {
                        Activity activity = new Activity();
                        activity.setId(activityId);
                        activity.setDate(rs.getDate("c.date").toLocalDate());
                        activity.setTime(rs.getInt("c.time"));
                        result.setActivity(activity);
                    }

                    return result;
                });
    }

    public List<AthleteResult> getAthleteResults(String database, long athleteId) throws SQLException {
        return selectList(
                "SELECT " +
                        "a.activity_id, " +
                        "a.position, " +
                        "a.date, " +
                        "a.time, " +
                        "a.points, " +
                        "a.time_trailtour, " +
                        "a.points_trailtour, " +
                        "b.number, " +
                        "b.name " +
                        "FROM " + database + ".result a " + "JOIN " + database + ".stage b ON b.number = a.stage_number " +
                        "WHERE a.athlete_id = ?", new Object[]{athleteId}, rs -> {
                    AthleteResult result = new AthleteResult();

                    StravaResult stravaResult = new StravaResult();
                    stravaResult.setActivityId(rs.getObject("a.activity_id", Long.class));
                    stravaResult.setPosition(rs.getObject("a.position", Integer.class));
                    stravaResult.setDate(rs.getString("a.date"));
                    stravaResult.setTime(rs.getObject("a.time", Integer.class));
                    stravaResult.setPoints(rs.getObject("a.points", Double.class));
                    result.setStravaResult(stravaResult);

                    TrailtourResult trailtourResult = new TrailtourResult();
                    trailtourResult.setTime(rs.getObject("a.time_trailtour", Integer.class));
                    trailtourResult.setPoints(rs.getObject("a.points_trailtour", Double.class));
                    result.setTrailtourResult(trailtourResult);

                    AthleteStage stage = new AthleteStage();
                    stage.setNumber(rs.getInt("b.number"));
                    stage.setName(rs.getString("b.name"));
                    result.setStage(stage);

                    return result;
                });
    }

    public int getResultsCount(String database, String gender, int stageNumber) throws SQLException {
        return selectObject("SELECT COUNT(*) as count FROM " + database + ".result a JOIN " + database + ".athlete b ON a.athlete_id = b.id WHERE b.gender = ? AND a.stage_number = ?", new Object[]{gender, stageNumber}, rs -> rs.getInt("count"));
    }
}
