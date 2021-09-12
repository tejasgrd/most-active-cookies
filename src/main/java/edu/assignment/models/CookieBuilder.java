package edu.assignment.models;

import java.time.OffsetDateTime;

/**
 * The {@code CookieBuilder} builder class for cookie object creation
 */
public class CookieBuilder {
  private String cookie;
  private OffsetDateTime date;

  public CookieBuilder() {
  }

  public CookieBuilder(Cookie cookie) {
    this.cookie = cookie.getCookie();
    this.date = cookie.getDate();
  }

  public CookieBuilder cookie(String cookie) {
    this.cookie = cookie;
    return this;
  }

  public CookieBuilder date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  public Cookie build() {
    Cookie cookie = new Cookie();
    cookie.setCookie(this.cookie);
    cookie.setDate(this.date);
    return cookie;
  }
}
