package edu.assignment.processor;

import com.github.javafaker.Faker;
import edu.assignment.models.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CookiesProcessorImplTest {

  private final Faker faker = new Faker();
  int numberOfCookies;
  int activeCookiesNumber;
  private CookiesProcessor cookiesProcessor;
  private OffsetDateTime onDate;
  private int multipleActiveCookiesSize;

  @Before
  public void setup() {
    cookiesProcessor = new CookiesProcessorImpl(2);
    onDate = OffsetDateTime.now();
    numberOfCookies = 15;
    activeCookiesNumber = 4;
    multipleActiveCookiesSize = 4;

  }

  @Test
  public void mostActiveCookiesOnDate_withOneMostActiveCookie_shouldReturnOneMostActiveCookies() {
    List<Cookie> listCookies = getSomeCookies(numberOfCookies, activeCookiesNumber, onDate);
    List<Cookie> activeCookies = cookiesProcessor.mostActiveCookiesOnDate(listCookies, onDate);
    assertTrue(activeCookies.size() == 1);
  }

  @Test
  public void mostActiveCookiesOnDate_withMultipleMostActiveCookies_shouldReturnAllActiveCookies() {
    List<Cookie> listCookies = getMultipleMostActiveCookies(numberOfCookies, onDate);
    List<Cookie> activeCookies = cookiesProcessor.mostActiveCookiesOnDate(listCookies, onDate);
    assertTrue(activeCookies.size() == 3);
  }

  @Test
  public void mostActiveCookiesOnDate_withNoRepetition_shouldNotReturnAnyCookie(){
    List<Cookie> listCookies = getDifferentCookiesOnSameDate(numberOfCookies, onDate);
    List<Cookie> activeCookies = cookiesProcessor.mostActiveCookiesOnDate(listCookies, onDate);
    assertTrue(activeCookies.size() == 0);
  }


  private List<Cookie> getSomeCookies(int size, int repeat, OffsetDateTime dateTime) {
    List<Cookie> cookies = new ArrayList<>();
    String cookie = faker.bothify("????####asz???a?a");
    while (size > 0) {
      if (repeat > 0) {
        cookies.add(Cookie.builder()
            .cookie(cookie)
            .date(dateTime)
            .build());
        repeat--;
        size--;
        continue;
      } else {
        cookies.add(Cookie.builder()
            .cookie(faker.bothify("????####asz???a?a"))
            .date(OffsetDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.of("UTC")))
            .build());
        size--;
      }
    }

    return cookies;
  }

  private List<Cookie> getMultipleMostActiveCookies(int size, OffsetDateTime dateTime) {
    List<Cookie> cookies = new ArrayList<>();
    int firstMostActiveCount , secondMostActiveCount, thirdMostActiveCount ;
    firstMostActiveCount = secondMostActiveCount = thirdMostActiveCount = faker.random().nextInt(1, size / multipleActiveCookiesSize);
    String firstCookie = faker.bothify("????####asz???a?a");
    String secondCookie = faker.bothify("????####zxc???a?a");
    String thirdCookie = faker.bothify("????####uio???a?a");
    while(size > 0 ){
      if(firstMostActiveCount > 0 ){
        cookies.add(Cookie.builder()
            .cookie(firstCookie)
            .date(dateTime)
            .build());
        size--;
        firstMostActiveCount--;
      }else if(secondMostActiveCount > 0 ){
        cookies.add(Cookie.builder()
            .cookie(secondCookie)
            .date(dateTime)
            .build());
        size--;
        secondMostActiveCount--;
      }else if(thirdMostActiveCount > 0 ){
        cookies.add(Cookie.builder()
            .cookie(thirdCookie)
            .date(dateTime)
            .build());
        size--;
        thirdMostActiveCount--;
      }else {
        cookies.add(Cookie.builder()
            .cookie( faker.bothify("????####tyr???a?a"))
            .date(OffsetDateTime.now())
            .build());
        size--;
      }
    }
    return cookies;
  }


  private List<Cookie> getDifferentCookiesOnSameDate(int size,OffsetDateTime dateTime) {
    List<Cookie> cookies = new ArrayList<>();
    while (size > 0) {
      cookies.add(Cookie.builder()
          .cookie(faker.bothify("????####asz???a?a"))
          .date(dateTime)
          .build());
      size--;
    }
    return cookies;
  }
}
