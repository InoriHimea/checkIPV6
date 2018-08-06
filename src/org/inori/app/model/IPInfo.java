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

    private String searchIp;

    private int delay;

    public IPInfo() {

    }

}
