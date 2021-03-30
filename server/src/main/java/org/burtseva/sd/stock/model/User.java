package org.burtseva.sd.stock.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty
    @Column(unique = true)
    private String login;

    @Min(0)
    private long money;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Stock> stocks;
}
