package cz.jr.trailtour.backend.core;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.entity.Result;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jiří Rýdel on 4/14/20, 11:47 AM
 */
public class Parser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Set<String> parseSegmentLinks(Document document) {
        Set<String> result = new HashSet<>();
        Elements element = document.select("ul.pagination");
        for (Element element1 : element.select("li").select("a")) {
            String href = element1.attr("href");
            result.add(href);
        }
        return result;
    }

    public ParserResult parseSegment(Document document, Athlete.Gender gender, long segmentId) throws JsonProcessingException, NoSegmentDataException {
        ParserResult parserResult = new ParserResult();

        Element test = document.select("div#results").select("tbody").select("td").first();
        if (test != null && "No results found".equals(test.text())) {
            throw new NoSegmentDataException();
        }

        List<Result> list = new ArrayList<>();

        Element tableElement = document.select("table.table-leaderboard").first();
        for (Element element : tableElement.select("tBody").select("tr")) {
            Element resultElement = element.select("td[data-tracking-element=leaderboard_effort]").first();
            if (resultElement == null) {
                continue;
            }
            //{"athlete_id":27058130,"activity_id":2029260372,"segment_effort_id":80735082841,"rank":1}
            String json = resultElement.attr("data-tracking-properties");
            JsonNode root = objectMapper.readTree(json);

            long athleteId = root.get("athlete_id").asLong();
            long activityId = root.get("activity_id").asLong();
            int position = root.get("rank").asInt();

            String date = resultElement.text();
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy"));

            // skip vsechny pred kvetnem 2020
            if (localDate.isBefore(LocalDate.of(2020, 5, 1))) {
                continue;
            }

            String time = element.child(6).text();
            String name = element.child(1).text();

            Athlete athlete = new Athlete();
            athlete.setId(athleteId);
            athlete.setName(name);
            athlete.setGender(gender);
            parserResult.getAthleteList().add(athlete);

            Result result = new Result();
            result.setAthleteId(athlete.getId());
            result.setStageId(segmentId);
            result.setActivityId(activityId);
            result.setPosition(position);
            result.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy")));
            result.setTime(parseTime(time));
            parserResult.getResultList().add(result);

            list.add(result);
        }

        return parserResult;
    }

    private long parseTime(String time) {
        String[] split = time.split(":");
        if (split.length == 1) {
            return Long.parseLong(split[0]);
        } else if (split.length == 2) {
            return Long.parseLong(split[0]) * 60 + Long.parseLong(split[1]);
        } else {
            return Long.parseLong(split[0]) * 3600 + Long.parseLong(split[0]) * 60 + Long.parseLong(split[1]);
        }
    }
}
