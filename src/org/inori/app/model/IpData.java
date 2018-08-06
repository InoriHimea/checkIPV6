package org.inori.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2018/8/6 11:02
 * @since jdk1.8
 */
public class IpData {

    @JsonProperty("myip")
    private String myIp;

    @JsonProperty("ip")
    private IPSegment ipRange;

    private String location;

    private String country;

    private String local;

    public IpData() {

    }

    public IpData(String myIp, IPSegment ipRange, String location, String country, String local) {
        this.myIp = myIp;
        this.ipRange = ipRange;
        this.location = location;
        this.country = country;
        this.local = local;
    }

    public String getMyIp() {
        return myIp;
    }

    public void setMyIp(String myIp) {
        this.myIp = myIp;
    }

    public IPSegment getIpRange() {
        return ipRange;
    }

    public void setIpRange(IPSegment ipRange) {
        this.ipRange = ipRange;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "IpData{" +
                "myIp='" + myIp + '\'' +
                ", ipRange=" + ipRange +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}
