package com.ezraloan.automation.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;
@Getter
@Setter
@Entity
public class MaxLoanAgeConfig {
    @Id
    @SequenceGenerator(
            name = "subscriber_sequence",
            sequenceName = "subscriber_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "subscriber_sequence"
    )

    @Hidden
    public Long id;
    public Long maxLoanAge;
    public String comment;
}
