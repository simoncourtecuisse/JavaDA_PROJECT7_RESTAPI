package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity(name = "Curve Point")
@Table(name = "curve_point")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    // DONE

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

    @Column(name = "curve_id")
    @NotNull
    private Integer curveId;

    @Column(name = "as_of_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp asOfDate;

    @Column(name = "term")
    private double term;

    @Column(name = "value")
    private double value;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp creationDate;

    public CurvePoint() {}

    public CurvePoint(Integer curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
