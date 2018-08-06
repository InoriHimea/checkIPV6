package org.inori.app.model;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2018/8/6 15:43
 * @since jdk1.8
 */
public class GoogleIp {

    private String ipv6;

    private String ipDns;

    private String type;

    private int delay;

    private int unknownA;

    private int unknownB;

    public GoogleIp() {

    }

    public GoogleIp(String ipv6, String ipDns, String type, int delay, int unknownA, int unknownB) {
        this.ipv6 = ipv6;
        this.ipDns = ipDns;
        this.type = type;
        this.delay = delay;
        this.unknownA = unknownA;
        this.unknownB = unknownB;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getIpDns() {
        return ipDns;
    }

    public void setIpDns(String ipDns) {
        this.ipDns = ipDns;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getUnknownA() {
        return unknownA;
    }

    public void setUnknownA(int unknownA) {
        this.unknownA = unknownA;
    }

    public int getUnknownB() {
        return unknownB;
    }

    public void setUnknownB(int unknownB) {
        this.unknownB = unknownB;
    }

    @Override
    public String toString() {
        return "GoogleIp{" +
                "ipv6='" + ipv6 + '\'' +
                ", ipDns='" + ipDns + '\'' +
                ", type='" + type + '\'' +
                ", delay=" + delay +
                ", unknownA=" + unknownA +
                ", unknownB=" + unknownB +
                '}';
    }
}
