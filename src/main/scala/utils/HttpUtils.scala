package utils

import play.api.libs.ws.Response
import play.api.libs.ws.WS
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.Await

object HttpUtils {

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  class HttpMethodWrapper(url:String, timeout:FiniteDuration){
    def get[T](m:Response => T):T = Await.result(WS.url(url).get().map(m),timeout)

    def put[T](m:Response => T) = new {
      def exec(map:Map[String,Seq[String]] = Map.empty[String,Seq[String]]):T =  Await.result(WS.url(url).put(map).map(m),timeout)
    }

    def post[T](m:Response => T) = new {
      def exec(map:Map[String,Seq[String]] = Map.empty[String,Seq[String]]):T = Await.result(WS.url(url).post(map).map(m),timeout)
    }

    def postXml[T](m:Response => T) = new {
      def exec(xml: String): T = Await.result(WS.url(url).withHeaders("Content-Type"->"application/xml").post(xml).map(m), timeout)
    }
  }

}
