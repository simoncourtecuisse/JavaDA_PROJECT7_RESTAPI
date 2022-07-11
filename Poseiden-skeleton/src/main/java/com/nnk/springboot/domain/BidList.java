package com.nnk.springboot.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * JPA entity: BidList - MySQL table: bid_list.
 *
 * @author SimonC.
 * @version 1.0
 */
@Entity(name = "bidList")
@Table(name = "bid_list")
public class BidList {
    // DONE: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @SequenceGenerator(
            name = "bid_list_sequence",
            sequenceName = "bid_list_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bid_list_sequence"
    )
    @Column(name = "bid_list_id", nullable = false)
    private Integer bidListId;

    @Column(name = "account")
    @NotBlank(message = "Account is mandatory") // import javax.validation.constraints.NotBlank; already in Imports -> Unused import statement
    private String account;

    @Column(name = "type")
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "bid_quantity", precision = 6, scale = 1) // precision scale ignore -> @digits. why ?
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.1", message = "Bid Quantity must be greater than or equal to 0.1")
    //@NotNull(message = "Bid Quantity can't be null")
    private double bidQuantity;

    @Column(name = "ask_quantity", precision = 6, scale = 1)
    private double askQuantity;

    @Column(name = "bid", precision = 6, scale = 1)
    private double bid;

    @Column(name = "ask", precision = 6, scale = 1)
    private double ask;

    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "bid_list_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp bidListDate;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "security")
    private String security;

    @Column(name = "status")
    private String status;

    @Column(name = "trader")
    private String trader;

    @Column(name = "book")
    private String book;

    @Column(name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp creationDate;

    @Column(name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp revisionDate;

    @Column(name = "deal_name")
    private String dealName;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "source_list_id")
    private String sourceListId;

    @Column(name = "side")
    private String side;

    public BidList() {
    }

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public Integer getBidListId() {
        return bidListId;
    }

    public void setBidListId(Integer bidListId) {
        this.bidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "BidList{" +
                "bidListId=" + bidListId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", bidQuantity=" + bidQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BidList)) return false;
        BidList bidList = (BidList) o;
        return Double.compare(bidList.bidQuantity, bidQuantity) == 0 && bidListId.equals(bidList.bidListId) && account.equals(bidList.account) && type.equals(bidList.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidListId, account, type, bidQuantity);
    }
}
