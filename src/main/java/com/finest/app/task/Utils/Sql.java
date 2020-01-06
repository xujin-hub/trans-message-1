package com.finest.app.task.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Sql {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getBh()
    {
        int num = (int)(Math.random()*1000000);
        return sdf.format(new Date())+num +"";
    }

    public static String getSid()
    {
        int num = (int)(Math.random()*100000+40000);
        return num +"";
    }

    public static String getCjbh(String bh)
    {
        int num1 = (int)(Math.random()*1000000);
        int num2 = (int)(Math.random()*100000);
        return "CJ"+bh+num1+num2;
    }

    public static DBPrama getDBPrama()
    {
        int num = (int)(Math.random()*500);
        return DBmap.list.get(num%DBmap.list.size());
    }

    public static void main(String[] args) {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT info.CASE_NO caseNO, info.CASE_TYPE caseType, info.CASE_LEVEL caseLevel, ");
        sql.append("  info.CASE_DIVISION_CODE caseDivisionCode, info.JQXZDM_MC jqxzdmMc, ");
        sql.append("  info.ALARM_PHONE_NUMBER alarmPhoneNumber, info.ALARM_USER_NAME alarmUserName, ");
        sql.append("  info.CASE_CONTENT caseContent, info.CRIME_TIME crimeTime, info.CRIME_ADDRESS crimeAddress, ");
        sql.append("  info.LONGITUDE longitude, info.LATITUDE latitude ,h.bh bh"); // , rec.CREATE_TIME createTime
        sql.append("   FROM police_station_case c left join police_case_info_detail h on h.bh=c.case_no left join POLICE_CASE_INFO info  on h.jjbh=info.case_no ");
        sql.append("  where c.station_no= '420204500000'    and c.status='0'  order by info.CRIME_TIME");
        System.out.println(sql.toString());
    }

    public static String[] getSql()
    {
        String[] res = new String[4];
        String bh = getBh();
        DBPrama prama = getDBPrama();
        String sql1 = "insert into police_station_case (SID, CASE_NO, STATION_NO, SEND_TIME, STATUS, ISBACK, REMARK, TYPE, HANDLE_START_TIME, HANDLE_END_TIME, HANDLE_RECH_TIME, IS_DELETE, XF_TIME, QS_TIME, DC_TIME, FK_TIME, TOTALTIME)" +
                "values ('"+getSid()+"', '"+bh+"', '420204500000', "+new Date().getTime()+", '0', '0', null, 0, null, null, null, 'N', null, null, null, null, null)";

        String sql2 = "insert into police_case_info_detail (BH, JJBH, JJDW, JJDWMC, JJYBH, JJYXM, JJZT, JJZHBM, JJSJ, STORAGE_TIME, SFQC) " +
                "values ('"+bh+"', '"+bh+"', '420204500000', '团城山派出所', '249', '黄倩', '0', '市局指挥中心', sysdate, sysdate, 1)";

        String sql3 = "insert into police_case_info (SID, AC_LOGIC_ID, AC_STANDARD_ADDR_NAME, AC_STANDARD_ADDR_CODE, AC_XCOORD, AC_YCOORD, AC_GEO, AC_POLITICAL_NAME, AC_POLITICAL_CODE, AC_REGION_NAME, AC_REGION_CODE, AC_TASK_ID, AC_TASK_BATCH_NUMBER, AC_MATCH_STATUS, AC_MATCH_EXCEPTION_INFO, AC_MATCH_PRECISION, AC_LAST_UPDATE_TIME, AC_OPERATOR_ID, AC_OPERATOR_NAME, CASE_NO, ALARM_START_TIME, CASE_TYPE, ALARM_TYPE, ALARM_TYPE_DESCRIBE, CASE_DIVISION_CODE, CASE_LEVEL, ALARM_PHONE_NUMBER, ALARM_USER_NAME, ALARM_ADDRESS, CRIME_TIME, CRIME_ADDRESS, CASE_STATE, ALARM_END_TIME, LONGITUDE, LATITUDE, GEO, POLICE_STATION_CODE, SSJCWDM, CASE_CONTENT, STORAGE_TIME, ALARM_LONGTITUDE, ALARM_LATITUDE, POLICE_STATION_NAME, JQXZDM_MC, AC_FIRST_GRID_ROW_CODE, AC_FIRST_GRID_COL_CODE, AC_SECOND_GRID_ROW_CODE, AC_SECOND_GRID_COL_CODE, AC_THIRD_GRID_ROW_CODE, AC_THIRD_GRID_COL_CODE, AC_FOURTH_GRID_ROW_CODE, AC_FOURTH_GRID_COL_CODE, AC_FIFTH_GRID_ROW_CODE, AC_FIFTH_GRID_COL_CODE, AC_SIXTH_GRID_ROW_CODE, AC_SIXTH_GRID_COL_CODE, IS_VALID, IS_READ, IS_FOCUS, POLICE_OPERATOR, CALLED_DEPARTMENT, POSITION_TYPE, POLICE_OPERATOR_NAME, BH, BJLBDM, BJLXDM, BJXLDM)" +
                "values ('"+UUID.randomUUID()+"', 54764344, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, sysdate, null, null, '"+bh+"', sysdate, '110', '100600', null, null, null, '19107250701', '王易', null, sysdate, '"+prama.getCrimeAddress()+"', '1', sysdate, 115.05146959, 29.75352800, '114.79585 29.753528', '420204500000', null, '"+prama.getCaseContent()+"', sysdate, null, null, null, '"+prama.getType()+"', null, null, null, null, null, null, null, null, null, null, null, null, '1', '0', '0', '249', '市局指挥中心', null, '黄倩', null, '10', '100600', null)";

        String sql4 = "update wis_data_state t set t.update_time = sysdate where t.police_no = '420204500000'";
        res[0] = sql1;
        res[1] = sql2;
        res[2] = sql3;
        res[3] = sql4;
        return res;
    }
}