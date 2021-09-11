package edu.assignment.exception;

public class NotSupportedFileTypeException extends RuntimeException {
  public NotSupportedFileTypeException(String format) {
    super(format);
  }
}