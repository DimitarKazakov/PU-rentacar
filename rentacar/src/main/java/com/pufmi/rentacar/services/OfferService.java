package com.pufmi.rentacar.services;

import com.pufmi.rentacar.mappers.ClientRowMapper;
import com.pufmi.rentacar.mappers.OfferRowMapper;
import com.pufmi.rentacar.models.Client;
import com.pufmi.rentacar.models.Offer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OfferService {
    private JdbcTemplate db;

    public OfferService(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Offer data) {
        var query = new StringBuilder();
        query.append("INSERT INTO OFFERS ( ACCEPTED, START_DATE, ENT_DATE, FINAL_PRICE, CLIENT_ID, CAR_ID ) VALUES (")
                .append(data.getAccepted()).append(",")
                .append("'").append(data.getStartDate()).append("',")
                .append("'").append(data.getEndDate()).append("',")
                .append(data.getFinalPrice()).append(",")
                .append(data.getClientId()).append(",")
                .append(data.getCarId())
                .append(");");

        this.db.execute(query.toString());

        return true;
    }

    public boolean accept(int id) {
        var query = new StringBuilder();
        query.append("UPDATE OFFERS ")
                .append("SET ACCEPTED=TRUE ")
                .append("WHERE ID = ?");

        return this.db.update(query.toString(), id) > 0;
    }

    public boolean delete(int id) {
        var query = new StringBuilder();
        query.append("UPDATE OFFERS SET IS_DELETED=TRUE WHERE ID = ?");

        return this.db.update(query.toString(), id) > 0;
    }

    public List<Offer> getAll(int clientId) {
        var query = new StringBuilder();
        query.append("SELECT * FROM OFFERS JOIN CARS ON CARS.ID=OFFERS.CAR_ID JOIN CLIENTS ON CLIENTS.ID=OFFERS.CLIENT_ID WHERE OFFERS.IS_DELETED=FALSE AND OFFERS.CLIENT_ID=")
                .append(clientId);

        return this.db.query(query.toString(), new OfferRowMapper());
    }

    public Offer getSpecific(int id) {
        var query = new StringBuilder();
        query.append("SELECT * FROM OFFERS JOIN CARS ON CARS.ID=OFFERS.CAR_ID JOIN CLIENTS ON CLIENTS.ID=OFFERS.CLIENT_ID WHERE OFFERS.IS_DELETED=FALSE AND OFFERS.ID=")
                .append(id);

        var result = this.db.query(query.toString(), new OfferRowMapper());
        return result.isEmpty() ? null : result.getFirst();
    }

    public double calculateFinalPrice(Offer data) {
        var finalPrice = 0.0;
        var diffInMillies = Math.abs(data.getEndDate().getTime() - data.getStartDate().getTime());
        var diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        var weekendDays = getWeekendDays(data.getStartDate(), data.getEndDate());
        finalPrice += (diffDays - weekendDays) * data.getCar().getPricePerDay();
        finalPrice += weekendDays * data.getCar().getPricePerDay() * 1.1;

        if (data.getClient().getHasIncidents()) {
            finalPrice += 200;
        }
        
        return finalPrice;
    }

    public int getWeekendDays(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        int weekendDays = 0;

        while (! c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                weekendDays++;
            }

            c1.add(Calendar.DATE, 1);
        }

        return weekendDays;
    }
}
