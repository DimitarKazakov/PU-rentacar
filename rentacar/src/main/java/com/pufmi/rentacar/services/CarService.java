package com.pufmi.rentacar.services;

import com.pufmi.rentacar.mappers.CarRowMapper;
import com.pufmi.rentacar.models.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private JdbcTemplate db;

    public CarService(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Car data) {
        var query = new StringBuilder();
        query.append("INSERT INTO CARS ( MAKE, MODEL , LOCATION , PRICE_PER_DAY ) VALUES (")
                .append("'").append(data.getMake()).append("',")
                .append("'").append(data.getModel()).append("',")
                .append("'").append(data.getLocation()).append("',")
                .append(data.getPricePerDay())
                .append(");");

        this.db.execute(query.toString());

        return true;
    }

    public boolean update(int id, Car data) {
        var query = new StringBuilder();
        query.append("UPDATE CARS ")
                .append("SET MAKE = ?,")
                .append("MODEL = ?,")
                .append("LOCATION = ?,")
                .append("PRICE_PER_DAY = ? ")
                .append("WHERE ID = ?");

        return this.db.update(query.toString(), data.getMake(), data.getModel(),data.getLocation(), data.getPricePerDay(), id) > 0;
    }

    public boolean delete(int id) {
        var query = new StringBuilder();
        query.append("UPDATE CARS SET IS_DELETED=TRUE WHERE ID = ?");

        return this.db.update(query.toString(), id) > 0;
    }

    public List<Car> getAll(String location) {
        var query = new StringBuilder();
        query.append("SELECT * FROM CARS WHERE IS_DELETED=FALSE");
        if (!location.isBlank()) {
            query.append(" AND LOCATION='").append(location).append("'");
        }

        return this.db.query(query.toString(), new CarRowMapper());
    }

    public Car getSpecific(int id) {
        var query = new StringBuilder();
        query.append("SELECT * FROM CARS WHERE IS_DELETED=FALSE AND ID=").append(id);

        var result = this.db.query(query.toString(), new CarRowMapper());
        return result.isEmpty() ? null : result.getFirst();
    }
}
