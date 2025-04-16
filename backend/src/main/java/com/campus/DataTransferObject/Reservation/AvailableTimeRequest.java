package com.campus.DataTransferObject.Reservation;

import com.campus.DataTransferObject.Resource.ResourceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AvailableTimeRequest {
    private ResourceDTO resourceDTO;
    private LocalDate localDate;
}
