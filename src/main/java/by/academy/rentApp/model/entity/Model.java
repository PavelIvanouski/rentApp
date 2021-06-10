package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(name = "seats_num", nullable = false)
    private int seatsNum;
    @Column(name = "engine_volume")
    private int engineVolume;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;
    @Column(name = "auto_transmission")
    private boolean autoTransmission;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<Car> cars;



}
