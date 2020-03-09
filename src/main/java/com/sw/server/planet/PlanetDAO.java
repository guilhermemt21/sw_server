package com.sw.server.planet;

import com.sw.server.command.PlanetCommand;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(PlanetMapper.class)
public interface PlanetDAO {

    @SqlUpdate("INSERT INTO planets (name, terrain, climate, apparitions) " +
            " VALUES (:planet.name, :planet.terrain, :planet.climate, :apparitions) " +
            " ON DUPLICATE KEY UPDATE terrain=VALUES(terrain), climate=VALUES(climate), apparitions=VALUES(apparitions)")
    void insertPlanet(@BindBean("planet") PlanetCommand planet, @Bind("apparitions") Integer apparitions);

    @SqlQuery("SELECT * FROM planets")
    List<Planet> findAllPlanets();

    @SqlQuery("SELECT * FROM planets WHERE id = :id")
    Planet findPlanet(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM planets WHERE name = :name")
    Planet findPlanet(@Bind("name") String name);

    @SqlUpdate("DELETE FROM planets WHERE id = :id")
    void deletePlanet(@Bind("id") Long id);
}
