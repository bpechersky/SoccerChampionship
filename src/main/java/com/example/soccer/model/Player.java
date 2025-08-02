package com.example.soccer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private int age;
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
