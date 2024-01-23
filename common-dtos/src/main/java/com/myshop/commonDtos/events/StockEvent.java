package com.myshop.commonDtos.events;

import com.myshop.commonDtos.dto.StockRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@NoArgsConstructor
@Data
public class StockEvent implements Event {

    private UUID eventId=UUID.randomUUID();
    private Date eventDate=new Date();
    private StockRequestDto stockRequestDto;
    private StockAvailabilityStatus stockavailabilityStatus;

    public StockEvent(StockRequestDto stockRequestDto, StockAvailabilityStatus stockavailabilityStatus) {
        this.stockRequestDto = stockRequestDto;
        this.stockavailabilityStatus = stockavailabilityStatus;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }
}
