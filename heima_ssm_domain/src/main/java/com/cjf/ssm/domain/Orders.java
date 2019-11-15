package com.cjf.ssm.domain;

import com.cjf.ssm.utils.DateUtils;

import java.util.Date;
import java.util.List;

//订单实体类
public class Orders {

    private int id;
    private String orderNum;
    private Date orderTime;
    private String orderTimeStr;
    private int orderStatus;
    private String orderStatusStr;
    private int peopleCount;
    //由于业务原因（本系统是旅游产品的后台管理），旅游订单里面产品（目的地--出发点--价格--描述）应该是只有一个的。
    private Product product;
    private List<Traveler> travellers;  //旅客
    private Member member;              //会员
    private Integer payType;
    private String payTypeStr;
    private String orderDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTimeStr() {
        this.orderTimeStr=DateUtils.date2String(this.orderTime,"yyyy-MM-dd HH:mm");
        return this.orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusStr() {
        //订单状态(0 未支付 1 已支付)
        if (this.orderStatus==0){
            orderStatusStr="未支付";
        }else  if (this.orderStatus==1){
            orderStatusStr="已支付";
        }
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Traveler> getTravellers() {
        return travellers;
    }

    public void setTravellers(List<Traveler> travellers) {
        this.travellers = travellers;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeStr() {
//        支付方式(0 支付宝 1 微信 2其它)
        if (payType==0){
            payTypeStr="支付宝";
        }else if (payType==1){
            payTypeStr="微信";
        }else if (payType==2){
            payTypeStr="其他";
        }
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderTime=" + orderTime +
                ", orderTimeStr='" + orderTimeStr + '\'' +
                ", orderStatus=" + orderStatus +
                ", peopleCount=" + peopleCount +
                ", product=" + product +
                ", travellers=" + travellers +
                ", member=" + member +
                ", payType=" + payType +
                ", payTypeStr='" + payTypeStr + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                '}';
    }
}
