import cats.effect.IO

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Task4 {

  def f[A](a: A): Future[A] = Future {
    a
  }

  // Remark, honestly haven't understood this task clearly
  // As solution decided to wrap Future into IO and make it lazy and referential transparent.
  def g[A](a: A): IO[A] =
    IO.fromFuture(IO(f(a)))
}
