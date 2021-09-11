package edu.assignment.exception;

public class FileParsingException extends RuntimeException{
  public FileParsingException(String format) {
    super(format);
  }
}
