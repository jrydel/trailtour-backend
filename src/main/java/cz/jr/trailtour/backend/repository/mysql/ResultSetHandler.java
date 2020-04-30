package cz.jr.trailtour.backend.repository.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jiří Rýdel on 3/11/20, 10:23 AM
 */
public interface ResultSetHandler<T> {

    T handle(ResultSet rs) throws SQLException;
}
