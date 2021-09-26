package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  ArrayList<Star> starData = new ArrayList<Star>();


  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // TODO: Add your REPL here!
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");
//lab
          MathBot M = new MathBot();
          if (arguments[0].equals("add")) {
            System.out.println(M.add(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2])));
          }
          else if(arguments[0].equals("subtract")) {
            System.out.println(M.subtract(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2])));
          }
//project
          //stars input & reading a CSV file
          
          //I like how compact and easy to follow logically your code is! 
          //Some more comments would be great, but even as is the code is pretty broken up which is nice.
         else if (arguments[0].equals("stars") && arguments.length == 2) {
           CsvReader csv = new CsvReader(starData);
           csv.readCSV(arguments[1]);
           starData = csv.starData;
          }

          else if (arguments[0].equals("naive_neighbors")) {
            if (arguments.length == 5) {
              Star newStar = new Star(923847332, "n/a", Double.parseDouble(arguments[2]), 
                  Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]));
              ArrayList<Integer> closeStars = newStar.nearestStar(newStar, starData, Integer.parseInt(arguments[1]));
              if (!closeStars.isEmpty()) {
                for (Integer n: closeStars) {
                  System.out.println(n);
                }
              }
            }

            //if 3 arguments are inputted in the terminal
            else if (arguments.length == 3) {
              String starName = arguments[2].substring(1, arguments[2].length()-1);
              //some stars have a space in their name, which to the terminal will be read as different arguments, which won't work with this section.
              Star dummyStar = new Star(2130428133, "test",0, 0, 0);
              for (Star starcompare: starData) {
                if (starcompare.properName.equals(starName)) {
                  dummyStar = starcompare;
                }
              }
              if (dummyStar.starID == 2130428133) {
                System.out.println("No such star was found");
              }
              else {
                dummyStar.nearestStar(dummyStar, starData, Integer.parseInt(arguments[1]));
              }
            }
            //else {System.out.println("Incorrect number of arguments");}
          }
          else {
            System.out.println("ERROR: Invalid input");
          }

        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  //I have never seen an exception printer before, so this is very cool and helpful.
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
