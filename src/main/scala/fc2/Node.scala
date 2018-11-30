package fc2

import model.mob.Mob

class Node(val id:Int, val color:Int=1, val knighthood:Boolean=false,
           val position:Array[Int], // <=> tieBreakValue
           val mob:Mob) extends Serializable
{
  override def toString: String = s"id : $id tiebreakValue : $position color : $color knighthood : $knighthood"
  // Here the tieBreakValue is the distance between two nodes (???)
}
