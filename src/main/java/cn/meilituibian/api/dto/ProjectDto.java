package cn.meilituibian.api.dto;

import cn.meilituibian.api.domain.Category;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectDto implements Serializable{
    private Long projectId;
    private Long categoryId;
    private String categoryName;
    private String shortDesc;
    private String detail;
    private String imgPath;
    private JsonNode operation;
    private JsonNode treatment;
    private JsonNode recure;
    private JsonNode advantage;
    private JsonNode disadvantage;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private float safe;
    private float concern;
    private float complex;
    private String content;

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

    public JsonNode getOperation() {
        return operation;
    }

    public void setOperation(JsonNode operation) {
        this.operation = operation;
    }

    public JsonNode getTreatment() {
        return treatment;
    }

    public void setTreatment(JsonNode treatment) {
        this.treatment = treatment;
    }

    public JsonNode getRecure() {
        return recure;
    }

    public void setRecure(JsonNode recure) {
        this.recure = recure;
    }

    public JsonNode getAdvantage() {
        return advantage;
    }

    public void setAdvantage(JsonNode advantage) {
        this.advantage = advantage;
    }

    public JsonNode getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(JsonNode disadvantage) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
