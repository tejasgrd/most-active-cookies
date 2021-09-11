package edu.assignment.processor;

import edu.assignment.models.Cookie;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CookiesProcessorImpl implements CookiesProcessor{

  @Override
  public List<Cookie> mostActiveCookiesOnDate(List<Cookie> cookies, OffsetDateTime date){
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
        cookieMap.put(cookie, appearance + 1);
        if (maxAppearance < appearance) {
          maxAppearance = appearance; // save highest appeared number
        }
      } else {
        cookieMap.put(cookie, 1);
      }
    }
    List<Cookie> maxAppeared = new LinkedList<>();
    for (Cookie cookie : cookieMap.keySet()) {
      if (cookieMap.get(cookie) == maxAppearance) {
        maxAppeared.add(cookie);
      }
    }
    return maxAppeared;
  }

  private List<Cookie> getCookiesOnDate(List<Cookie> cookies, OffsetDateTime date) {
    return cookies.stream()
        .filter(cookie -> cookie.getDate().getDayOfMonth() == date.getDayOfMonth())
        .collect(Collectors.toList());
  }
}
