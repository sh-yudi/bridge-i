package dev.utkarsh.bridgei.batch;

import dev.utkarsh.bridgei.model.CustomerCloud;
import dev.utkarsh.bridgei.model.CustomerLegacy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component // <--- THIS IS THE MAGIC WORD SPRING WAS LOOKING FOR!
public class CustomerProcessor implements ItemProcessor<CustomerLegacy, CustomerCloud> {

    @Override
    public CustomerCloud process(CustomerLegacy legacyItem) throws Exception {
        CustomerCloud modernItem = new CustomerCloud();

        modernItem.setId(legacyItem.getCustomerId());
        modernItem.setFullName(legacyItem.getCustomerName());

        String status = legacyItem.getStatusCode();
        if ("A".equalsIgnoreCase(status)) {
            modernItem.setAccountStatus("ACTIVE");
        } else if ("I".equalsIgnoreCase(status)) {
            modernItem.setAccountStatus("INACTIVE");
        } else {
            modernItem.setAccountStatus("UNKNOWN");
        }

        if (legacyItem.getJoinDate() != null && legacyItem.getJoinDate() > 0) {
            String dateString = String.valueOf(legacyItem.getJoinDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate parsedDate = LocalDate.parse(dateString, formatter);
            modernItem.setJoinedDate(parsedDate);
        }

        return modernItem;
    }
}