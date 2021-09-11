package edu.assignment.exception;

public class WrongArgumentsException extends RuntimeException{
  public WrongArgumentsException(String format) {
    super(format);
  }
}
