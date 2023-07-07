package com.ezraloan.automation.entity;

import com.ezraloan.automation.Utils.APIUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Subscriber {

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

    //@Hidden
    public Long id;
    @Pattern(regexp = "^254\\\\d{9}$", message = "Invalid MSISDN")
    public String msisdn;
    public String firstName;
    public String lastName;
    public String email;
    public Boolean active;
    //Create a 1 to many relationship with loans requested. i.e one can make more than one loan request
    //@OneToMany(mappedBy = "subscriber")
    //public List<LendingRequest> loan;

    //Audit Fields
    @JsonFormat(pattern = "YYYY-MM-DD")
    public String dateOfBirth;

    @Hidden
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    public Date createdAt;

    @Hidden
    public String createdBy;

    @Hidden
    public String updatedBy;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    public Date updatedAt;
    @Column(insertable = false)
    @Hidden
    public Boolean isArchived = false;

    @Hidden
    public Date dateArchived;

    @Hidden
    public String archivedBy;

}
