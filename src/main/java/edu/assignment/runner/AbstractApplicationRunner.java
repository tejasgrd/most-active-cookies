package edu.assignment.runner;

import edu.assignment.exception.WrongArgumentsException;
import edu.assignment.models.Arguments;
import edu.assignment.models.Cookie;
import edu.assignment.models.FileType;
import edu.assignment.parser.FileParser;
import edu.assignment.parser.FileParserFactory;
import edu.assignment.processor.CookiesProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.List;

public abstract class AbstractApplicationRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractApplicationRunner.class);

  private CookiesProcessor cookiesProcessor;

  public AbstractApplicationRunner(CookiesProcessor cookiesProcessor) {
    this.cookiesProcessor = cookiesProcessor;
  }

  public abstract Arguments parseArguments(String[] args) throws WrongArgumentsException;

  public abstract FileType detectFileType(Arguments arguments);

  public abstract List<Cookie> findMostActiveCookies(List<Cookie> cookies, OffsetDateTime date,
                                                     CookiesProcessor cookiesProcessor);


  public final void runApplication(String[] args) {
    try {
      Arguments arguments = parseArguments(args);
      FileType fileType = detectFileType(arguments);
      FileParser fileParser = FileParserFactory.getFileParser(fileType);
      List<Cookie> cookies = fileParser.parseFile(arguments.getFileName());
      List<Cookie> mostActiveCookies = findMostActiveCookies(cookies, arguments.getDate(), cookiesProcessor);
      LOGGER.info("Most active cookies is/are ");
      for (Cookie cookie : mostActiveCookies) {
        LOGGER.info("Most Active Cookie : " + cookie.getCookie());
      }
    } catch (Exception e) {
      LOGGER.error("Application halted due to error :" + e.getMessage());
    }
  }
}
