package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.entities.*;
import cz.jr.trailtour.backend.repository.entities.athlete.AthleteResult;
import cz.jr.trailtour.backend.repository.entities.feed.FeedResult;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:15 PM
 */
@Repository
public class ResultRepository extends BaseRepository {

    public ResultRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Map<String, Object> getFeed(String database, int limit, int offset) throws SQLException {
        int count = selectObject("SELECT COUNT(*) AS count FROM " + database + ".activity", new Object[]{}, rs -> rs.getInt("count"));
        LocalDateTime lastUpdate = getLastResultUpdate(database);
        List<FeedResult> results = selectList(
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
                        "FROM " + database + ".activity a " +
                        "JOIN " + database + ".athlete b ON a.athlete_id = b.id " +
                        "JOIN " + database + ".stage c ON c.number = a.stage_number " +
                        "ORDER BY a.created DESC LIMIT ? OFFSET ?", new Object[]{limit, offset}, rs -> {
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

        Map<String, Object> map = new HashMap<>();
        map.put("lastUpdate", lastUpdate.format(DateTimeFormatter.ISO_DATE_TIME));
        map.put("count", count);
        map.put("data", results);
        return map;
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



    public Map<Integer, Map<String, Object>> getKomResults(String database) throws SQLException {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        select("SELECT stage_number, athlete_id, athlete_name, athlete_gender, club_id, club_name, activity_id, activity_time FROM " + database + ".athlete_data WHERE position = 1", new Object[]{}, rs -> {
            while (rs.next()) {
                int stageNumber = rs.getInt("stage_number");
                long athleteId = rs.getLong("athlete_id");
                String athleteName = rs.getString("athlete_name");
                String athleteGender = rs.getString("athlete_gender");
                long clubId = rs.getLong("club_id");
                String clubName = rs.getString("club_name");
                long activityId = rs.getLong("activity_id");
                int activityTime = rs.getInt("activity_time");

                Map<String, Object> temp = new HashMap<>();
                temp.put("athleteId", athleteId);
                temp.put("athleteName", athleteName);
                temp.put("clubId", clubId);
                temp.put("clubName", clubName);
                temp.put("activityId", activityId);
                temp.put("activityTime", activityTime);

                result.computeIfAbsent(stageNumber, k -> new HashMap<>()).put(athleteGender, temp);
            }
            return null;
        });
        return result;
    }

    public Map<String, Integer> getResultsCounts(String database, int stageNumber) throws SQLException {
        Map<String, Integer> result = new LinkedHashMap<>();
        select("SELECT athlete_gender, COUNT(*) as count FROM " + database + ".athlete_data WHERE stage_number = ? GROUP BY athlete_gender",
                new Object[]{stageNumber},
                rs -> {
                    while (rs.next()) {
                        String gender = rs.getString("athlete_gender");
                        int count = rs.getInt("count");

                        result.put(gender, count);
                    }
                    return null;
                });

        return result;
    }
}
