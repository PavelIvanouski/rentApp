package by.academy.rentApp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Rent> rentList;

}
