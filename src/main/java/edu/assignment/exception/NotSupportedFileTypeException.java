package edu.assignment.exception;
/**
 *  The {@code NotSupportedFileTypeException} Custom exception should be thrown when given file type not supported
 */
public class NotSupportedFileTypeException extends RuntimeException {


  public NotSupportedFileTypeException(String format) {
    super(format);
  }
}