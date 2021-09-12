package edu.assignment.models;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * The {@code Cookie} class holds Cookie value and Date
 */
public class Cookie {
  private String cookie;
  private OffsetDateTime date;

  public static CookieBuilder builder() {
    return new CookieBuilder();
  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
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
