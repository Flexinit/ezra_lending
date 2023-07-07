package com.ezraloan.automation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@ToString
@Getter
@Setter
@Entity
public class LendingRequest {
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
    //@ManyToOne
    //@JoinColumn(name = "subscriber_id") //subscriber_id is the foreign key column here
    public Long subscriberId;
    public String loanProductId;
    public float amountDue;
    @JsonFormat(pattern = "YYYY-MM-DD")
    public Date dateOfRepayment;


    //@OneToMany(mappedBy = "lendingRequest")
    //private List<RepaymentRequest> repayments;

    //Audit Fields
    @Hidden
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    public LocalDate createdAt;
    @Hidden
    public String createdBy;
    @Hidden
    public String updatedBy;
    @Hidden
    public Date updatedAt;

    @Hidden
    @Value("${myapp.feature.enabled:false}")
    public Boolean archived; //Used to mean deleted/ sweeped due to defaulting of repayment

    @Hidden
    public Date archiveDate;
    @Hidden
    public String archivedBy;

}
