package com.bazigar.bulandawaaz.report;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String objectType;
    private Long objectId;
    private Long reportedUserId;
    private Long reportingUserId;
    private String reportType;

    public Report() {
    }

    public Report(String objectType, Long objectId, Long reportedUserId, Long reportingUserId, String reportType) {
        this.objectType = objectType;
        this.objectId = objectId;
        this.reportedUserId = reportedUserId;
        this.reportingUserId = reportingUserId;
        this.reportType = reportType;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Long getReportingUserId() {
        return reportingUserId;
    }

    public void setReportingUserId(Long reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public Long getId() {
        return id;
    }



    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
