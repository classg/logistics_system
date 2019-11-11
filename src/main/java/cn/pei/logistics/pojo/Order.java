package cn.pei.logistics.pojo;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 8932110282247397107L;
    private Long orderId;

    private Long userId;

    private Long customerId;

    private String shippingAddress;

    private String shippingName;

    private String shippingPhone;

    private String takeName;

    private String takeAddress;

    private String takePhone;

    private Integer orderStatus;

    private Long paymentMethodId;

    private Long intervalId;

    private Long takeMethodId;

    private Long freightMethodId;

    private String orderRemark;

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress == null ? null : shippingAddress.trim();
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone == null ? null : shippingPhone.trim();
    }

    public String getTakeName() {
        return takeName;
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName == null ? null : takeName.trim();
    }

    public String getTakeAddress() {
        return takeAddress;
    }

    public void setTakeAddress(String takeAddress) {
        this.takeAddress = takeAddress == null ? null : takeAddress.trim();
    }

    public String getTakePhone() {
        return takePhone;
    }

    public void setTakePhone(String takePhone) {
        this.takePhone = takePhone == null ? null : takePhone.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Long getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(Long intervalId) {
        this.intervalId = intervalId;
    }

    public Long getTakeMethodId() {
        return takeMethodId;
    }

    public void setTakeMethodId(Long takeMethodId) {
        this.takeMethodId = takeMethodId;
    }

    public Long getFreightMethodId() {
        return freightMethodId;
    }

    public void setFreightMethodId(Long freightMethodId) {
        this.freightMethodId = freightMethodId;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }
}