package org.demis.comics.data.jpa.entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractEntity {

    private Date created;
    private Date updated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date", nullable = false, updatable = true)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, updatable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
