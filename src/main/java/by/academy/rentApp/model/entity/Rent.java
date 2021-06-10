package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(name = "rent_begin")
    private Timestamp rentBegin;
    @Column(name = "rent_end")
    private Timestamp rentEnd;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

}
