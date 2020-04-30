package cz.jr.trailtour.backend.repository.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:39 PM
 */
@Repository
public class MysqlRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MysqlRepository.class);

    protected final HikariDataSource dataSource;

    @Autowired
    public MysqlRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T select(String sql, Object[] params, ResultSetHandler<T> handler) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return select(connection, sql, params, handler);
        }
    }

    public <T> T select(Connection connection, String sql, Object[] params, ResultSetHandler<T> handler) throws SQLException {
        LOG.debug("[{}] {}", sql, params);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            initParams(statement, params);
            try (ResultSet rs = statement.executeQuery()) {
                return handler.handle(rs);
            }
        }
    }

    public <T> T selectObject(String sql, Object[] params, ResultSetHandler<T> handler) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return selectObject(connection, sql, params, handler);
        }
    }

    public <T> T selectObject(Connection connection, String sql, Object[] params, ResultSetHandler<T> handler) throws SQLException {
        LOG.debug("[{}] {}", sql, params);
        return select(connection, sql, params, rs -> {
            if (rs.next()) {
                return handler.handle(rs);
            } else {
                return null;
            }
        });
    }

    public <T> List<T> selectList(String sql, Object[] params, ResultSetHandler<T> handler) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return selectList(connection, sql, params, handler);
        }
    }

    public <T> List<T> selectList(Connection connection, String sql, Object[] params, ResultSetHandler<T> handler) throws SQLException {
        LOG.debug("[{}] {}", sql, params);
        return select(connection, sql, params, rs -> {
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                T entity = handler.handle(rs);
                result.add(entity);
            }
            return result;
        });
    }

    public int execute(String sql, Object[] params) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return execute(connection, sql, params);
        }
    }

    public int execute(Connection connection, String sql, Object[] params) throws SQLException {
        LOG.debug("[{}] {}", sql, params);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            initParams(statement, params);
            return statement.executeUpdate();
        }
    }

    public static void initParams(PreparedStatement statement, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(1 + i, params[i]);
            }
        }
    }

    public String generateUpsert(String table, Param[] params) {
        List<String> columnSet = new ArrayList<>();
        List<String> valueSet = new ArrayList<>();
        List<String> updateSet = new ArrayList<>();

        for (Param param : params) {
            if (UpdateParam.class.isAssignableFrom(param.getClass())) {
                updateSet.add(param.getColumn() + " = " + param.getValue());
            } else {
                columnSet.add(param.getColumn());
                valueSet.add(param.getValue());
                if (param instanceof UpsertParam) {
                    UpsertParam upsertParam = (UpsertParam) param;
                    updateSet.add(upsertParam.getColumn() + " = " + upsertParam.getUpsertValue());
                }
            }
        }

        return "INSERT INTO " + table + " (" + String.join(", ", columnSet) + ") VALUES (" +
                String.join(", ", valueSet) + " ) " +
                (updateSet.isEmpty() ? "" : "ON DUPLICATE KEY UPDATE " + String.join(", ", updateSet));
    }
}