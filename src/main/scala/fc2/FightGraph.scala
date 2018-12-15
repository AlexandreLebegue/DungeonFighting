package fc2

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
    // Envoi de messages si les monstres expéditeur et destinataire sont vivants
    if (!ctx.srcAttr.mob.isDead && !ctx.dstAttr.mob.isDead)
      {
        // On crée un array pour pouvoir stocker la liste des ennemis du mob
        // (Cette liste sera donc agrandie dans le mergePositionMessages)
        var array1 = Array(ctx.srcAttr.mob)
        var array2 = Array(ctx.dstAttr.mob)

        // Envoi des messages
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
    // Comparer les positions pour déterminer lequel est le plus proche
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



  // Chaque noeud envoie à tous ses voisins, un Array contenant sa position et sa distance par rapport à son ennemi
  def sendAttackMessages(ctx: EdgeContext[Node, String, Array[Mob]]): Unit = {
    // TODO
  }

  // On choisit l'adversaire le plus proche
  // TODO: Plutôt garder tous les mobs assez proches, plutôt que seulement le plus proche?
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
