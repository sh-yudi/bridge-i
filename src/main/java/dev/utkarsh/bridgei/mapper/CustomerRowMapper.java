package dev.utkarsh.bridgei.mapper;

import dev.utkarsh.bridgei.model.CustomerLegacy;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<CustomerLegacy> {

    @Override
    public CustomerLegacy mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerLegacy customer = new CustomerLegacy();

        customer.setCustomerId(rs.getInt("CUSTID"));

        // AS/400 CHAR fields often return with trailing spaces.
        // Always use .trim() when extracting string data from DB2 for i!
        String name = rs.getString("NAME");
        customer.setCustomerName(name != null ? name.trim() : null);

        customer.setStatusCode(rs.getString("STATUS"));
        customer.setJoinDate(rs.getInt("JOIN_DATE"));

        return customer;
    }
}