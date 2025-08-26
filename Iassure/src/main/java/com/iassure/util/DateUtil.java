/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author rjanumpally
 */
@Component
@Log4j2
public class DateUtil {

    public Timestamp getTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        DateFormat df1 = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("IST"));
        df1.setTimeZone(TimeZone.getTimeZone("IST"));
        Timestamp date = null;
        try {
            date = new Timestamp(df1.parse(df.format(new java.util.Date())).getTime());
        } catch (Exception e) {
            log.error("Exception in getTimestamp util {}",e.getMessage());
        }
        return date;
    }

}
