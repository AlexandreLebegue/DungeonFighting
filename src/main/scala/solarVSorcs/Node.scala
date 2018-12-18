package solarVSorcs

import model.mob.Mob

class Node(val id:Int,
           val mob:Mob) extends Serializable
{
  override def toString: String = s"id : $id mob : $mob"
}