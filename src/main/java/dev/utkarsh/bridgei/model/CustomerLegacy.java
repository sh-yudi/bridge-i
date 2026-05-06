package dev.utkarsh.bridgei.model;

import lombok.Data;

@Data // Lombok automatically generates Getters, Setters, and toString()
public class CustomerLegacy {
    private Integer customerId;
    private String customerName;
    private String statusCode;
    private Integer joinDate; // AS/400 often stores dates as YYYYMMDD integers
}