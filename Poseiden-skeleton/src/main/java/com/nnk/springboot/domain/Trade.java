package com.nnk.springboot.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Objects;


@Entity(name = "Trade")
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
    // DONE

    @Id
    @SequenceGenerator(
            name = "trade_sequence",
            sequenceName = "trade_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trade_sequence"
    )
    @Column(name = "trade_id", nullable = false)
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    private String type;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.1", message = "Bid Quantity must be greater than or equal to 0.1")
    @Column(name = "buy_quantity", precision = 6, scale = 1)
    private double buyQuantity;

    @Column(name = "sell_quantity", precision = 6, scale = 1)
    private double sellQuantity;

    @Column(name = "buy_price", precision = 6, scale = 1)
    private double buyPrice;

    @Column(name = "sell_price", precision = 6, scale = 1)
    private double sellPrice;

    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "trade_date")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Timestamp tradeDate;

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

    public Trade() {}

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
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

    public double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public double getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return Double.compare(trade.buyQuantity, buyQuantity) == 0 && tradeId.equals(trade.tradeId) && account.equals(trade.account) && type.equals(trade.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, account, type, buyQuantity);
    }
}
