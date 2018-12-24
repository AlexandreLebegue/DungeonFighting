package fight

import model.mob._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Edge, Graph}


object main extends App
{
  val conf = new SparkConf()
    .setAppName("Dungeon Fight")
    .setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")

  // Create all creatures
  val solar = new Solar(1)
  val worgsRider1 = new WorgRider(2)
  val worgsRider2 = new WorgRider(3)
  val worgsRider3 = new WorgRider(4)
  val worgsRider4 = new WorgRider(5)
  val worgsRider5 = new WorgRider(6)
  val worgsRider6 = new WorgRider(7)
  val worgsRider7 = new WorgRider(8)
  val worgsRider8 = new WorgRider(9)
  val worgsRider9 = new WorgRider(10)
  val warLord = new WarLord(11)
  val barbaresOrc1 = new BarbareOrc(12)
  val barbaresOrc2 = new BarbareOrc(13)
  val barbaresOrc3 = new BarbareOrc(14)
  val barbaresOrc4 = new BarbareOrc(15)

  // Generate random initial positions for the mobs
  val positionedMobs = new java.util.ArrayList[Mob]()
  solar.generatePosition(positionedMobs)
  positionedMobs.add(solar)
  worgsRider1.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider1)
  worgsRider2.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider2)
  worgsRider3.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider3)
  worgsRider4.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider4)
  worgsRider5.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider5)
  worgsRider6.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider6)
  worgsRider7.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider7)
  worgsRider8.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider8)
  worgsRider9.generatePosition(positionedMobs)
  positionedMobs.add(worgsRider9)
  warLord.generatePosition(positionedMobs)
  positionedMobs.add(warLord)
  barbaresOrc1.generatePosition(positionedMobs)
  positionedMobs.add(barbaresOrc1)
  barbaresOrc2.generatePosition(positionedMobs)
  positionedMobs.add(barbaresOrc2)
  barbaresOrc3.generatePosition(positionedMobs)
  positionedMobs.add(barbaresOrc3)
  barbaresOrc4.generatePosition(positionedMobs)

  // Creating nodes (mobs)
  var myVertices = sc.makeRDD(Array(
    (1L, new Node(id = 1, mob = solar)),
    (2L, new Node(id = 2, mob = worgsRider1)),
    (3L, new Node(id = 3, mob = worgsRider2)),
    (4L, new Node(id = 4, mob = worgsRider3)),
    (5L, new Node(id = 5, mob = worgsRider4)),
    (6L, new Node(id = 6, mob = worgsRider5)),
    (7L, new Node(id = 7, mob = worgsRider6)),
    (8L, new Node(id = 8, mob = worgsRider7)),
    (9L, new Node(id = 9, mob = worgsRider8)),
    (10L, new Node(id = 10, mob = worgsRider9)),
    (11L, new Node(id = 11, mob = warLord)),
    (12L, new Node(id = 12, mob = barbaresOrc1)),
    (13L, new Node(id = 13, mob = barbaresOrc2)),
    (14L, new Node(id = 14, mob = barbaresOrc3)),
    (15L, new Node(id = 15, mob = barbaresOrc4))
  ))

  // Creating edges (links between mobs)
  var myEdges = sc.makeRDD(Array(
    Edge(1L, 2L, "1"), Edge(1L, 3L, "2"), Edge(1L, 4L, "3"),
    Edge(1L, 5L, "4"), Edge(1L, 6L, "5"), Edge(1L, 7L, "6"),
    Edge(1L, 8L, "7"), Edge(1L, 9L, "8"), Edge(1L, 10L, "9"),
    Edge(1L, 11L, "10"), Edge(1L, 12L, "11"), Edge(1L, 13L, "12"),
    Edge(1L, 14L, "13"), Edge(1L, 15L, "14")
  ))

  // Creating the graph and execute the iterative algorithm
  var myGraph = Graph(myVertices, myEdges)
  val algoColoring = new FightGraph()
  val res = algoColoring.execute(myGraph, 20)
}
