package fight

/**
  * Class representing a Message sent in the iterative algorithm using GraphX
  * A String represents the action to do (move, being attacked, or being healed), and some Double values
  * represent some useful values: either the new position (x,y,z) for a move action, or (in the case of
  * isAttacked/isHealed), 1) the ID of the attacker/healer mob, 2) the ID of the attacked/healed mob,
  * 3) the damage/healed health value
  * @param actionToPerform Represents the action to do: move, being attacked, or being healed
  * @param value1 Either x position for "move", or the ID of the attacker/healer for "isAttacked" / "isHealed"
  * @param value2 Either y position for "move", or the ID of the attacked/healed mob for "isAttacked" / "isHealed"
  * @param value3 Either z position for "move, or the damage/healed health value for "isAttacked" / "isHealed"
  */
class Message(val actionToPerform:String,
              val value1:Double,
              val value2:Double,
              val value3:Double) extends Serializable
{
  override def toString: String = s"actionToPerform : $actionToPerform value1 : $value1 value2 : $value2 value3 : $value3"
}