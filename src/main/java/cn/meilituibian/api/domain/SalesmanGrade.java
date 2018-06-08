package cn.meilituibian.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesmanGrade implements Serializable{
    private Long id;
    private int titleCode;
    private String titleName;
    private int months;
    private BigDecimal amountMoney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(int titleCode) {
        this.titleCode = titleCode;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public BigDecimal getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(BigDecimal amountMoney) {
        this.amountMoney = amountMoney;
    }
}
