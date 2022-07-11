package com.nnk.springboot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * JPA entity: CurvePoint - MySQL table: curve_point.
 *
 * @author SimonC.
 * @version 1.0
 */
@Entity(name = "curvePoint")
@Table(name = "curve_point")
public class CurvePoint {
    // DONE: Map columns in data table CURVEPOINT with corresponding java fields

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
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "curve_id")
    @NotNull(message = "Curve Id must not be null")
    private Integer curveId;

    @Column(name = "as_of_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp asOfDate;

    @Column(name = "term", precision = 6, scale = 1)
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.1", message = "Term must be greater than or equal to 0.1")
    private double term;

    @Column(name = "value", precision = 6, scale = 1)
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.1", message = "Value must be greater than or equal to 0.1")
    private double value;

    @Column(name = "creation_date")
//    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Timestamp creationDate;

    public CurvePoint() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurvePoint)) return false;
        CurvePoint that = (CurvePoint) o;
        return Double.compare(that.term, term) == 0 && Double.compare(that.value, value) == 0 && id.equals(that.id) && curveId.equals(that.curveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, curveId, term, value);
    }
}
