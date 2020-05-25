package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import cz.jr.trailtour.backend.repository.mysql.MysqlRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by Jiří Rýdel on 5/20/20, 10:56 AM
 */
@Repository
public class BaseRepository extends MysqlRepository {

    public BaseRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public LocalDateTime getLastResultUpdate(String database) throws SQLException {
        return selectObject("SELECT MAX(timestamp) as max FROM " + database + ".strava_requests", new Object[]{}, rs -> rs.getTimestamp("max").toLocalDateTime());
    }
}
