package com.sw.server.planet;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanetMapper implements RowMapper<Planet> {
    @Override
    public Planet map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Planet(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("terrain"),
                rs.getString("climate"),
                rs.getInt("apparitions")
        );
    }
}