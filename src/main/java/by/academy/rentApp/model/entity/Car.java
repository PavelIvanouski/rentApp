package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(nullable = false)
    private String vin;
    @Column(name = "state_num", nullable = false)
    private String stateNum;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String year;
    @Column(name = "climate_control")
    private boolean climateControl;
    @Column(name = "cruise_control")
    private boolean cruiseControl;
    @Column(name = "panoramic_roof")
    private boolean panoramicRoof;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Rent> rentList;

}
