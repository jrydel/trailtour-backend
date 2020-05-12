package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.entities.Result;
import cz.jr.trailtour.backend.repository.entities.StravaResult;
import cz.jr.trailtour.backend.repository.entities.TrailtourResult;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteStage;
import cz.jr.trailtour.backend.repository.entities.feed.FeedAthlete;
import cz.jr.trailtour.backend.repository.entities.feed.FeedResult;
import cz.jr.trailtour.backend.repository.entities.feed.FeedStage;
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
                        "a.activity_id, " +
                        "a.time, " +
                        "a.position, " +
                        "a.created, " +
                        "b.id, " +
                        "b.name, " +
                        "b.club_name, " +
                        "b.abuser, " +
                        "c.number, " +
                        "c.name " +
                        "FROM " + database + ".result a " + "JOIN " + database + ".athlete b ON a.athlete_id = b.id JOIN " + database + ".stage c ON c.number = a.stage_number " +
                        "WHERE a.activity_id IS NOT NULL AND b.status = ?" +
                        "ORDER BY a.created DESC LIMIT ?", new Object[]{"enabled", limit}, rs -> {
                    FeedResult result = new FeedResult();
                    result.setActivityId(rs.getLong("a.activity_id"));
                    result.setTime(rs.getInt("a.time"));
                    result.setPosition(rs.getInt("a.position"));
                    result.setCreated(rs.getTimestamp("a.created").toLocalDateTime());

                    FeedStage stage = new FeedStage();
                    stage.setNumber(rs.getInt("c.number"));
                    stage.setName(rs.getString("c.name"));
                    result.setStage(stage);

                    FeedAthlete athlete = new FeedAthlete();
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
                        "a.activity_id, " +
                        "a.position, " +
                        "a.date, " +
                        "a.time, " +
                        "a.points, " +
                        "a.time_trailtour, " +
                        "a.points_trailtour, " +
                        "a.created, " +
                        "b.id, " +
                        "b.name, " +
                        "b.gender, " +
                        "b.club_name, " +
                        "b.abuser, " +
                        "b.points, " +
                        "b.points_trailtour " +
                        "FROM " + database + ".result a " + "JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "WHERE b.status = ? AND a.stage_number = ?", new Object[]{"enabled", stageNumber}, rs -> {
                    Result result = new Result();

                    Athlete athlete = new Athlete();
                    athlete.setId(rs.getLong("b.id"));
                    athlete.setName(rs.getString("b.name"));
                    athlete.setGender(rs.getString("b.gender"));
                    athlete.setClub(rs.getString("b.club_name"));
                    athlete.setPoints(rs.getObject("b.points", Double.class));
                    athlete.setPointsTrailtour(rs.getObject("b.points_trailtour", Double.class));
                    athlete.setAbuser(rs.getBoolean("b.abuser"));
                    result.setAthlete(athlete);

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
        return selectObject("SELECT COUNT(*) as count FROM " + database + ".result a JOIN " + database + ".athlete b ON a.athlete_id = b.id WHERE b.gender = ? AND a.stage_number = ? AND b.status = ?", new Object[]{gender, stageNumber, "enabled"}, rs -> rs.getInt("count"));
    }
}
