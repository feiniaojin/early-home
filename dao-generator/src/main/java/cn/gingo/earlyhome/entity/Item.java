package cn.gingo.earlyhome.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
    private Long id;

    private BigDecimal price;

    private Integer stock;

    private String title;

    private Long merchantId;

    private Date createTime;

    private Date updateTime;

    private Date offSalesTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOffSalesTime() {
        return offSalesTime;
    }

    public void setOffSalesTime(Date offSalesTime) {
        this.offSalesTime = offSalesTime;
    }
}