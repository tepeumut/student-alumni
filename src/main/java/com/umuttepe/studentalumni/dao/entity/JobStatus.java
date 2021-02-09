package com.umuttepe.studentalumni.dao.entity;

import lombok.Getter;

@Getter
public enum JobStatus {
    FULLTIME("Tam Zamanlı"),
    PARTTIME("Yarı Zamanlı"),
    REMOTE("Remote"),
    PROJECT("Proje Bazlı"),
    INTERN("Stajyer");
    private final String status;
    JobStatus(String status) { this.status = status;}
}