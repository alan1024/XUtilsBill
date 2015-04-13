package com.alan.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by aaa
 * User:alan
 * Date:15-4-12
 * Email:MyEmail
 */
@Table(name = "tb_type")
public class Category {
    @Id(column = "_id")
    private long id;
    @Column(column = "typeId")
    private String typeId;

    @Column(column = "type")
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
