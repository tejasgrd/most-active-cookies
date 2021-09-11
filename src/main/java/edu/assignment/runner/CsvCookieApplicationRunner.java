package edu.assignment.runner;

import edu.assignment.exception.NotSupportedFileTypeException;
import edu.assignment.exception.WrongArgumentsException;
import edu.assignment.models.Arguments;
import edu.assignment.models.Cookie;
import edu.assignment.models.FileType;
import edu.assignment.processor.CookiesProcessor;
import edu.assignment.processor.CookiesProcessorImpl;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvCookieApplicationRunner extends AbstractApplicationRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(CsvCookieApplicationRunner.class);

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final ZoneId UTCZoneId = ZoneId.of("UTC");

  public CsvCookieApplicationRunner() {
    super(new CookiesProcessorImpl());
  }

  @Override
  public Arguments parseArguments(String[] args) throws WrongArgumentsException {

    Options options = new Options();
    Option file = new Option("f", "file", true, "input file path");
    file.setRequired(true);
    options.addOption(file);
    Option date = new Option("d", "date", true, "date for most active cookies");
    date.setRequired(true);
    options.addOption(date);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      LOGGER.error("Input arguments are not as expected "+e);
      formatter.printHelp("most-active-cookies", options);
      System.exit(1);
    }

    String filePath = cmd.getOptionValue("file");
    String dateInput = cmd.getOptionValue("date");

    Arguments arguments = new Arguments();
    arguments.setFileName(filePath);
    LocalDate localDate = LocalDate.parse(dateInput, dateTimeFormatter);
    OffsetDateTime forDate = localDate.atStartOfDay(UTCZoneId).toOffsetDateTime();
    arguments.setDate(forDate);

    return arguments;
  }

  @Override
  public FileType detectFileType(Arguments arguments) {
    String fileName = arguments.getFileName();
    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    if(extension == null){
      throw new NotSupportedFileTypeException("File Extension is not provided, please provide file name with extension");
    }
    return FileType.valueOf(extension.toUpperCase());
  }

  @Override
  public List<Cookie> findMostActiveCookies(List<Cookie> cookies, OffsetDateTime date,
                                            CookiesProcessor cookiesProcessor) {
    return cookiesProcessor.mostActiveCookiesOnDate(cookies, date);
  }


}
