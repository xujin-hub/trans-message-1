package com.finest.app.task.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB extends DBConnection {
    private static Connection con = null;
    private static Statement sql = null;
    private static ResultSet rs = null;

    public static void main(String[] args) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT A.*, ROWNUM FROM(SELECT info.CASE_NO caseNO, info.CASE_TYPE caseType, info.CASE_LEVEL caseLevel, ");
        sql.append("  info.CASE_DIVISION_CODE caseDivisionCode, info.JQXZDM_MC jqxzdmMc, ");
        sql.append("  info.ALARM_PHONE_NUMBER alarmPhoneNumber, info.ALARM_USER_NAME alarmUserName, ");
        sql.append("  info.CASE_CONTENT caseContent, info.CRIME_TIME crimeTime, info.CRIME_ADDRESS crimeAddress, ");
        sql.append("  info.LONGITUDE longitude, info.LATITUDE latitude ,'' bh , info.STORAGE_TIME createTime"); // , rec.CREATE_TIME createTime
        sql.append("  FROM POLICE_CASE_INFO info");
        sql.append("  where info.CASE_NO in ('') and info.STORAGE_TIME > to_date('" + "2020-01-02 21:00:33");
        sql.append("','yyyy-MM-dd hh24:mi:ss') order by  info.STORAGE_TIME ASC) A WHERE ROWNUM between 0 and 10");

        System.out.println(sql.toString());
    }

    public static void sss() throws Exception {
        try
        {
            int i = 1/0;
        }catch (Exception e)
        {
            throw new Exception();
        }

    }


    public static void mockData() throws SQLException {

        String caseContent = "";
        String crimeAddress = "";
        String jqxzdmMc = "";
        try {

            String str = "SELECT info.CASE_NO            caseNO,\n" +
                    "       info.CASE_TYPE          caseType,\n" +
                    "       info.CASE_LEVEL         caseLevel,\n" +
                    "       info.CASE_DIVISION_CODE caseDivisionCode,\n" +
                    "       info.JQXZDM_MC          jqxzdmMc,\n" +
                    "       info.ALARM_PHONE_NUMBER alarmPhoneNumber,\n" +
                    "       info.ALARM_USER_NAME    alarmUserName,\n" +
                    "       info.CASE_CONTENT       caseContent,\n" +
                    "       info.CRIME_TIME         crimeTime,\n" +
                    "       info.CRIME_ADDRESS      crimeAddress,\n" +
                    "       info.LONGITUDE          longitude,\n" +
                    "       info.LATITUDE           latitude,\n" +
                    "       h.bh                    bh\n" +
                    "  FROM police_station_case c\n" +
                    "  left join police_case_info_detail h\n" +
                    "    on h.bh = c.case_no\n" +
                    "  left join POLICE_CASE_INFO info\n" +
                    "    on h.jjbh = info.case_no\n" +
                    " where c.station_no = '420204500000'\n" +
                    "   and c.status <> '0'\n" +
                    " order by info.CRIME_TIME\n";
            con = dbConn("PORTAL", "PORTAL");
            if (con == null) {
                System.out.print("连接失败");
                System.exit(0);
            }
            sql = con.createStatement();

            String[] temp = Sql.getSql();
            sql.execute(temp[0]);
            sql.execute(temp[1]);
            sql.execute(temp[2]);
//            sql.execute(temp[3]);
//            rs = sql.executeQuery(str);
//            while (rs.next()) {
//                caseContent = rs.getString("caseContent");
//                crimeAddress = rs.getString("crimeAddress");
//                jqxzdmMc = rs.getString("jqxzdmMc");
//                System.out.println("list.add(new DBPrama(\""+crimeAddress+"\",\""+caseContent+"\",\""+jqxzdmMc+"\"));");
//            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            con.close();
        }
    }
}