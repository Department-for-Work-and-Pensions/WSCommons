package utils

import play.api.libs.ws.WSResponse
import play.api.libs.ws.WS
import play.api.Play.current
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.Await
import scala.xml.Elem

object HttpUtils {

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  class HttpMethodWrapper(url:String, timeout:FiniteDuration){
    def get[T](m:WSResponse => T):T = Await.result(WS.url(url).get().map(m),timeout)

    def put[T](m:WSResponse => T) = new {
      def exec(map:Map[String,Seq[String]] = Map.empty[String,Seq[String]]):T =  Await.result(WS.url(url).put(map).map(m),timeout)
    }

    def post[T](m:WSResponse => T) = new {
      def exec(map:Map[String,Seq[String]] = Map.empty[String,Seq[String]]):T = Await.result(WS.url(url).post(map).map(m),timeout)
      def exec(s:String):T = Await.result(WS.url(url).post(s).map(m),timeout)
      def exec(s:Elem):T = Await.result(WS.url(url).post(s).map(m),timeout)
    }

    def postXml[T](m:WSResponse => T) = new {
      def exec(xml: String): T = Await.result(WS.url(url).withHeaders("Content-Type"->"application/xml").post(xml).map(m), timeout)
    }
  }

}
