package base.utils.sqllite;

import java.sql.ResultSet;

public interface ResultSetExtractor<T> {
    T extractData(ResultSet rs);
}
