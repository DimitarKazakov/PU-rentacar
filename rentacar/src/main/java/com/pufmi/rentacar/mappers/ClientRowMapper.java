package com.pufmi.rentacar.mappers;

import com.pufmi.rentacar.models.Car;
import com.pufmi.rentacar.models.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new Client();
        entity.setId(rs.getInt("id"));
        entity.setName(rs.getString("name"));
        entity.setAddress(rs.getString("address"));
        entity.setPhone(rs.getString("phone"));
        entity.setAge(rs.getInt("age"));
        entity.setHasIncidents(rs.getBoolean("has_incidents"));

        return entity;
    }
}
