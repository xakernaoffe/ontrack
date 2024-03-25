object Task5 {
  // Using following trait we abstract not only over MyAlg implementation
  // but we also abstract over effect F. This means that at so called 'end-of-the-world` point
  // in application (main method, http endpoint etc) we can select how program will be interpreted.
  //
  // For example in production code we could replace F by IO from cats lib
  // and for simplicity in tests we could use other type like Either, Try or similar.
  //
  // This pattern of organizing code called "Final tagless"
  trait MyAlg[F[_]] {
    def insertItSomewhere(someInt: Int): F[Unit]
    def doSomething(someInt: Int): F[Int]
  }
}
