package fc2

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{EdgeContext, Graph, TripletFields, VertexId}

class FightGraph extends Serializable
{
  // Chaque noeud envoie à tous ses voisins, un Array contenant sa position et sa distance par rapport à son ennemi
  def sendPositionMessages(ctx: EdgeContext[Node, String, Array[Int]]): Unit = {
    // Envoi de messages si les monstres expéditeur et destinataire sont vivants
    if (ctx.srcAttr.mob.isAlive && ctx.dstAttr.mob.isAlive)
      {
        // Calcul de la distance entre les deux monstres
        var positionMobSrc = ctx.srcAttr.position
        var positionMobDst = ctx.dstAttr.position
        var distance = math.sqrt(math.pow(positionMobDst(0) - positionMobSrc(0), 2) + math.pow(positionMobDst(1) - positionMobSrc(1), 2) + math.pow(positionMobDst(2) - positionMobSrc(2), 2) ).toInt

        // Construction d'un Array contenant la position et la distance
        var array1 = Array(positionMobSrc(0), positionMobSrc(1), positionMobSrc(2), distance)
        var array2 = Array(positionMobDst(0), positionMobDst(1), positionMobDst(2), distance)

        // Envoi des messages

        ctx.sendToDst(array1)
        ctx.sendToSrc(array2)
      }
  }

  // On choisit l'adversaire le plus proche
  // TODO: Plutôt garder tous les mobs assez proches, plutôt que seulement le plus proche?
  def mergePositionMessages(pos1: Array[Int], pos2: Array[Int]): Array[Int] = {
    // Comparer les positions pour déterminer lequel est le plus proche
    if (pos1(3) < pos2(3)) pos1
    else pos2
  }

  // Effectuer l'action
  def doAction(vid: VertexId, sommet: Node, bestTarget: Array[Int]): Node = {

    var chosenAction = sommet.mob.think(bestTarget)
    if (chosenAction == "attaque")
      {
        // Envoyer un message pour signaler à l'ennemi qu'il est attaqué
        // (et qu'il perde de la vie)
      }

    sommet
  }

  def execute(g: Graph[Node, String], maxIterations: Int, sc: SparkContext): Graph[Node, String] = {
    var myGraph = g
    var counter = 0
    val fields = new TripletFields(true, true, false) // join strategy

    def loop1(): Unit = {
      while (true) {

        println("ITERATION " + (counter + 1))
        counter += 1
        if (counter == maxIterations) return

        val messages = myGraph.aggregateMessages[Array[Int]](
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

    loop1() //execute loop
    myGraph //return the result graph
  }
}
