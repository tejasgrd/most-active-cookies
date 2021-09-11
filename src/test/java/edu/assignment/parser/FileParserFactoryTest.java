package edu.assignment.parser;

import edu.assignment.models.FileType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class FileParserFactoryTest {

  @Mock
  FileParser fileParser;

  @Test
  public void getFileParser_withValidFileType_shouldReturnFileParser(){
    FileType fileType = FileType.CSV;

    try (MockedStatic<FileParserFactory> factoryMockedStatic = Mockito.mockStatic(FileParserFactory.class)) {
      factoryMockedStatic.when(() -> FileParserFactory.getFileParser(fileType))
          .thenReturn(fileParser);

      assertThat(FileParserFactory.getFileParser(fileType), is(fileParser));
    }
  }
}
