package com.example.internet_store.models.mapper;

import com.example.internet_store.models.Manufacturer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManufacturerMapper implements RowMapper<Manufacturer> {
    @Override
    public Manufacturer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacurerId(rs.getInt("manufacturer_id"));
        manufacturer.setManufacturerName(rs.getString("manufacturer_name"));
        manufacturer.setRegistration_date(rs.getDate("registration_date"));
        return manufacturer;
    }
}
