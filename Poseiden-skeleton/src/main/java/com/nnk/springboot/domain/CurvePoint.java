package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity(name = "Curve Point")
@Table(name = "curve_point")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @SequenceGenerator(
            name = "curve_point_sequence",
            sequenceName = "curve_point_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "curve_point_sequence"
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "moody_rating")
    private String moodyRating;

}
