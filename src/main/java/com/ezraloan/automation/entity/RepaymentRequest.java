package com.ezraloan.automation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class RepaymentRequest {
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
    //@JoinColumn(name = "lending_request_id")
    //private LendingRequest lendingRequest;
    private Long lendingRequestId;
    public float amountPaid;
    public float amountDue;
    public Long subscriberId;

   //Audit Fields
   @Hidden
   @JsonFormat(pattern = "YYYY-MM-DD")
    public Date createdAt;
    public String createdBy;
    public String updatedBy;
    @JsonFormat(pattern = "YYYY-MM-DD")
    public Date updatedAt;

}
