package cn.meilituibian.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Project implements Serializable{
    private Long projectId;
    private Long categoryId;
    private String categoryName;
    private String shortDesc;
    private String detail;
    private String operation;
    private String treatment;
    private String recure;
    private String advantage;
    private String disadvantage;
    private String imgPath;
    private float safe;
    private float concern;
    private float complex;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int bookNum;

    private Long parentCategoryId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getRecure() {
        return recure;
    }

    public void setRecure(String recure) {
        this.recure = recure;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(String disadvantage) {
        this.disadvantage = disadvantage;
    }

    public float getSafe() {
        return safe;
    }

    public void setSafe(float safe) {
        this.safe = safe;
    }

    public float getConcern() {
        return concern;
    }

    public void setConcern(float concern) {
        this.concern = concern;
    }

    public float getComplex() {
        return complex;
    }

    public void setComplex(float complex) {
        this.complex = complex;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }
}
