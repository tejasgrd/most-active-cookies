package edu.assignment.parser;

import edu.assignment.exception.NotSupportedFileTypeException;
import edu.assignment.models.FileType;

public class FileParserFactory {

  public static FileParser getFileParser(FileType fileType) {
    switch (fileType) {
      case CSV:
        return new CsvFileParser();
      default:
        throw new NotSupportedFileTypeException(
            String.format("File type [%s] is not supported", fileType));
    }
  }
}
