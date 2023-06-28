package com.ezraloan.automation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
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
    public String createdBy;
    public String updatedBy;
    public Date updatedAt;

    public Boolean archived; //Used to mean deleted/ sweeped due to defaulting of repayment
    public Date archiveDate;
    public String archivedBy;

}
