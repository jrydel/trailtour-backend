package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.core.Downloader;
import cz.jr.trailtour.backend.core.NoSegmentDataException;
import cz.jr.trailtour.backend.core.Parser;
import cz.jr.trailtour.backend.core.ParserResult;
import cz.jr.trailtour.backend.repository.AthleteRepository;
import cz.jr.trailtour.backend.repository.ResultRepository;
import cz.jr.trailtour.backend.repository.SegmentRepository;
import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.entity.Result;
import cz.jr.trailtour.backend.repository.entity.Stage;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 2:47 PM
 */
@Service
public class ScraperService {

    private final SegmentRepository segmentService;
    private final AthleteRepository athleteRepository;
    private final ResultRepository resultRepository;

    public ScraperService(SegmentRepository segmentService, AthleteRepository athleteRepository, ResultRepository resultRepository) {
        this.segmentService = segmentService;
        this.athleteRepository = athleteRepository;
        this.resultRepository = resultRepository;
    }

    public long scrap() throws IOException, InterruptedException, SQLException {
        long start = System.currentTimeMillis();

        final Downloader downloader = new Downloader();
        final Parser parser = new Parser();
        List<Stage> stageList = segmentService.findAll();

        try {
            downloader.logIn();
            for (Stage stage : stageList) {
                ParserResult temp = processSegment(downloader, parser, stage);
                for (Athlete athlete : temp.getAthleteList()) {
                    athleteRepository.saveAthlete(athlete);
                }
                for (Result result : temp.getResultList()) {
                    resultRepository.saveResult(result);
                }
            }
        } finally {
            downloader.logOut();
        }
        
        return System.currentTimeMillis() - start;
    }

    private ParserResult processSegment(Downloader downloader, Parser parser, Stage stage) throws IOException, InterruptedException {
        ParserResult parserResult = new ParserResult();
        for (Athlete.Gender gender : Athlete.Gender.values()) {
            int page = 1;
            while (true) {
                Document document = downloader.get("https://www.strava.com/segments/" + stage.getId() + "/leaderboard?filter=overall&gender=" + gender.toString() + "&page=" + page++ + "&per_page=25&partial=true");
                try {
                    ParserResult temp = parser.parseSegment(document, gender, stage.getId());
                    parserResult.getAthleteList().addAll(temp.getAthleteList());
                    parserResult.getResultList().addAll(temp.getResultList());
                } catch (IndexOutOfBoundsException | NoSegmentDataException e) {
                    break;
                }
            }
        }
        return parserResult;
    }
}
