package by.academy.rentApp.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "invoice")
public class Invoiсe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "serial_number", nullable = false)
    private Integer serialNumber;


    @Column(name = "creating_date")
    private OffsetDateTime creatingDate;

    @Column
    private String message;

    @Column(nullable = false)
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;

}
