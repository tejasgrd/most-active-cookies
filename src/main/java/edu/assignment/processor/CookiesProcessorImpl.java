package edu.assignment.processor;

import edu.assignment.models.Cookie;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  The {@code CookiesProcessorImpl} Class to process list of cookies and find most active cookie for the day
 */
public class CookiesProcessorImpl implements CookiesProcessor {

  private final int maximumAppearance;

  public CookiesProcessorImpl(int maximumAppearance) {
    this.maximumAppearance = maximumAppearance;
  }

  /**
   * This method will receive list of cookies and date to find most active cookie for that day.
   * The most active cookies are the one which appeared maximum number of time in a day.
   * If all cookies provided appeared only once in a given date then there is(are) no most active cookie(s) as all the
   * cookies are same has appearance. However this default behaviour can be configured by providing maximumAppearance
   * value , by default it is 2 that is for cookie to be most active in a day it should al least appear twice in a day
   * @param cookies
   * @param date
   * @return
   */
  @Override
  public List<Cookie> mostActiveCookiesOnDate(List<Cookie> cookies, OffsetDateTime date) {
    List<Cookie> cookiesForDay = getCookiesOnDate(cookies, date);
    return searchForMostActiveCookies(cookiesForDay);
  }

  private List<Cookie> searchForMostActiveCookies(List<Cookie> cookiesForDay) {
    Map<Cookie, Integer> cookieMap = new HashMap<>();
    int maxAppearance = 0;
    for (Cookie cookie : cookiesForDay) {
      if (cookieMap.containsKey(cookie)) {
        int appearance = cookieMap.get(cookie);
        appearance = appearance + 1;
        cookieMap.put(cookie, appearance);
        if (maxAppearance < appearance) {
          maxAppearance = appearance; // save highest appeared number
        }
      } else {
        cookieMap.put(cookie, 1);
      }
    }
    Set<Cookie> maxAppeared = new HashSet<>();
    for (Cookie cookie : cookieMap.keySet()) {
      if (cookieMap.get(cookie) == maxAppearance && maxAppearance >= maximumAppearance) {
        maxAppeared.add(cookie);
      }
    }
    return maxAppeared.stream().collect(Collectors.toList());
  }

  private List<Cookie> getCookiesOnDate(List<Cookie> cookies, OffsetDateTime date) {
    return cookies.stream()
        .filter(cookie -> cookie.getDate().getDayOfMonth() == date.getDayOfMonth())
        .collect(Collectors.toList());
  }
}
