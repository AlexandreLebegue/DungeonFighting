package fight

import model.mob.Mob

import scala.collection.mutable

/**
  * Class representing a Node in the Graph
  * @param id The ID of the node
  * @param mob The creature represented by the node
  * @param messagesToTreat The list of messages the mob has to treat for this round (it is filled by the first round
  *                        of messages, and treated by the second round of messages)
  */
class Node(val id:Int,
           val mob:Mob,
           val messagesToTreat:mutable.Queue[Message] = mutable.Queue()) extends Serializable
{
  override def toString: String = s"id : $id mob : $mob"
}