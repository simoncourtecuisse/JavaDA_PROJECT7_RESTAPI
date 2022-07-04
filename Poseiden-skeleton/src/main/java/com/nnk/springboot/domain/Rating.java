package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity(name = "Rating")
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    // DONE

    @Id
    @SequenceGenerator(
            name = "rating_sequence",
            sequenceName = "rating_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rating_sequence"
    )
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "MoodyRating is mandatory")
    @Column(name = "moody_rating")
    private String moodyRating;

    @NotBlank(message = "SandPRating is mandatory")
    @Column(name = "sand_p_rating")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    @Column(name = "fitch_rating")
    private String fitchRating;

    @NotNull(message = "OrderNumber must not be null")
    @Column(name = "order_Number")
    private Integer orderNumber;

    public Rating() {}

    public Rating(String moodyRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodyRating = moodyRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodyRating() {
        return moodyRating;
    }

    public void setMoodyRating(String moodyRating) {
        this.moodyRating = moodyRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return id.equals(rating.id) && moodyRating.equals(rating.moodyRating) && sandPRating.equals(rating.sandPRating) && fitchRating.equals(rating.fitchRating) && orderNumber.equals(rating.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moodyRating, sandPRating, fitchRating, orderNumber);
    }
}
