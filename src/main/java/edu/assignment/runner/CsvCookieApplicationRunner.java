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
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *  The {@code CsvCookieApplicationRunner} The class implements all the required method to parse scv files with cookies
 *  and find most active cookie
 */
public class CsvCookieApplicationRunner extends AbstractApplicationRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(CsvCookieApplicationRunner.class);

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final ZoneId UTCZoneId = ZoneId.of("UTC");
  private static final int MAXIMUM_APPEARANCE_NUMBER = 2;

  public CsvCookieApplicationRunner() {
    super(new CookiesProcessorImpl(MAXIMUM_APPEARANCE_NUMBER));
  }

  /**
   * This method will parse all command line arguments and convert it into Arguments object
   * It will log and throw exception if any invalid or missing argument is provided
   * @param args
   * @return Arguments
   */
  @Override
  public Arguments parseArguments(String[] args){

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
      formatter.printHelp("Arguments supported are ", options);
      LOGGER.error("The application does not support provided arguments, please provide valid arguments", e);
      throw new WrongArgumentsException("Invalid arguments passed");
    }

    String filePath = cmd.getOptionValue("file");
    String dateInput = cmd.getOptionValue("date");

    Arguments arguments = new Arguments();
    arguments.setFileName(filePath);
    try {
      LocalDate localDate = LocalDate.parse(dateInput, dateTimeFormatter);
      OffsetDateTime forDate = localDate.atStartOfDay(UTCZoneId).toOffsetDateTime();
      arguments.setDate(forDate);
    } catch (DateTimeParseException ex) {
      LOGGER.error("Parsing Date Exception", ex);
      throw new WrongArgumentsException("Wrong date argument format, please provide date in yyyy-MM-dd format");
    }
    return arguments;
  }

  /**
   * This method will detect file type from file extension.
   * It will throw NotSupportedFileTypeException if given file type is not supported ny application
   * @param arguments
   * @return FileType
   */
  @Override
  public FileType detectFileType(Arguments arguments) {
    String fileName = arguments.getFileName();
    int extensionStartIndex = fileName.lastIndexOf(".");
    if (extensionStartIndex == -1) {
      throw new NotSupportedFileTypeException("File Extension is not provided, please provide file name with " +
          "extension");
    }
    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    try {
      return FileType.valueOf(extension.toUpperCase());
    } catch (IllegalArgumentException ex) {
      String errorMessage = String.format("File extension %s is not supported, please provide files with supported " +
          "formats", extension);
      throw new NotSupportedFileTypeException(errorMessage);
    }
  }

  /**
   * This method will the CookiesProcessor Implementation for CSV files and will return most active cookies.
   * @param cookies
   * @param date
   * @param cookiesProcessor
   * @return
   */
  @Override
  public List<Cookie> findMostActiveCookies(List<Cookie> cookies, OffsetDateTime date,
                                            CookiesProcessor cookiesProcessor) {
    return cookiesProcessor.mostActiveCookiesOnDate(cookies, date);
  }


}
