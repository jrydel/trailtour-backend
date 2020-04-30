package cz.jr.trailtour.backend.service;

import cz.jr.trailtour.backend.repository.SegmentRepository;
import cz.jr.trailtour.backend.repository.entity.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:26 PM
 */
@Service
public class SegmentService {

    private final SegmentRepository segmentRepository;

    @Autowired
    public SegmentService(SegmentRepository segmentRepository) {
        this.segmentRepository = segmentRepository;
    }

    public List<Stage> getAll() {
        return segmentRepository.findAll();
    }

    public Stage save(Stage stage) {
        return segmentRepository.saveAndFlush(stage);
    }

    public void delete(Stage stage) {
        segmentRepository.delete(stage);
    }
}
