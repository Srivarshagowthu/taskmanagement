package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CommonUtils {
    final static long hoursInMillis = 60L * 60L * 1000;
    final static long minutesInMillis = 60L * 1000;
    public final static long serverOverlapTime = 5 * hoursInMillis + (30 * minutesInMillis);

    public static Map<String, Object> getObjectMapper(String requestParam) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        try {
            // Adjust the TypeReference to Map<String, Object>
            map = mapper.readValue(requestParam, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Map<String, Object> getObjectMapperObject(String requestParam) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = mapper.readValue(requestParam, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static int getRandomAssignedToUser(List<Integer> users) {
        int size = users.size();
        Random random = new Random();
        int randomIndex = random.nextInt(size);
        randomIndex = randomIndex % size;
        return users.get(randomIndex);
    }


    public static String listToCommaSeparatedString(List<Integer> intList) {
        String commaSeparatedString = "";
        int intListSize = intList.size();
        for (int i = 0; i < intListSize; i++) {
            if (i != 0) {
                commaSeparatedString += ",";
            }
            commaSeparatedString += intList.get(i);
        }

        return commaSeparatedString;
    }

    public static List<Integer> commaSeparatedStringToList(String string) {
        //NOTA BENE : This function will only convert Integer values
        List<Integer> list = new ArrayList<Integer>();
        if (string != null && !string.isEmpty()) {
            List<String> intAsString = Arrays.asList(string.split(","));
            list = intAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
        }
        return list;
    }


    public static List<String> commaSeparatedStringToStringList(String string) {
        List<String> list = new ArrayList<String>();
        if (string != null && !string.isEmpty()) {
            list = Arrays.asList(string.split(","));
        }
        return list;
    }

    public static String stringListToCommaSeparatedString(List<String> intList) {
        String commaSeparatedString = "";
        int intSize = intList.size();
        for (int i = 0; i < intSize; i++) {
            if (i != 0) {
                commaSeparatedString += ",";
            }
            commaSeparatedString += intList.get(i);
        }
        return commaSeparatedString;
    }

    public static String numberListToCommaSeparatedString(List<Number> intList) {
        String commaSeparatedString = "";
        int intSize = intList.size();
        for (int i = 0; i < intSize; i++) {
            if (i != 0) {
                commaSeparatedString += ",";
            }
            commaSeparatedString += intList.get(i);
        }

        return commaSeparatedString;
    }


    public static List<Integer> getIntegerListFromStringList(List<String> list) {
        // NOTA BENE : This function will only convert Integer values
        List<Integer> result = new ArrayList<>();
        for(String integer : list) {
            result.add(Integer.parseInt(integer));
        }
        return result;
    }

    public static String getStringFromObject(Map<String, Object> object) {
        ObjectMapper mapper = new ObjectMapper();
        String string = new String();
        try {
            string = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static <T> T getMappedObject(Object object,Class<T> t) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, t);
    }

    public static String dateFormatSort(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String timeFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static Date getDateTimeFromString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date);
    }

    public static Date getDateFromString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static Date addMinutesToDate(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static Date addHoursToDate(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    public static Date subtractHoursFromDate(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -hours);
        return cal.getTime();
    }

    public static long getHoursDiff(Date date1, Date date2) {
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);

        // if minutes are greater than 30 add one more hour.
        int hours = (Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 >= 30) ? 1 : 0;

        return (Days.daysBetween(dt1, dt2).getDays() * 24) + (Hours.hoursBetween(dt1, dt2).getHours() % 24) + hours;

    }


    public static Date getCurrentISTTime() throws ParseException {
        Date currentGMTTime = new Date(); // GMT
        Date currentISTTime = new Date(currentGMTTime.getTime() + serverOverlapTime); // IST
        return currentISTTime;
    }

    public static Date addDayToDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static void addToMap(Map<String, List<String>> map, String key, String... values) {
        map.computeIfAbsent(key, k -> new ArrayList<>(values.length)).addAll(Arrays.asList(values));
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        DateTime dt1 = new DateTime(d1);
        DateTime dt2 = new DateTime(d2);
        long day = Days.daysBetween(dt1, dt2).getDays();
        return day;
    }
}

