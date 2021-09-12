package edu.assignment.parser;

import edu.assignment.exception.FileParsingException;
import edu.assignment.models.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CsvFileParserTest {

  private CsvFileParser csvFileParser;

  @Before
  public void setup() {
    csvFileParser = new CsvFileParser();
  }

  @Test
  public void parseFile_withValidFile_shouldReturnCookiesList() {
    String filePath = "test-classes/files/cookies-1.csv";
    List<Cookie> cookiesList = csvFileParser.parseFile(filePath);
    assertTrue(cookiesList.size() == 8);
  }

  @Test
  public void parseFile_withInvalidFilePath_shouldThrowException() {
    String invalidFilePath = "test-classes/files2/cookies-1.csv";
    assertThrows(
        FileParsingException.class, () -> csvFileParser.parseFile(invalidFilePath));
  }

  @Test
  public void parseFile_withSomeCorruptDateRecords_shouldParseRemainingCorrectly() {
    String filePath = "test-classes/files/cookies-with-3-corrupt-dates.csv";
    List<Cookie> cookiesList = csvFileParser.parseFile(filePath);
    assertTrue(cookiesList.size() == 5);
  }

  @Test
  public void parseFile_withEmptyFile_shouldNotReturnAnyCookies() {
    String filePath = "test-classes/files/cookies-with-no-records.csv";
    List<Cookie> cookiesList = csvFileParser.parseFile(filePath);
    assertTrue(cookiesList.size() == 0);
  }
}
