package com.lodamar.model

final case class Player(name: String, position: Int) {
  def move(pos: Int => Int): Player = copy(position = pos(position))
  def move(pos: Int): Player        = copy(position = pos)
}
final case class DiceRoll(first: Int, second: Int)
final case class State(players: Set[Player], outputs: List[Output]) {
  def addPlayer(name: String): State      = copy(players = players + Player(name, 0))
  def updatePlayer(player: Player): State = copy(players = players.filterNot(_.name == player.name) + player)
  def addOutput(output: Output): State    = copy(outputs = output :: outputs)
  def clearOutputs: State                 = copy(outputs = List.empty)
}

sealed trait Command
final case class AddPlayer(name: String)                        extends Command
final case class MovePlayer(player: Player, diceRoll: DiceRoll) extends Command

sealed trait CommandType
case object Move extends CommandType
case object Add  extends CommandType

sealed trait Box {
  def name: Int => String = _.toString
}

case object Victory extends Box
case object Bounce  extends Box
case object Normal  extends Box
case object Start extends Box {
  override def name: Int => String = _ => "Start"
}
case object Goose extends Box {
  override def name: Int => String = pos => s"$pos, The Goose"
}
final case class Bridge(to: Int) extends Box {
  override def name: Int => String = _ => "The Bridge"
}
