package com.ninjacart.task_mgmt_service.entity;
import com.ninjacart.task_mgmt_service.model.User;
import com.ninjacart.task_mgmt_service.model.AsgardUser;
import jakarta.persistence.Column;
import com.ninjacart.task_mgmt_service.model.enums.CommonEnum;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<PK extends Serializable> implements GenericEntity<PK>,Cloneable {

    @Column(name = "CreatedAt")
    @CreatedDate
    private Date createdAt;

    @Column(name = "UpdatedAt")
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "CreatedBy")
    @CreatedBy
    private int createdBy;

    @Column(name = "UpdatedBy")
    @LastModifiedBy
    private int updatedBy;

    @Column(name = "Deleted")
    private byte deleted;

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public byte getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

}