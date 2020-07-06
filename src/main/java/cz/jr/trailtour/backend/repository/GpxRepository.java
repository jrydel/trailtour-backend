package cz.jr.trailtour.backend.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * Created by Jiří Rýdel on 7/6/20, 11:38 AM
 */
@Repository
public class GpxRepository extends BaseRepository {

    public GpxRepository(HikariDataSource dataSource) {
        super(dataSource);
    }

    public Long saveGpx(String database, String name, byte[] content) throws SQLException {
        return executeReturnId("INSERT INTO " + database + ".gpx (name, content) VALUES (?, ?)", new Object[]{name, content});
    }

    public GpxEntity getGpx(String database, Long id) throws SQLException {
        return selectObject("SELECT name, content FROM " + database + ".gpx WHERE id = ?", new Object[]{id}, rs -> new GpxEntity(rs.getString("name"), rs.getBytes("content")));
    }

    public static class GpxEntity {
        private final String name;
        private final byte[] content;

        public GpxEntity(String name, byte[] content) {
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public byte[] getContent() {
            return content;
        }
    }
}
