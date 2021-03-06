package edu.assignment.parser;

import edu.assignment.exception.FileParsingException;
import edu.assignment.models.Cookie;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@code CsvFileParser} Csv file parser to read and convert CSV file with cookies to Cookie object
 */
public class CsvFileParser implements FileParser {

  private static final int COOKIE_INDEX = 0;
  private static final int COOKIE_DATE_INDEX = 1;
  private static String FILE_SEPARATOR_SLASH;

  private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileParser.class);

  private static final DateTimeFormatter isoDateTimeFormatter = new DateTimeFormatterBuilder()
      .append(DateTimeFormatter.ISO_DATE_TIME)
      .toFormatter();

  public CsvFileParser(){
    String osName = System.getProperty("os.name");
    if(osName != null && osName.toLowerCase().contains("windows")){
      FILE_SEPARATOR_SLASH = "\\";
    }else{
      FILE_SEPARATOR_SLASH = "/";
    }
  }

  /**
   * This method will read csv file line by line and each row is saved as Cookie Object in List.
   * This method assumes that first line of the csv file is header and will skip that line from parsing.
   * This method parses Date using Instant and UTC
   *
   * @param filePath - absolute file path
   * @return List<Cookie> - All cookies present in CSV file
   * @throws IOException
   */
  @Override
  public List<Cookie> parseFile(String filePath){
    List<Cookie> cookies = new ArrayList<>();
    filePath = getCSVFilePath(filePath);
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      String fileHeader = bufferedReader.readLine();
      String csvRow = "";
      while ((csvRow = bufferedReader.readLine()) != null && !csvRow.isEmpty()) {
        String[] csvRowArray = csvRow.split(",");
        try {
          cookies.add(Cookie.builder()
              .cookie(csvRowArray[COOKIE_INDEX])
              .date(parseUTCStringDate(csvRowArray[COOKIE_DATE_INDEX]))
              .build());
        } catch (DateTimeParseException e) {
          String errorMessage = String.format("Exception while parsing Date for cookie %s and date %s, " +
              "skipping this record", csvRowArray[COOKIE_INDEX], csvRowArray[COOKIE_DATE_INDEX]);
          LOGGER.error(errorMessage);
        }
      }
    } catch (Exception e) {
      LOGGER.error("file parsing exception", e);
      throw new FileParsingException("Exception occurred during file parsing" + e.getMessage());
    }
    return cookies;
  }

  private OffsetDateTime parseUTCStringDate(String dateString) throws DateTimeParseException {
    return OffsetDateTime.parse(dateString, isoDateTimeFormatter);
  }

  private String getCSVFilePath(String filePath){
    File jarPath = new File(CsvFileParser.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String jarFolderPath = jarPath.getParentFile().getAbsolutePath();
    LOGGER.info("Jar folder is "+jarFolderPath);
    String path = jarFolderPath+FILE_SEPARATOR_SLASH+filePath;
    LOGGER.info("complete file path is "+path);
    return path;
  }

}
