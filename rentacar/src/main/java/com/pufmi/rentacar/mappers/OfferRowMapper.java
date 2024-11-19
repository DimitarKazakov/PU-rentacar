package com.pufmi.rentacar.mappers;

import com.pufmi.rentacar.models.Car;
import com.pufmi.rentacar.models.Client;
import com.pufmi.rentacar.models.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new Offer();
        entity.setId(rs.getInt("id"));
        entity.setAccepted(rs.getBoolean("accepted"));
        entity.setStartDate(rs.getDate("start_date"));
        entity.setEndDate(rs.getDate("ent_date"));
        entity.setFinalPrice(rs.getDouble("final_price"));

        var car = new Car();
        car.setId(rs.getInt("car_id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setLocation(rs.getString("location"));
        car.setPricePerDay(rs.getDouble("price_per_day"));
        entity.setCar(car);
        entity.setCarId(rs.getInt("car_id"));

        var client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setHasIncidents(rs.getBoolean("has_incidents"));
        client.setAge(rs.getInt("age"));
        client.setName(rs.getString("name"));
        client.setPhone(rs.getString("phone"));
        client.setAddress(rs.getString("address"));
        entity.setClient(client);
        entity.setClientId(rs.getInt("client_id"));

        return entity;
    }
}
