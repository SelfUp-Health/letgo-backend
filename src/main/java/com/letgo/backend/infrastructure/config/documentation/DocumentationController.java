package com.letgo.backend.infrastructure.config.documentation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
class DocumentationController {

  @Value("${documentation.title:}")
  private String viewTitle;
  @Value("${server.servlet.context-path:}")
  private String serverContextPath;

  private Configuration freemarkerConfiguration;

  @PostConstruct
  private void onPostConstruct() {
    Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
    configuration.setClassForTemplateLoading(this.getClass(), "/templates");
    freemarkerConfiguration = configuration;
  }

  @GetMapping({"/api-documentation", "/api-documentation.html"})
  public void getApiDocumentationView(HttpServletResponse response) {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(formatHtml());
      response.getWriter().flush();
      response.getWriter().close();
    } catch (IOException ex) {
      log.error("Failed to serve API documentation", ex);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private String formatHtml() {
    try {
      Template template = freemarkerConfiguration.getTemplate("api-documentation.html");
      StringWriter stringWriter = new StringWriter();
      Map<String, String> attributes = new HashMap<>();
      attributes.put("title", Optional.ofNullable(viewTitle).orElse("REST API documentation"));
      attributes.put("contextPath", Optional.ofNullable(serverContextPath).orElse(""));
      template.process(attributes, stringWriter);
      return stringWriter.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
