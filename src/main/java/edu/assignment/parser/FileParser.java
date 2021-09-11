package edu.assignment.parser;

import edu.assignment.models.Cookie;

import java.io.IOException;
import java.util.List;

public interface FileParser {
  List<Cookie> parseFile(String filePath) throws IOException;
}
