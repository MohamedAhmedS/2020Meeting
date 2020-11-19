package com.example.virtualmeetingapp.Model;

public class AppointmentModel {

    private long appointmentId;
    private String officerId;
    private String officerName;
    private String prisonerId;
    private String prisonerName;
    private String visitorId;
    private String visitorName;
    private String appointmentDate;
    private String appointmentTime;
    private boolean isChatApvByOfficer;

    public AppointmentModel() {
        appointmentId = System.currentTimeMillis();
        officerId = "";
        officerName = "";
        prisonerId = "";
        prisonerName = "";
        visitorId = "";
        visitorName = "";
        appointmentDate = "";
        appointmentTime = "";
        isChatApvByOfficer = false;

    }

    public AppointmentModel(long appointmentId, String prisonerId, String prisonerName, String officerId,
                            String officerName, String visitorId, String visitorName, String appointmentDate,
                            String appointmentTime, boolean isChatApvByOfficer) {
        this.appointmentId = appointmentId;
        this.officerId = officerId;
        this.officerName = officerName;
        this.prisonerId = prisonerId;
        this.prisonerName = prisonerName;
        this.visitorId = visitorId;
        this.visitorName = visitorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.isChatApvByOfficer = isChatApvByOfficer;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public boolean isChatApvByOfficer() {
        return isChatApvByOfficer;
    }

    public void setChatApvByOfficer(boolean chatApvByOfficer) {
        isChatApvByOfficer = chatApvByOfficer;
    }
}
