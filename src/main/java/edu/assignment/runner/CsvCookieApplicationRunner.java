package edu.assignment.runner;

import edu.assignment.exception.WrongArgumentsException;
import edu.assignment.models.Arguments;
import edu.assignment.models.Cookie;
import edu.assignment.models.FileType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvCookieApplicationRunner extends AbstractApplicationRunner {

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final ZoneId UTCZoneId = ZoneId.of("UTC");

  @Override
  public Arguments parseArguments(String[] args) throws WrongArgumentsException {
    if (args.length < 4 || args.length > 4) {
      throw new WrongArgumentsException("Wrong Number of Arguments please specify -f <fileName> -d <date>");
    }
    boolean isFileArg = false;
    boolean isDateArg = false;
    Arguments arguments = new Arguments();
    for (String argument : args) {
      if (isFileArg) {
        arguments.setFileName(argument);
      } else if (isDateArg) {
        LocalDate localDate = LocalDate.parse(argument, dateTimeFormatter);
        OffsetDateTime forDate = localDate.atStartOfDay(UTCZoneId).toOffsetDateTime();
        arguments.setDate(forDate);
      } else if (argument.equalsIgnoreCase("-f")) {
        isFileArg = true;
      } else if (argument.equalsIgnoreCase("-d")) {
        isDateArg = true;
      }
    }
    return arguments;
  }

  @Override
  public FileType detectFileType(Arguments arguments) {
    String fileName = arguments.getFileName();
    String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
    return FileType.valueOf(extension.toUpperCase());
  }

  @Override
  public List<Cookie> findMostActiveCookies(List<Cookie> cookies, OffsetDateTime date) {
    List<Cookie> cookiesForDay = getCookiesOnDate(cookies, date);
    return null;
  }

  private List<Cookie> searchForMostActiveCookies(List<Cookie> cookiesForDay){
    Map<Cookie, Integer> cookieMap = new HashMap<>();

    for(Cookie cookie: cookiesForDay){
      if(cookieMap.containsKey(cookie)){
        int appearance = cookieMap.get(cookie);
        cookieMap.put(cookie, appearance+1);
      }else{
        cookieMap.put(cookie, 1);
      }
    }

    int maxAppearance = 0;

  }

  private List<Cookie> getCookiesOnDate(List<Cookie> cookies, OffsetDateTime date) {
    return cookies.stream()
        .filter(cookie -> cookie.getDate().getDayOfMonth() == date.getDayOfMonth())
        .collect(Collectors.toList());
  }
}
