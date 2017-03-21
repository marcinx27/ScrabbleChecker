object ScrabbleChecker{
  def main(args: Array[String]): Unit = {

    val tileList: List[Tile] = List(
      Tile('a', 9, 1),
      Tile('b', 2, 3),
      Tile('c', 2, 3),
      Tile('d', 4, 2),
      Tile('e', 12, 1),
      Tile('f', 2, 4),
      Tile('g', 3, 2),
      Tile('h', 2, 4),
      Tile('i', 9, 1),
      Tile('j', 1, 8),
      Tile('k', 1, 5),
      Tile('l', 4, 1),
      Tile('m', 2, 3),
      Tile('n', 6, 1),
      Tile('o', 8, 1),
      Tile('p', 2, 3),
      Tile('q', 1, 10),
      Tile('r', 6, 1),
      Tile('s', 4, 1),
      Tile('t', 6, 1),
      Tile('u', 4, 1),
      Tile('v', 2, 4),
      Tile('w', 2, 4),
      Tile('x', 1, 8),
      Tile('y', 2, 4),
      Tile('z', 1, 10)
    )

    val dictionary: List[String] = scala.io.Source.fromFile("/usr/share/dict/words").getLines.toList

    val wordToCheck: String = "accrescence"//scala.io.StdIn.readLine("What word do you want to check?\n")

    if(dictionary.contains(wordToCheck)) {

      val wordTiles: List[Tile] = wordToCheck.toList.flatMap { letter: Char =>
        tileList.filter(_.letter == letter)
      }

      val badTiles: Set[(Tile, Int)] = wordTiles.filter { tileToCheck: Tile =>
        wordTiles.count(_ == tileToCheck) > tileToCheck.totalAmount
      }.map(tile => (tile, wordTiles.count(_ == tile))).toSet


      val totalTilesLeftOver: Int = badTiles.toList.map(tuple => tuple._2 - tuple._1.totalAmount).sum


      println(wordTiles)
      println(badTiles)
      println(totalTilesLeftOver)

      if(totalTilesLeftOver > 2) {
        println(s"Not enough letters to make $wordToCheck.")
      } else if(totalTilesLeftOver == 0) {
        println(s"$wordToCheck is worth ${wordTiles.map(_.value).sum}")
      } else {
        val toSub: Int = badTiles.toList.map(tuple => (tuple._2 - tuple._1.totalAmount) * tuple._1.value).sum
        println(s"$wordToCheck is worth ${wordTiles.map(_.value).sum - toSub}")
      }

    } else {
      println(s"$wordToCheck is not in the dictionary.")
    }

  }

  case class Tile(letter: Char, totalAmount: Int, value: Int)
}