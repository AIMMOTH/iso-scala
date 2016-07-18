package example

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import scalatags.Text.all._
import scalatags.stylesheet._
import scalatags.Text.tags2.{ style => style2 }
import scalatags.Text.tags2.{ title => title2 }
import javax.servlet.annotation.WebServlet

object Html {

  def apply(css: String, minified: Boolean = false) = {
    val min = if (minified) ".min" else ""
    html(
      head(
        title2("All Scala!"),
        /*
         * All css files should be read from resources and bundled into one.
         */
        link(rel := "stylesheet", href := s"/css/foundation$min.css"),
        link(rel := "stylesheet", href := "/css/app.css"),
        style2(`type` := "text/css")(css)),
      body(attr("ng-app") := "app")(
        div(cls := "row")(
          div(cls := "large-12 columns")(
            h1("All Scala!"),
            p(backgroundColor := "green")("Push the red button below for an alert!"),
            button(cls := "button example-Stylisch-redBackground example-Stylisch-yellowColor")(onclick := "example.Alerter().hello()")("Press me (when javascript.js is loaded)!")),
          p("Source at ")(a(href := "https://github.com/AIMMOTH/iso-scala/tree/angular")("GitHub")),
          div(cls := "callout alert")(
            h5("This is a callout"),
            p("It has an easy to override visual style, and is appropriately subdued.")))),

      div(attr("ng-controller") := "SimpleController as simple")(
        div(cls := "row")(
          div(cls := "large-12 columns")(
            label("Simple update"),
            p(
              input(`type` := "button", attr("ng-click") := "simple.inc()", value := "+"),
              input(`type` := "button", attr("ng-click") := "simple.dec()", value := "-")),
            p("{{ simple.simpleInt }}")))),

      /*
       * Footer
       * All javascript should be read from resources and bundled into one file.
       */
      script(src := "/js/vendor/jquery.js"),
      script(src := "/js/vendor/what-input.js"),
      script(src := s"/js/vendor/foundation$min.js"),
      script(src := "https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.js"),
      script(src := "javascript.js"),
      /*
       * Start Foundation and Angular
       */
      script()("example.Main().start();"))
  }
}

object Stylisch extends StyleSheet {

  val yellowColor = cls(color := "yellow")

  val redBackground = cls(backgroundColor := "red")
}

@WebServlet(name = "htmlBuilder", urlPatterns = Array("/index.scala", "/index.html"))
class Builder extends HttpServlet {

  private val style = Stylisch

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) =
    response.getWriter().print(Html(style.styleSheetText).render)

}