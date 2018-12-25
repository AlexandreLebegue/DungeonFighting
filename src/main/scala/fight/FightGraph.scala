package fight

import java.util

import model.mob.Mob
import org.apache.spark.graphx.{EdgeContext, Graph, TripletFields, VertexId}


/**
  * Class executing an iterative algorithm, using GraphX, on a Graph of creatures,
  * to simulate a fight between teams of creatures
  */
class FightGraph() extends Serializable
{
  /**
    * List of all mobs of the graph
    * It is updated at the end of each iteration
    */
  var allMobs:java.util.ArrayList[Mob] = new util.ArrayList[Mob]() // List of all the mobs of the fight


  ////////// Functions for the first round of message - Determine the action of the mob //////////


  /**
    * Sends messages between mobs which are connected in the graph
    * This function sends an Array of Mob objects, so that each mob can then determine if he should move, attack, or heal
    * @param ctx EdgeContext
    */
  def sendMobDataMessages(ctx: EdgeContext[Node, String, Array[Mob]]): Unit = {
    // Send messages if source and destination mobs are alive
    if (!ctx.srcAttr.mob.isDead && !ctx.dstAttr.mob.isDead)
      {
        // Create an array to store the list of this mob's relatives (allies and enemies)
        // (This list will grow in the mergeMobDataMessages function)
        val array1 = Array(ctx.srcAttr.mob)
        val array2 = Array(ctx.dstAttr.mob)

        // Send the messages
        ctx.sendToDst(array1)
        ctx.sendToSrc(array2)
      }
  }


  /**
    * Merges the messages sent to a same mob
    * This function takes care of the messages containing Arrays of Mobs to which the current mob is connected
    * @param mob1 First mob data received
    * @param mob2 Second mob data received
    * @return An array merging the data about the two mobs
    */
  def mergeMobDataMessages(mob1: Array[Mob], mob2: Array[Mob]): Array[Mob] = {
    // Merge arrays to finally obtain a full list of the mob's relatives (allies and enemies)
    val array = Array(mob1, mob2).flatten
    array
  }


  /**
    * For a given mob, decides which action to perform (move, attack, heal)
    * It also updates the Queue of messages of the node, to put in it all the messages representing
    * the next actions to do
    * @param vid Vertex ID
    * @param vertex Node object representing the vertex
    * @param relativeMobs Array containing all relative mobs (allies and enemies)
    * @return A new Node representing the current mob in the graph
    */
  def determineAction(vid: VertexId, vertex: Node, relativeMobs: Array[Mob]): Node =
  {
    val relatives = new util.ArrayList[Mob]()
    relativeMobs.foreach(mob => {
      relatives.add(mob)
    })

    val chosenAction = vertex.mob.think(relatives)

    if (chosenAction.equals("move"))
      {
        // Send a message "move" containing the new position
        val newPosition = vertex.mob.getMoveToPerform(allMobs)
        vertex.messagesToTreat += new Message("move", newPosition(0), newPosition(1), newPosition(2))
        println("Mob " + vertex.mob + " will move to (" + newPosition(0) + " ; " + newPosition(1) + " ; " + newPosition(2) + ")")
        new Node(vertex.id, vertex.mob, vertex.messagesToTreat)
      }
    else if (chosenAction.equals("heal"))
      {
        val mobs = vertex.mob.determineAlliesToHeal(allMobs)
        // Filling a queue with the Java ArrayList content, to call foreach method on the queue and update the accumulator
        // We have to do that, because accumulators should be modified in action operations
        for (i <- 0 until mobs.size())
        {
          val mob = mobs.get(i)
          val healedHealth = vertex.mob.getHealthToHeal
          vertex.messagesToTreat += new Message("heal", vertex.mob.getId, mob.getId, healedHealth)
          println("Mob " + vertex.mob + " will heal " + mob + " : healed health = " + healedHealth)
        }

        new Node(vertex.id, vertex.mob, vertex.messagesToTreat)
      }
    else //if (chosenAction.equals("attack"))
      {
        val mobs = vertex.mob.determineEnemiesToAttack(allMobs)
        // Filling a queue with the Java ArrayList content, to call foreach method on the queue and update the accumulator
        // We have to do that, because accumulators should be modified in action operations
        for (i <- 0 until mobs.size())
        {
          val mob = mobs.get(i)
          val weapon = vertex.mob.determineWeaponToUse(mob)
          val damage = vertex.mob.getDamageForAttack(mob, weapon)
          vertex.messagesToTreat += new Message("attack", vertex.mob.getId, mob.getId, damage)
          println("Mob " + vertex.mob + " is trying to attack " + mob + " : damage = " + damage)
        }

        new Node(vertex.id, vertex.mob, vertex.messagesToTreat)
      }
  }


  ////////// Functions for the second round of message - Perform the actions //////////


  /**
    * Sends messages between mobs which are connected in the graph
    * This function sends a List of Message objects (see Message.scala class):
    * - if the action to perform is "move", we send the same Message to the moving mob (containing the new position)
    * - if the action to perform is "attack", we send the same Message to the attacked mob (containing the damage it has to take)
    * - if the action to perform is "heal", we send the same Message to the healed mob (containing the healed health it has to get back)
    * @param ctx EdgeContext
    */
  def sendActionMessages(ctx: EdgeContext[Node, String, List[Message]]): Unit = {
    if(!ctx.srcAttr.mob.isDead)
      {
        ctx.srcAttr.messagesToTreat.foreach(message => {
          if (message.actionToPerform == "move") ctx.sendToSrc(List(message)) // We can just directly send the message
          else if (message.actionToPerform == "attack")
            {
              // Here we need to send the message to the attacked mob, to tell him he is attacked
              // In order to do that, we need to find if the current dst Mob corresponds to the attacked mob
              if (!ctx.dstAttr.mob.isDead && message.value2 == ctx.dstAttr.mob.getId)
                {
                  ctx.sendToDst(List(new Message("isAttacked", message.value1, message.value2, message.value3)))
                }
            }
          else if (message.actionToPerform.equals("heal"))
            {
              // Here we need to send the message to the healed mob, to tell him he is healed
              // In order to do that, we need to find if the current dst Mob corresponds to the healed mob
              if (!ctx.dstAttr.mob.isDead && message.value2 == ctx.dstAttr.mob.getId)
                {
                  ctx.sendToDst(List(new Message("isHealed", message.value1, message.value2, message.value3)))
                }
            }
        })
      }

    if(!ctx.dstAttr.mob.isDead)
      {
        ctx.dstAttr.messagesToTreat.foreach(message => {
          if (message.actionToPerform.equals("move")) ctx.sendToDst(List(message)) // We just need to send an array containing the message
          else if (message.actionToPerform.equals("attack"))
          {
            // Again, we need to send the message to the attacked mob
            if (!ctx.srcAttr.mob.isDead && message.value2 == ctx.srcAttr.mob.getId)
            {
              ctx.sendToSrc(List(new Message("isAttacked", message.value1, message.value2, message.value3)))
            }
          }
          else if (message.actionToPerform.equals("heal"))
          {
            // Again, we need to send the message to all healed mobs
            if (!ctx.srcAttr.mob.isDead && message.value2 == ctx.srcAttr.mob.getId)
            {
              ctx.sendToSrc(List(new Message("isHealed", message.value1, message.value2, message.value3)))
            }
          }
        })
      }
  }


  /**
    * Merges the messages sent to a same mob
    * This function takes care of the lists of Messages received by a same mob: it ensures that only one message of each type
    * (move, isAttacked, isHealed) is present in the list:
    * - it keeps only the first move message it finds (if there was several move messages, they would contain the same new position)
    * - it sums the damages of all isAttacked messages, and stores it in a single Message
    * - it sums the healed health values of all isHealed messages, and stores it in a single Message
    * @param messages1 First list of messages received
    * @param messages2 Second list of messages received
    * @return A list of merged messages
    */
  def mergeActionMessages(messages1: List[Message], messages2: List[Message]): List[Message] = {
    // We need to get only one of the move messages
    // (since all the move messages will contain the same position for a given mob)
    val moveMessages1 = messages1.filter(msg => msg.actionToPerform.equals("move"))
    var moves = List[Message]()
    if (moveMessages1.nonEmpty) { moves = List(moveMessages1.head) } // Take the first element
    else
      {
        val moveMessages2 = messages2.filter(msg => msg.actionToPerform.equals("move"))
        if (moveMessages2.nonEmpty) { moves = List(moveMessages2.head) }
      }

    // For isAttacked and isHealed, we should merge the values of ALL messages in a single message

    // isAttacked messages
    val isAttackedMessages1 = messages1.filter(msg => msg.actionToPerform.equals("isAttacked"))
    val isAttackedMessages2 = messages2.filter(msg => msg.actionToPerform.equals("isAttacked"))

    var totalDamage = 0.0
    isAttackedMessages1.foreach(msg => totalDamage += msg.value3.toDouble)
    isAttackedMessages2.foreach(msg => totalDamage += msg.value3.toDouble)

    var isAttacked = List[Message]()

    // These checks are meant to avoid "NoSuchElementException: head of empty list"
    if (isAttackedMessages1.nonEmpty || isAttackedMessages2.nonEmpty)
      {
        // Here we don't care for values 1 and 2, they are not useful anymore
        isAttacked = List(new Message("isAttacked", 0, 0, totalDamage))
        // totalDamage = 0.0 if there is no isAttacked message
      }

    // isHealed messages
    val isHealedMessages1 = messages1.filter(msg => msg.actionToPerform.equals("isHealed"))
    val isHealedMessages2 = messages2.filter(msg => msg.actionToPerform.equals("isHealed"))

    var totalHealedHealth = 0.0
    isHealedMessages1.foreach(msg => println(msg))
    isHealedMessages2.foreach(msg => println(msg))
    isHealedMessages1.foreach(msg => totalHealedHealth += msg.value3.toDouble)
    isHealedMessages2.foreach(msg => totalHealedHealth += msg.value3.toDouble)

    var isHealed = List[Message]()

    // These checks are meant to avoid "NoSuchElementException: head of empty list"
    if (isHealedMessages1.nonEmpty || isHealedMessages2.nonEmpty)
    {
      // Here we don't care for values 1 and 2, they are not useful anymore
      isHealed = List(new Message("isHealed", 0, 0, totalHealedHealth))
      // totalHealedHealth = 0.0 if there is no isHealed message
    }

    // Finally, we group in a new List, the merged messages for move, isAttacked and isHealed actions
    if (moves.nonEmpty && isAttacked.nonEmpty && isHealed.nonEmpty)
    {
      List(moves, isAttacked, isHealed).flatten
    }
    else if (moves.isEmpty && isAttacked.nonEmpty && isHealed.nonEmpty)
    {
      List(isAttacked, isHealed).flatten
    }
    else if (moves.nonEmpty && isAttacked.isEmpty && isHealed.nonEmpty)
    {
      List(moves, isHealed).flatten
    }
    else if (moves.nonEmpty && isAttacked.nonEmpty && isHealed.isEmpty)
    {
      List(moves, isAttacked).flatten
    }
    else if (moves.isEmpty && isAttacked.isEmpty && isHealed.nonEmpty)
    {
      List(isHealed).flatten
    }
    else if (moves.isEmpty && isAttacked.nonEmpty && isHealed.isEmpty)
    {
      List(isAttacked).flatten
    }
    else //if (moves.nonEmpty && isAttacked.isEmpty && isHealed.isEmpty)
    {
      List(moves).flatten
    }
  }


  /**
    * For a given mob, performs the actions contained in the list of merged messages:
    * - moving the mob
    * - giving him the total damage received
    * - giving him the total healed health received
    * @param vid Vertex ID
    * @param vertex Node representing the vertex
    * @param mergedMessages List of messages containing data about the actions to perform (new position, total damage, total healed health)
    * @return A new Node representing the current mob in the graph
    */
  def performActions(vid: VertexId, vertex: Node, mergedMessages: List[Message]): Node =
  {
    mergedMessages.foreach(message => {
      if (message.actionToPerform.equals("move"))
      {
        val newPosition = Array[Double](message.value1, message.value2, message.value3)
        vertex.mob.move(newPosition)
      }
      else if (message.actionToPerform.equals("isHealed"))
        {
          vertex.mob.heal(message.value3.toInt)
          println("Mob " + vertex.mob + " has been healed! Total health given = " + message.value3.toInt)
        }
      else if (message.actionToPerform.equals("isAttacked"))
      {
        vertex.mob.takeDamage(message.value3.toInt)
        println("Mob " + vertex.mob + " has been attacked! Total damage = " + message.value3.toInt)
      }
      //else // if (message.actionToPerform.equals("isHealed")) {}
    })

    // Update the alive or dead state of the mob for the next iteration
    vertex.mob.updateLifeState()

    vertex.messagesToTreat.dequeueAll(message => true)

    new Node(vertex.id, vertex.mob, vertex.messagesToTreat)
  }


  ////////// Execution of the iterative algorithm //////////


  /**
    * Executes the iterative algorithm on our graph
    * @param graph Graph
    * @param maxIterations Maximal number of iterations
    * @return The processed graph
    */
  def execute(graph: Graph[Node, String], maxIterations: Int): Graph[Node, String] = {
    val fields = new TripletFields(true, true, false) // Join strategy
    var myGraph = graph

    println()
    println("INITIAL GRAPH")
    myGraph.vertices.collect.foreach(vertex =>
    {
      val mob = vertex._2.mob
      println(mob.toString)
    })
    println()

    def loop1(): Unit = {
      var iterationNumber = 0
      while (true)
        {
          // Update the Java ArrayList of all mobs (which will be useful since it is used in parameter in many functions in the Mob class)
          allMobs.clear()
          val allVertices = myGraph.vertices.collect()
          allVertices.foreach(vertex => {
            allMobs.add(vertex._2.mob)
          })

          // Start an iteration
          iterationNumber += 1
          if (iterationNumber == maxIterations) { println("Max iterations reached"); return }

          // First, determine the action to perform
          val actionMessages = myGraph.aggregateMessages[Array[Mob]](
            sendMobDataMessages,
            mergeMobDataMessages,
            fields
          )

          // If no message has been sent (<=> no action should be performed), then stop here
          if (actionMessages.isEmpty()) { println("No more messages sent"); return }

          myGraph = myGraph.joinVertices(actionMessages)(
            (vid, vertex, enemyMobs) => determineAction(vid, vertex, enemyMobs))

          // Then, perform those actions
          val attackMessages = myGraph.aggregateMessages[List[Message]](
            sendActionMessages,
            mergeActionMessages,
            fields
          )

          myGraph = myGraph.joinVertices(attackMessages)(
            (vid, vertex, totalDamage) => performActions(vid, vertex, totalDamage)
          )

          // Finally, show the results in IntelliJ console
          var printedGraph = myGraph.vertices.collect()
          println()
          println("GRAPH AFTER ITERATION " + iterationNumber)
          printedGraph = printedGraph.sortBy(_._1)
          printedGraph.foreach(
            elem => println(elem._2)
          )
          println()

        } // end while(true)

    } // end loop

    loop1() // Execute the loop
    myGraph // Return final graph
  }
}
