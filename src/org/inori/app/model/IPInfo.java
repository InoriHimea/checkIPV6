package org.inori.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 封装返回的信息
 * @author InoriHimea
 * @date 2018/8/6
 * @since jdk1.8
 * @version 1.0
 */
public class IPInfo {

    private int code;

    @JsonProperty("data")
    private IpData ipData;

    private GoogleIp googleIp;

    public IPInfo() {

    }

    public IPInfo(int code, IpData ipData, GoogleIp googleIp) {
        this.code = code;
        this.ipData = ipData;
        this.googleIp = googleIp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IpData getIpData() {
        return ipData;
    }

    public void setIpData(IpData ipData) {
        this.ipData = ipData;
    }

    public GoogleIp getGoogleIp() {
        return googleIp;
    }

    public void setGoogleIp(GoogleIp googleIp) {
        this.googleIp = googleIp;
    }

    @Override
    public String toString() {
        return "IPInfo{" +
                "code=" + code +
                ", ipData=" + ipData +
                ", googleIp=" + googleIp +
                '}';
    }
}
