package edu.assignment.processor;

import edu.assignment.models.Cookie;

import java.time.OffsetDateTime;
import java.util.List;

public interface CookiesProcessor {
  List<Cookie> mostActiveCookiesOnDate(List<Cookie> cookies, OffsetDateTime date);
}
