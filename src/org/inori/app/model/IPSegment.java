package org.inori.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2018/8/6 11:03
 * @since jdk1.8
 */
public class IPSegment {

    @JsonProperty("start")
    private String startIp;

    @JsonProperty("end")
    private String endIp;

    public IPSegment() {

    }

    public IPSegment(String startIp, String endIp) {
        this.startIp = startIp;
        this.endIp = endIp;
    }

    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    @Override
    public String toString() {
        return "IPSegment{" +
                "startIp='" + startIp + '\'' +
                ", endIp='" + endIp + '\'' +
                '}';
    }
}
