package com.ezraloan.automation.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class LoanProduct {
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
    public float interestRate;
    @Column(unique = true)
    public String code;
    public String name;
    public String description;
    public String maximumLoanAgeInmonths;
    public float minimumLoanAmount;

    //Audit Fields
    @Hidden
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    public Date createdAt;

    @Hidden
    public String createdBy;

    @Hidden
    public String updatedBy;

    @Hidden
    public Date updatedAt;

}
