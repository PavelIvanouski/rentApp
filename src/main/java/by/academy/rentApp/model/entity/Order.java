package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ordr")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(name = "rent_begin")
    private OffsetDateTime rentBegin;
    @Column(name = "rent_end")
    private OffsetDateTime rentEnd;

    @Column(name = "creating_date")
    private OffsetDateTime creatingDate;

    @Column(name = "updating_date")
    private OffsetDateTime updatingDate;

    @Column
    private Integer price;

    @Column
    private String message;

    @Column
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Invoiсe> invoiсes;
}
