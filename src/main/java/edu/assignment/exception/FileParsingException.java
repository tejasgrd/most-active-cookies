package edu.assignment.exception;

/**
 *  The {@code FileParsingException} Custom exception should be thrown when error occurred while parsing file
 */
public class FileParsingException extends RuntimeException{
  public FileParsingException(String format) {
    super(format);
  }
}
