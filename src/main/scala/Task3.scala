import scala.annotation.tailrec

object Task3 {
  private def incrementNumber(numbers: Seq[Int]): Seq[Int] = {
    @tailrec
    def incrementInternally(reversedNumbers: Seq[Int], result: Seq[Int], increment: Boolean): Seq[Int] = {
      reversedNumbers.headOption match {
        case None =>
          if (increment)
            result.prepended(1)
          else
            result

        case Some(value) if increment =>
          val incrementedValue = value + 1
          if (incrementedValue > 9)
            incrementInternally(reversedNumbers.tail, result.prepended(0), increment = true)
          else
            incrementInternally(reversedNumbers.tail, result.prepended(incrementedValue), increment = false)

        case Some(value) =>
          incrementInternally(reversedNumbers.tail, result.prepended(value), increment = false)
      }
    }

    if (numbers.nonEmpty)
      incrementInternally(numbers.reverse, Seq.empty[Int], increment = true)
    else
      numbers
  }

  def main(args: Array[String]): Unit = {
    assert(incrementNumber(Seq.empty[Int]) == Nil)
    assert(incrementNumber(Seq(0)) == Seq(1))
    assert(incrementNumber(Seq(1, 2, 3)) == Seq(1, 2, 4))
    assert(incrementNumber(Seq(9, 9, 9)) == Seq(1, 0, 0, 0))
  }
}
