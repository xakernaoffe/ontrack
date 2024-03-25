import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

object Task1 {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  private def f1: Future[Unit] = Future {
    println("Calculating f1")
  }

  private def f2: Future[Unit] = Future {
    println("Calculating f2")
  }

  private def f3: Future[Unit] = Future {
    println("Calculating f3")
  }

  private def f4: Future[Unit] = Future {
    println("Calculating f4")
  }

  def main(args: Array[String]): Unit = {
    // Task 1: There are no dependencies between the functions
    //
    // Explanation:
    // Feature is eager structure. Computation will start as soon as new instance of Future is constructed
    // and underlying ExecutionContext has free capacity
    val f1Val: Future[Unit] = f1
    val f2Val: Future[Unit] = f2
    val f3Val: Future[Unit] = f3
    val f4Val: Future[Unit] = f4
    // All futures could be combined in single Future holding list of results via Future.seq
    Await.result(Future.sequence(Seq(f1Val, f2Val, f3Val, f4Val)), 1.minute)
    println("Calculated value for solution 1")

    // Task two: f4 depends on f3 which depends on f2 which depends on f1
    //
    // Explanation:
    // FlatMap allows to sequence one Future after the previous one completed with success.
    val dependentFutures: Future[Unit] = for {
      _ <- f1
      _ <- f2
      _ <- f3
      _ <- f4
    } yield ()
    Await.result(dependentFutures, 1.minute)
    println("Calculated value for solution 2")

    // Task three: f4 depends on f3 and f2, and f3 and f2 both depend on f1

    val f = {
      // Wait until f1 is completed
      f1.flatMap { _ =>
        // Run f2, f3 in parallel using result of f1 as input
        Future.sequence(List(f2, f3)).flatMap {
          case f2Val :: f3Val :: Nil =>
            // use result from f2 and f3 as inputs for f4
            f4
        }
      }
    }
    Await.result(f, 1.minute)
    println("Calculated value for solution 3")
  }
}
