package edu.assignment.models;

import java.time.OffsetDateTime;

public class Arguments {
  private String fileName;
  private OffsetDateTime date;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }


}
