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
    String filePath = "src/test/resources/files/cookies-1.csv";
    List<Cookie> cookiesList = csvFileParser.parseFile(filePath);
    assertTrue(cookiesList.size() == 8);
  }

  @Test
  public void parseFile_withInvalidFilePath_shouldThrowException() {
    String invalidFilePath = "src/test/resources/files2/cookies-1.csv";
    assertThrows(
        FileParsingException.class, () -> csvFileParser.parseFile(invalidFilePath));
  }

  @Test
  public void parseFile_withSomeCorruptDatesRecords_shouldParseRemainingCorrectly(){
    String filePath = "src/test/resources/files/cookies-with-3-corrupt-dates.csv";
    List<Cookie> cookiesList = csvFileParser.parseFile(filePath);
    assertTrue(cookiesList.size() == 5 );
  }
}
