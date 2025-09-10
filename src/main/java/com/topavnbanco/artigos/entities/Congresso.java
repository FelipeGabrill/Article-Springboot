package com.topavnbanco.artigos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_congresso")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Congresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private String place;

    private Date submissionDeadline;

    private Date reviewDeadline;

    @OneToMany(mappedBy = "congresso")
    private List<User> user = new ArrayList<>();

    public Congresso() {
    }

}
