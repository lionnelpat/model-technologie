package com.modeltechnologie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BootcampResponseDTO {
    private String title; // correspond à name
    private String description;
    private String duration; // formaté à partir de durationDays/durationHours
    private String audience;
    private String prerequisites;
    private String price; // formaté à partir de priceFcfa
    private String nextSession;
    private List<String> benefits;
    private Boolean featured;
}