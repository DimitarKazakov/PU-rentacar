package com.pufmi.rentacar.services;

import com.pufmi.rentacar.mappers.ClientRowMapper;
import com.pufmi.rentacar.models.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private JdbcTemplate db;

    public ClientService(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Client data) {
        var query = new StringBuilder();
        query.append("INSERT INTO CLIENTS ( NAME, ADDRESS , PHONE , AGE, HAS_INCIDENTS ) VALUES (")
                .append("'").append(data.getName()).append("',")
                .append("'").append(data.getAddress()).append("',")
                .append("'").append(data.getPhone()).append("',")
                .append(data.getAge()).append(",")
                .append(data.getHasIncidents())
                .append(");");

        this.db.execute(query.toString());

        return true;
    }

    public boolean update(int id, Client data) {
        var query = new StringBuilder();
        query.append("UPDATE CLIENTS ")
                .append("SET NAME = ?,")
                .append("ADDRESS = ?,")
                .append("PHONE = ?,")
                .append("AGE = ?,")
                .append("HAS_INCIDENTS = ? ")
                .append("WHERE ID = ?");

        return this.db.update(query.toString(), data.getName(), data.getAddress(),data.getPhone(), data.getAge(),data.getHasIncidents(), id) > 0;
    }

    public boolean delete(int id) {
        var query = new StringBuilder();
        query.append("UPDATE CLIENTS SET IS_DELETED=TRUE WHERE ID = ?");

        return this.db.update(query.toString(), id) > 0;
    }

    public List<Client> getAll() {
        var query = new StringBuilder();
        query.append("SELECT * FROM CLIENTS WHERE IS_DELETED=FALSE");

        return this.db.query(query.toString(), new ClientRowMapper());
    }

    public Client getSpecific(int id) {
        var query = new StringBuilder();
        query.append("SELECT * FROM CLIENTS WHERE IS_DELETED=FALSE AND ID=").append(id);

        var result = this.db.query(query.toString(), new ClientRowMapper());
        return result.isEmpty() ? null : result.getFirst();
    }

    public Client getByNamePhone(String name, String phone) {
        var query = new StringBuilder();
        query.append("SELECT * FROM CLIENTS WHERE IS_DELETED=FALSE AND NAME='").append(name).append("' AND PHONE='").append(phone).append("';");

        var result = this.db.query(query.toString(), new ClientRowMapper());
        return result.isEmpty() ? null : result.getFirst();
    }
}
