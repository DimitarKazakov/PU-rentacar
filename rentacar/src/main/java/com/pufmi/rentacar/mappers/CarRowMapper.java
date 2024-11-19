package com.pufmi.rentacar.mappers;

import com.pufmi.rentacar.models.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new Car();
        entity.setId(rs.getInt("id"));
        entity.setMake(rs.getString("make"));
        entity.setModel(rs.getString("model"));
        entity.setLocation(rs.getString("location"));
        entity.setPricePerDay(rs.getDouble("price_per_day"));

        return entity;
    }
}
