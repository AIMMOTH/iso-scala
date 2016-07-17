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

  def apply(css : String) = {
    html(
      head(
        title2("All Scala!"),
        style2(`type` := "text/css")(css),
        script(src := "javascript.js")),
      body(
        h1("All Scala!"),
        p(backgroundColor := "green")("Push the red button below for an alert!"),
        button(cls := "example-Stylisch-redBackground")(onclick := "example.HelloWorld().alert()")("Press me!")),
        p("Source at ")(a(href := "https://github.com/AIMMOTH/iso-scala")("GitHub"))
      )
  }
}

object Stylisch extends StyleSheet {

  val redBackground = cls(backgroundColor := "red")
}

@WebServlet(name = "htmlBuilder", urlPatterns = Array("/index.scala", "/index.html"))
class Builder extends HttpServlet {

  private val style = Stylisch
  
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = 
    response.getWriter().print(Html(style.styleSheetText).render)
    
}