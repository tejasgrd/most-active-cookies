package edu.assignment.runner;

import edu.assignment.exception.WrongArgumentsException;
import edu.assignment.models.Arguments;
import edu.assignment.models.Cookie;
import edu.assignment.models.FileType;
import edu.assignment.processor.CookiesProcessor;
import edu.assignment.processor.CookiesProcessorImpl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvCookieApplicationRunner extends AbstractApplicationRunner {

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final ZoneId UTCZoneId = ZoneId.of("UTC");

  public CsvCookieApplicationRunner(){
    super(new CookiesProcessorImpl());
  }

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
  public List<Cookie> findMostActiveCookies(List<Cookie> cookies, OffsetDateTime date, CookiesProcessor cookiesProcessor) {
    return cookiesProcessor.mostActiveCookiesOnDate(cookies, date);
  }


}
