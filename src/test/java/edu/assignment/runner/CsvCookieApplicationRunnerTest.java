package edu.assignment.runner;

import edu.assignment.exception.NotSupportedFileTypeException;
import edu.assignment.exception.WrongArgumentsException;
import edu.assignment.models.Arguments;
import edu.assignment.models.FileType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class CsvCookieApplicationRunnerTest {

  private CsvCookieApplicationRunner csvCookieApplicationRunner;
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final ZoneId UTCZoneId = ZoneId.of("UTC");
  private String fileName;
  private String date;

  @Before
  public void setup() {
    csvCookieApplicationRunner = new CsvCookieApplicationRunner();
    fileName = "example.csv";
    date = "2018-12-30";
  }

  @Test
  public void parseArguments_withValidArguments_shouldReturnArgumentObject(){
    String[] arguments = new String[] {"-f",fileName,"-d",date};
    LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
    OffsetDateTime forDate = localDate.atStartOfDay(UTCZoneId).toOffsetDateTime();
    Arguments argumentsObj = csvCookieApplicationRunner.parseArguments(arguments);
    assertNotNull(argumentsObj);
    assertThat(argumentsObj.getFileName(), is(fileName));
    assertThat(argumentsObj.getDate(), is(forDate));
  }

  @Test
  public void parseArguments_withArgsInReverseOrder_shouldReturnArgumentObject(){
    String[] arguments = new String[] {"-d",date,"-f",fileName};
    LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
    OffsetDateTime forDate = localDate.atStartOfDay(UTCZoneId).toOffsetDateTime();
    Arguments argumentsObj = csvCookieApplicationRunner.parseArguments(arguments);
    assertNotNull(argumentsObj);
    assertThat(argumentsObj.getFileName(), is(fileName));
    assertThat(argumentsObj.getDate(), is(forDate));
  }

  @Test
  public void parseArguments_withoutFileArguments_shouldThrowException() {
    String[] arguments = new String[]{"-d", date};
    assertThrows(
        WrongArgumentsException.class,
        () -> csvCookieApplicationRunner.parseArguments(arguments)
    );
  }

  @Test
  public void parseArguments_withoutDateArgument_shouldThrowException() {
    String[] arguments = new String[]{"-f", fileName};
    assertThrows(
        WrongArgumentsException.class,
        () -> csvCookieApplicationRunner.parseArguments(arguments)
    );
  }

  @Test
  public void parseArguments_withWrongDateFormat_shouldThrowException() {
    String[] arguments = new String[] {"-d","01-03-2021","-f",fileName};
    assertThrows(
        WrongArgumentsException.class,
        () -> csvCookieApplicationRunner.parseArguments(arguments)
    );
  }

  @Test
  public void detectFileType_withValidFileExtension_shouldReturnFileType(){
    Arguments arguments = getValidArgument();
    FileType fileType = csvCookieApplicationRunner.detectFileType(arguments);
    assertThat(fileType, is(FileType.CSV));
  }

  @Test
  public void detectFileType_withoutFileExtension_shouldThrowException(){
    Arguments invalidArguments = getArgumentsWithNoFileExtension();
    assertThrows(
        NotSupportedFileTypeException.class,
        () -> csvCookieApplicationRunner.detectFileType(invalidArguments)
    );
  }

  @Test
  public void detectFileType_withInvalidFileExtension_shouldThrowException(){
    Arguments invalidFileExtensionArgs = getArgumentsWithInvalidFileExtension();
    assertThrows(
        NotSupportedFileTypeException.class,
        () -> csvCookieApplicationRunner.detectFileType(invalidFileExtensionArgs)
    );
  }

  private Arguments getValidArgument(){
    Arguments arguments = new Arguments();
    arguments.setFileName("cookies.csv");
    arguments.setDate(OffsetDateTime.now());
    return arguments;
  }

  private Arguments getArgumentsWithNoFileExtension(){
    Arguments arguments = new Arguments();
    arguments.setFileName("cookies-files");
    arguments.setDate(OffsetDateTime.now());
    return arguments;
  }

  private Arguments getArgumentsWithInvalidFileExtension(){
    Arguments arguments = new Arguments();
    arguments.setFileName("cookies-files.json");
    arguments.setDate(OffsetDateTime.now());
    return arguments;
  }

}
