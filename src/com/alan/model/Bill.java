package com.alan.model;

import android.preference.PreferenceCategory;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by aaa
 * User:alan
 * Date:on 15-4-12.
 * Email:
 */
@Table(name="tb_bill")
public class Bill {
    @Id(column="_id")
    private long id;
    @Column(column = "money",defaultValue = "0")
    private float money;
    @Column(column = "date")
    private String date;
    @Foreign(column = "typeId",foreign = "typeId")
    private Category category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
