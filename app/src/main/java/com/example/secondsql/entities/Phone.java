package com.example.secondsql.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;


@Entity(tableName = "PhonesDB")
public class Phone {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "Producer")
    private String producer;

    @NonNull
    @ColumnInfo(name = "Model")
    private String model;

    @ColumnInfo(name = "AndroidVersion")
    private String androidVer;

    @ColumnInfo(name = "WebPage")
    private String web;

    public Phone(Long id, @NonNull String producer, @NonNull String model, String androidVer, String web) {
        this.id = id;
        this.producer = producer;
        this.model = model;
        this.androidVer = androidVer;
        this.web = web;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
