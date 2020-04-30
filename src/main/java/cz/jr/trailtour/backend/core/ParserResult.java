package cz.jr.trailtour.backend.core;

import cz.jr.trailtour.backend.repository.entity.Athlete;
import cz.jr.trailtour.backend.repository.entity.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:08 PM
 */
public class ParserResult {

    private final List<Athlete> athleteList = new ArrayList<>();
    private final List<Result> resultList = new ArrayList<>();

    public List<Athlete> getAthleteList() {
        return athleteList;
    }

    public List<Result> getResultList() {
        return resultList;
    }
}
