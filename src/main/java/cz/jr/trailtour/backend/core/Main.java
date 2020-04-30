package cz.jr.trailtour.backend.core;

/**
 * Created by Jiří Rýdel on 4/14/20, 10:34 AM
 */
public class Main {
//
//    private static final Logger LOG = LogManager.getLogger(Main.class);
//
//    public static void main(String[] args) {
//        try {
//            Downloader downloader = new Downloader();
//            Parser parser = new Parser();
//            try (HikariDataSource dataSource = MysqlUtils.createDatasource()) {
//                List<Long> segments = getSegmentsToDownload(dataSource);
//
//                downloader.logIn();
//
//                for (Long segmentId : segments) {
//                    List<Result> resultList = processSegment(downloader, parser, segmentId);
//                    for (Result result : resultList) {
//                        Athlete athlete = result.getAthlete();
//                        MysqlUtils.execute(dataSource, "INSERT INTO trailtour.athlete (id, name, gender) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name), gender = VALUES(gender)",
//                                new Object[]{athlete.getId(), athlete.getName(), athlete.getGender().toString()});
//                        MysqlUtils.execute(dataSource, "INSERT INTO trailtour.result (segment_id, athlete_id, activity_id, position, date, time) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE activity_id = VALUES(activity_id), position = VALUES(position), date = VALUES(date), time = VALUES(time)",
//                                new Object[]{result.getStageId(), athlete.getId(), result.getActivityId(), result.getPosition(), result.getDate().format(DateTimeFormatter.ISO_DATE), result.getTime()});
//                    }
//                }
//            } finally {
//                downloader.logOut();
//            }
//        } catch (IOException | InterruptedException | SQLException e) {
//            LOG.error("Error.", e);
//        }
//    }
//
//    static List<Result> processSegment(Downloader downloader, Parser parser, long segmentId) throws IOException, InterruptedException {
//        List<Result> result = new ArrayList<>();
//        for (Athlete.Gender gender : Athlete.Gender.values()) {
//            int page = 1;
//            while (true) {
//                Document document = downloader.get("https://www.strava.com/segments/" + segmentId + "/leaderboard?filter=overall&gender=" + gender.toString() + "&page=" + page++ + "&per_page=25&partial=true");
//                try {
//                    List<Result> resultList = parser.parseSegment(document, gender, segmentId);
//                    result.addAll(resultList);
//                } catch (IndexOutOfBoundsException | NoSegmentDataException e) {
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//
//    static List<Long> getSegmentsToDownload(HikariDataSource dataSource) throws SQLException {
//        return MysqlUtils.selectRawList(dataSource, "SELECT id FROM trailtour.segment", new Object[]{}, Long.class);
//    }
}
