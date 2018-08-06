package org.inori.app.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
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

    public static void main(String[] args) throws IOException {
        List<GoogleIp> googleIpList = readFromFile("D:/ipv6_list.txt");


        Map<String, List<IPInfo>> countryMap = new LinkedHashMap<String, List<IPInfo>>();
        int i = 1;
        for (GoogleIp googleIp : googleIpList) {
            String ipInfo_str = getIPInfo(googleIp.getIpv6());
            mapper.writerWithDefaultPrettyPrinter();
            IPInfo ipInfo = mapper.readValue(ipInfo_str, IPInfo.class);
            ipInfo.setSearchIp(googleIp.getIpv6());

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

        write2File(countryMap);

    }

    private static void write2File(Map<String, List<IPInfo>> countryMap) throws IOException {
        for (String key : countryMap.keySet()) {
            File file = new File("D:/xx-net/ipv6/" + key + "/ipv6_list.txt");
            if (! file.exists()) {
                file.createNewFile();
            }

            FileWriter writer  = new FileWriter(file);

        }
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
            googleIp.setDelay(lineStr[3]);
            googleIp.setUnknownA(lineStr[4]);
            googleIp.setUnknownB(lineStr[5]);
            ipList.add(googleIp);
        }
        return ipList;
    }
}
