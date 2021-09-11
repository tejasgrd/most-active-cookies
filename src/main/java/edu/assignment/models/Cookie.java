package edu.assignment.models;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

public class Cookie {
  private String cookie;
  private OffsetDateTime date;

  public String getCookie() {
    return cookie;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public static CookieBuilder builder(){
    return new CookieBuilder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cookie cookie1 = (Cookie) o;
    return getCookie().equals(cookie1.getCookie());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCookie());
  }
}
