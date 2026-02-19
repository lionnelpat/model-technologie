package com.modeltechnologie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootcampUpdateDTO {

    @Size(max = 2000)
    private String title;

    @Size(max = 2000)
    private String description;

    @Min(1)
    @Max(30)
    private Integer durationDays;

    @Min(1)
    @Max(200)
    private Integer durationHours;

    @Size(max = 500)
    private String audience;

    @Size(max = 500)
    private String prerequisites;

    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})? [A-Z]+$")
    private String price;

    private Boolean featured;

    private List<@NotBlank String> benefits;

    private String status;
}