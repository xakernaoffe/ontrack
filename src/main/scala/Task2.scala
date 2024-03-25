// Given two functions f1 and f2, implement f3 by composing f1 and f2
object Task2 {
  val f1: (Int, Int) => Int = (a, b) => a + b
  val f2: Int => String = _.toString

  val f3: (Int, Int) => String = (a, b) => f1.tupled.andThen(f2).apply((a, b))


  def main(args: Array[String]): Unit = {
    assert(f3(1, 3) == "4")
  }
}
