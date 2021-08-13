package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Brand brand;

    @Column(name = "creating_date")
    private OffsetDateTime creatingDate;

    @Column(name = "updating_date")
    private OffsetDateTime updatingDate;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars;




}
