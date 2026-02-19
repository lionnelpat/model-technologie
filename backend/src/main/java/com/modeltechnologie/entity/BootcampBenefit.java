package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "bootcamp_benefits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BootcampBenefit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id", nullable = false)
    private Bootcamp bootcamp;

    @Column(name = "benefit_text", nullable = false, length = 500)
    private String benefitText;

    @Column(name = "display_order")
    private Integer displayOrder;
}