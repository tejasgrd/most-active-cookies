package edu.assignment.processor;

import edu.assignment.models.Cookie;

import java.util.Date;
import java.util.List;

public interface CookiesProcessor {
  List<Cookie> mostActiveCookiesOnDate(List<Cookie> cookies, Date date);
}
