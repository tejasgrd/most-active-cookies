package edu.assignment.executor ;

import edu.assignment.runner.AbstractApplicationRunner;
import edu.assignment.runner.CsvCookieApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieExecutor {

  private static final Logger LOGGER = LoggerFactory.getLogger(CookieExecutor.class);

  public static void main(String[] args) {
    LOGGER.info("Most Active Cookie Application START");
    AbstractApplicationRunner applicationRunner = new CsvCookieApplicationRunner();
    applicationRunner.runApplication(args);
    LOGGER.info("Most Active Cookie Application END");
  }

}
