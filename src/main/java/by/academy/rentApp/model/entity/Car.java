package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false, length = 17)
    private String vin;

    @Column(name = "state_num", nullable = false)
    private String stateNum;

    @Column(nullable = false)
    private String year;

    @Column(name = "engine_volume")
    private int engineVolume;

    @Column(name = "seats_num", nullable = false)
    private int seatsNum;

    @Column(name = "auto_transmission")
    private boolean autoTransmission;

    @Column(name = "creating_date")
    private Timestamp creatingDate;

    @Column(name = "updating_date")
    private Timestamp updatingDate;

    @Column
    private String color;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = true, length = 64)
    private String photos;

//    @Column(name = "climate_control")
//    private boolean climateControl;
//    @Column(name = "cruise_control")
//    private boolean cruiseControl;
//    @Column(name = "panoramic_roof")
//    private boolean panoramicRoof;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "model_id", nullable = false)
//    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orderList;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/car-photos/" + id + "/" + photos;
    }

}
