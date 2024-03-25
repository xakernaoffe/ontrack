import cats.Monad
import cats.implicits.catsSyntaxFlatMapOps

object Task6 {

  trait MyAlg[F[_]] {
    def insertItSomewhere(someInt: Int): F[Unit]
    def doSomething(someInt: Int): F[Int]
  }

  // To use MyAlg we introduce restriction that type F should have implicit instance of Monad available.
  // This way we can sequence computations via `flatMap` method
  class MyProg[F[_]](myAlg: MyAlg[F])(implicit F: Monad[F]) {
    def checkThenAddIt(someInt: Int): F[Unit] =
      myAlg.doSomething(someInt) >>=
        (result => myAlg.insertItSomewhere(result))
  }
}
