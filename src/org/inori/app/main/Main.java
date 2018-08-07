package org.inori.app.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.inori.app.model.GoogleIp;
import org.inori.app.model.IPInfo;
import org.inori.app.model.IpData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2018/8/6 11:08
 * @since jdk1.8
 */
public class Main {

    private static final String API_URL = "http://ip.zxinc.org/api.php?type=json&ip={ip}";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 主执行程序
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<GoogleIp> googleIpList = readFromFile("D:/ipv6_list.txt");

        Map<String, List<IPInfo>> countryMap = getIpInfo2MapByArea(googleIpList);

        write2File(countryMap);
    }

    /**
     * 查询ip信息，并将信息按照IP地址的区域存入Map
     * @param googleIpList
     * @return
     * @throws IOException
     */
    private static Map<String, List<IPInfo>> getIpInfo2MapByArea(List<GoogleIp> googleIpList) throws IOException {
        Map<String, List<IPInfo>> countryMap = new LinkedHashMap<String, List<IPInfo>>();
        int i = 1;
        for (GoogleIp googleIp : googleIpList) {
            String ipInfo_str = getIPInfo(googleIp.getIpv6());
            mapper.writerWithDefaultPrettyPrinter();
            IPInfo ipInfo = mapper.readValue(ipInfo_str, IPInfo.class);
            ipInfo.setGoogleIp(googleIp);

            IpData ipData = ipInfo.getIpData();
            String country = ipData.getCountry();

            List<IPInfo> ipInfoList = countryMap.get(country);
            if (ipInfoList == null) {
                ipInfoList = new ArrayList<IPInfo>();
                countryMap.put(country, ipInfoList);
            }
            ipInfoList.add(ipInfo);
            System.out.println("---查询完成第" + i + "个IP地址---");
            i ++;
        }
        return countryMap;
    }

    /**
     * 将整理的ipv6地址按地区分文件夹显示
     * @param countryMap
     * @throws IOException
     */
    private static void write2File(Map<String, List<IPInfo>> countryMap) throws IOException {
        for (String key : countryMap.keySet()) {
            File xx_net = new File("D:/xx-net/ipv6/" + key, "/ipv6_list.txt");
            File xx_net_detail = new File("D:/xx-net/ipv6/" + key, "ipv6_list_detail.txt");

            File xx_net_parent = xx_net.getParentFile();
            if (! xx_net_parent.exists()) {
                xx_net_parent.mkdirs();
            }
            xx_net.createNewFile();

            File xx_net_detail_parent = xx_net_detail.getParentFile();
            if (! xx_net_detail_parent.exists()) {
                xx_net_detail_parent.mkdirs();
            }
            xx_net_detail.createNewFile();

            try (FileWriter xx_net_writer = new FileWriter(xx_net);
                    FileWriter xx_net_detail_writer = new FileWriter(xx_net_detail)) {

                int index = 1;
                List<IPInfo> ipInfoList = countryMap.get(key);
                for (IPInfo ipInfo : ipInfoList) {

                    /**
                     * 按照原始格式输出成文件
                     */
                    GoogleIp googleIp = ipInfo.getGoogleIp();
                    IpData ipData = ipInfo.getIpData();

                    StringBuffer sb = new StringBuffer();
                    sb.append(googleIp.getIpv6());
                    sb.append(" ");
                    sb.append(googleIp.getIpDns());
                    sb.append(" ");
                    sb.append(googleIp.getType());
                    sb.append(" ");
                    sb.append(googleIp.getDelay());
                    sb.append(" ");
                    sb.append(googleIp.getUnknownA());
                    sb.append(" ");
                    sb.append(googleIp.getUnknownB());

                    if (index < ipInfoList.size()) {
                        sb.append("\n");
                    }

                    //写出到源文件
                    xx_net_writer.write(sb.toString());

                    /**
                     * 输出详细信息到文本
                     */
                    StringBuffer sb1 = new StringBuffer();
                    sb1.append(index + "：");
                    sb1.append("\r\n");
                    sb1.append("\t查询的IP：");
                    sb1.append(googleIp.getIpv6());
                    sb1.append("\r\n");
                    sb1.append("\tDNS：");
                    sb1.append(googleIp.getIpDns());
                    sb1.append("\r\n");
                    sb1.append("\t类型：");
                    sb1.append(googleIp.getType());
                    sb1.append("\r\n");
                    sb1.append("\t延迟：");
                    sb1.append(googleIp.getDelay());
                    sb1.append("\r\n");
                    sb1.append("\t查询机器的IP地址：");
                    sb1.append("\r\n");
                    sb1.append("\tIP所在地址段的开始地址：");
                    sb1.append(ipData.getIpRange().getStartIp());
                    sb1.append("\r\n");
                    sb1.append("\tIP所在地址段的结束地址：");
                    sb1.append(ipData.getIpRange().getEndIp());
                    sb1.append("\r\n");
                    sb1.append("\t所在地区：");
                    sb1.append(ipData.getLocation());
                    sb1.append("\r\n");
                    sb1.append("\t所在国家：");
                    sb1.append(ipData.getCountry());
                    sb1.append("\r\n");
                    sb1.append("\t其他参考数据：");
                    sb1.append(ipData.getLocal());
                    sb1.append("\r\n");

                    //写入到详细信息文本中
                    xx_net_detail_writer.write(sb1.toString());

                    index ++;
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } finally {
                System.out.println("---关闭FileWriter---");
            }
        }

        System.out.println("---全部写入完成---");
    }

    /**
     * 通过java的HttpURLConnection的方式
     * @param ip
     * @return
     * @throws IOException
     */
    private static String getIPInfo(String ip) throws IOException {
        String searchUrl = API_URL.replace("{ip}", ip);
        URL url = new URL(searchUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");

        InputStream inputStream = conn.getInputStream();
        StringBuffer sb = new StringBuffer();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 从文件读取地址信息存入内存中的list中
     * @param filePath
     * @return
     * @throws IOException
     */
    private static List<GoogleIp> readFromFile(String filePath) throws IOException {
        File ipFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(ipFile));
        String str;
        List<GoogleIp> ipList = new LinkedList<GoogleIp>();
        while ((str = reader.readLine()) != null) {
            String[] lineStr = str.split(" ");

            GoogleIp googleIp = new GoogleIp();
            googleIp.setIpv6(lineStr[0]);
            googleIp.setIpDns(lineStr[1]);
            googleIp.setType(lineStr[2]);
            googleIp.setDelay(Integer.parseInt(lineStr[3]));
            googleIp.setUnknownA(Integer.parseInt(lineStr[4]));
            googleIp.setUnknownB(Integer.parseInt(lineStr[5]));
            ipList.add(googleIp);
        }
        return ipList;
    }
}
