package com.jetsen.pack.optram.bean;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="SystemStatusReportResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemStatusReportResponse", propOrder = {
        "channelCode",
        "playDate",
        "systemStatus",
        "normalPackingTodayVersion",
        "specialPackingTodayVersion",
        "normalPackingNextDayVersion",
        "specialPackingNextDayVersion"
})
public class SystemStatusReportResponse {

    @XmlElement(name = "ChannelCode", required = true)
    protected String channelCode;
    @XmlElement(name = "PlayDate", required = true)
    protected String playDate;
    @XmlElement(name = "SystemStatus", required = true)
    protected String systemStatus;
    @XmlElement(name = "NormalPackingTodayVersion", required = true)
    protected String normalPackingTodayVersion;
    @XmlElement(name = "SpecialPackingTodayVersion", required = true)
    protected String specialPackingTodayVersion;
    @XmlElement(name = "NormalPackingNextDayVersion", required = true)
    protected String normalPackingNextDayVersion;
    @XmlElement(name = "SpecialPackingNextDayVersion", required = true)
    protected String specialPackingNextDayVersion;

    /**
     * 获取channelCode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 设置channelCode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    /**
     * 获取playDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPlayDate() {
        return playDate;
    }

    /**
     * 设置playDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPlayDate(String value) {
        this.playDate = value;
    }

    /**
     * 获取systemStatus属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSystemStatus() {
        return systemStatus;
    }

    /**
     * 设置systemStatus属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSystemStatus(String value) {
        this.systemStatus = value;
    }

    /**
     * 获取normalPackingTodayVersion属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNormalPackingTodayVersion() {
        return normalPackingTodayVersion;
    }

    /**
     * 设置normalPackingTodayVersion属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNormalPackingTodayVersion(String value) {
        this.normalPackingTodayVersion = value;
    }

    /**
     * 获取specialPackingTodayVersion属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpecialPackingTodayVersion() {
        return specialPackingTodayVersion;
    }

    /**
     * 设置specialPackingTodayVersion属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpecialPackingTodayVersion(String value) {
        this.specialPackingTodayVersion = value;
    }

    /**
     * 获取normalPackingNextDayVersion属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNormalPackingNextDayVersion() {
        return normalPackingNextDayVersion;
    }

    /**
     * 设置normalPackingNextDayVersion属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNormalPackingNextDayVersion(String value) {
        this.normalPackingNextDayVersion = value;
    }

    /**
     * 获取specialPackingNextDayVersion属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpecialPackingNextDayVersion() {
        return specialPackingNextDayVersion;
    }

    /**
     * 设置specialPackingNextDayVersion属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpecialPackingNextDayVersion(String value) {
        this.specialPackingNextDayVersion = value;
    }

}