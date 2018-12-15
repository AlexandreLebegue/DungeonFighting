package solarVSorcs

import java.util.ArrayList

import model.mob.Mob
import org.apache.spark.SparkContext
import org.apache.spark.graphx.{EdgeContext, Graph, TripletFields, VertexId}

class FightGraph(graph: Graph[Node,String]) extends Serializable
{
  var myGraph:Graph[Node,String] = graph

  /**
    * Sends messages between mobs which are connected in the graph
    * This function sends an Array of Mob objects, so that each mob can then determine if he should attack or move
    * @param ctx
    */
  def sendPositionMessages(ctx: EdgeContext[Node, String, Array[Mob]]): Unit = {
    // Send messages if source and destination mobs are alive
    if (!ctx.srcAttr.mob.isDead && !ctx.dstAttr.mob.isDead)
      {
        // Create an array to store the list of this mob's enemies
        // (This list will grown in the mergePositionMessages function)
        var array1 = Array(ctx.srcAttr.mob)
        var array2 = Array(ctx.dstAttr.mob)

        // Send the messages
        ctx.sendToDst(array1)
        ctx.sendToSrc(array2)
      }
  }


  /**
    * Merges the messages sent to a same mob
    * This function takes care of the messages containing Arrays of Mobs to which the current mob is connected
    * @param mob1
    * @param mob2
    * @return
    */
  def mergePositionMessages(mob1: Array[Mob], mob2: Array[Mob]): Array[Mob] = {
    // Merge arrays to finally obtain a full list of the mob's enemies
    var array = Array(mob1, mob2).flatten
    array
  }


  /**
    * For a given mob, decides which action to perform (move, attack, etc.)
    * - for "move", this function also performs the move
    * - for "attack", it should send some more messages (not implemented yet, TODO)
    * @param vid
    * @param sommet
    * @param bestPosition
    * @return
    */
  def doAction(vid: VertexId, sommet: Node, bestPosition: Array[Mob]): Node =
  {
    var enemies = new ArrayList[Mob]()
    bestPosition.foreach(mob => {
      enemies.add(mob)
    })

    var chosenAction = sommet.mob.think(enemies)

    if (chosenAction == "move")
      {
        sommet.mob.move()
      }
    else if (chosenAction == "attack")
      {
        // TODO

        /*val fields = new TripletFields(true, true, false) // join strategy
        val messages = myGraph.aggregateMessages[Array[Mob]](
          sendAttackMessages,
          mergeAttackMessages,
          fields
        )

        if (messages.isEmpty()) new Node(sommet.id, sommet.position, sommet.mob) // return

        myGraph = myGraph.joinVertices(messages)(
          (vid, sommet, bestTarget) => doAttack(vid, sommet, bestTarget))*/
      }

    new Node(sommet.id, sommet.mob)
  }


  /////////// TODO for attacks ///////////

  def sendAttackMessages(ctx: EdgeContext[Node, String, Array[Mob]]): Unit = {
    // TODO
  }


  def mergeAttackMessages(mob1: Array[Mob], mob2: Array[Mob]): Array[Mob] = {
    // TODO
    null
  }

  // Effectuer l'action
  def doAttack(vid: VertexId, sommet: Node, bestPosition: Array[Mob]): Node =
  {
    // TODO
    null
  }

  /////////// END TODO for attacks ///////////


  /**
    * Executes the iterative algorithm on our graph
    * @param g
    * @param maxIterations
    * @param sc
    * @return
    */
  def execute(g: Graph[Node, String], maxIterations: Int, sc: SparkContext): Graph[Node, String] = {
    //var myGraph = g
    var counter = 0
    val fields = new TripletFields(true, true, false) // join strategy

    def loop1(): Unit = {
      while (true) {

        println("ITERATION " + (counter + 1))
        myGraph.vertices.collect.foreach(vertice =>
        {
          var mob = vertice._2.mob
          println(mob.getName + " : " + mob.getHealth + " - (" + mob.getPosition()(0) + " ; " + mob.getPosition()(1) + " ; " + mob.getPosition()(2) + ")")
        })
        counter += 1
        if (counter == maxIterations) return

        val messages = myGraph.aggregateMessages[Array[Mob]](
          sendPositionMessages,
          mergePositionMessages,
          fields
        )

        if (messages.isEmpty()) return

        myGraph = myGraph.joinVertices(messages)(
          (vid, sommet, bestTarget) => doAction(vid, sommet, bestTarget))

        var printedGraph = myGraph.vertices.collect()
        printedGraph = printedGraph.sortBy(_._1)
        printedGraph.foreach(
          elem => println(elem._2)
        )
      }

    }

    /*println("TEST")
    for (i<-0 until allMobs.size())
      {
        println("allMobs(i) = " + allMobs.get(i))
      }
    myGraph.vertices.collect.foreach(vertex => println(vertex._2.mob))*/

    loop1() //execute loop
    myGraph //return the result graph
  }
}
