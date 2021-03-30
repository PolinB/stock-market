package org.burtseva.sd.stock.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    @NotEmpty
    private String company;

    @Min(0)
    private long price;

    private long count;
}
