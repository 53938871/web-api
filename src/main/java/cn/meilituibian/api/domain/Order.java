package cn.meilituibian.api.domain;

import cn.meilituibian.api.common.OrderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Long id;
    @NotNull(message = "项目Id不能为空")
    private Long projectId;
    @NotNull(message = "openId不能为空")
    //private String openId;
    private String guid;
    private String parentGuid;
    private String userName;
    private String projectName;
    @NotNull(message = "联系电话不能为空")
    private String phone;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String address;
    @JsonIgnore
    private String postCode;
    @JsonIgnore
    private int status; //-1:冻结,0:待处理,1:处理中，2:处理完成
    private String orderStatus;
    private String orderNo; //订单号

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateDate;

    @NotNull(message = "预约日期不能为空")
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date subscribeDate; //下单时间
    private String parentOpenId; //推荐人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getParentOpenId() {
        return parentOpenId;
    }

    public void setParentOpenId(String parentOpenId) {
        this.parentOpenId = parentOpenId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(Date subscribeDate) {
        this.subscribeDate = subscribeDate;
    }


    public String getOrderStatus() {
        return OrderEnum.getOrderStatus(status);
    }

/*    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }*/

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }
}
