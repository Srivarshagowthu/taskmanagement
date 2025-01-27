package com.ninjacart.task_mgmt_service.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

        final static long hoursInMillis = 60L * 60L * 1000L;
        final static long minutesInMillis = 60L * 1000L;
        public final static long serverOverlapTime = 5L * hoursInMillis + (30L * minutesInMillis);

        public static Date addHours(Date date, int hours) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, hours);
            return calendar.getTime();
        }

        public static Date addMinutes(Date date, int hours) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, hours);
            return calendar.getTime();
        }


        public static boolean  isDateAfter(Date from,Date to){
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(from);
            cal2.setTime(to);
            return cal1.after(cal2);
        }

        public static String dateFormat(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }

        public static SimpleDateFormat dateFormatter() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf;
        }

        public static String dateFormatSort(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }

        public static Date getDate(String date) throws ParseException {
            try {
                return dateFormatter().parse(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw e;
            }
        }

        public static String addOneToDate(Date date) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            Date newDate = cal.getTime();
            String newDateStr = dateFormatSort(newDate);
            return newDateStr;
        }

        public static long getDateDifference(Date date1, Date date2, TimeUnit timeUnit) {
            long diffInMillies = date2.getTime() - date1.getTime();
            return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
        }

        public static String timeFormat(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(date);
        }

        public static Date addMinutesToDate(Date date, int minutes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, minutes);
            return cal.getTime();
        }

        public static Date getLocalDate() throws ParseException {
            Date serverDate = new Date(); // oldDate == current time
            Date localDate = new Date(serverDate.getTime() + serverOverlapTime); // Adds
            // 5
            // hours
            // 32
            // mingetCuutes
            String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(localDate);
            Date parseDate = dateFormatter().parse(deliveryDate);
            return parseDate;
        }


        public static String getCurrentISTDate() throws ParseException {
            Date serverDate = new Date();
            Date localDate = new Date(serverDate.getTime() + serverOverlapTime);
            return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
        }

        public static Date changeISOToUTC(String datetime) {
            String str_date = datetime;
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                date = (Date) formatter.parse(str_date);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Date changeDateTimeFromUtcToIst(Date UTC) {
            // Get the date in IST
            Date tempDateIST = new Date(UTC.getTime() + serverOverlapTime);
            return tempDateIST;
        }

        public static Date changeDateTimeFromIstToUtc(Date UTC) {
            // Get the date in IST
            Date tempDateIST = new Date(UTC.getTime() - serverOverlapTime);
            return tempDateIST;
        }

        public static Date getDateLong(String date) throws ParseException {
            try {
                return dateFormatterLong().parse(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw e;
            }
        }

        public static Date getCurrentISTTime() throws ParseException {
            Date currentGMTTime = new Date(); // GMT TIME
            Date currentISTTime = new Date(currentGMTTime.getTime() + serverOverlapTime); // IST
            // TIME
            return currentISTTime;
        }

        public static SimpleDateFormat dateFormatterLong() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf;
        }

        public static void main(String[] args) throws Exception {
            Date date = new Date();
            String dateString = dateFormatSort(date);
            Date newDate = getDate(dateString);
            System.out.println(newDate);

        }

        public static void verifyDateFormat(String date, SimpleDateFormat simpleDateFormat) throws ParseException{
            simpleDateFormat.parse(date);
        }
    }

