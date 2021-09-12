package edu.assignment.exception;

/**
 * The {@code WrongArgumentsException}   Custom exception should be thrown when arguments provided are not valid
 */
public class WrongArgumentsException extends RuntimeException{
  public WrongArgumentsException(String format) {
    super(format);
  }
}
