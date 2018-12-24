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

  // Set the positions of the mobs
  // TODO Generate random initial positions for the mobs
  val solarPosition = Array(150.0,150.0,50.0)
  solar.setPosition(solarPosition)
  val worgsRider1Position = Array(10.0,10.0,0.0)
  worgsRider1.setPosition(worgsRider1Position)
  val worgsRider2Position = Array(20.0,20.0,0.0)
  worgsRider2.setPosition(worgsRider2Position)
  val worgsRider3Position = Array(15.0,40.0,0.0)
  worgsRider3.setPosition(worgsRider3Position)
  val worgsRider4Position = Array(70.0,50.0,0.0)
  worgsRider4.setPosition(worgsRider4Position)
  val worgsRider5Position = Array(50.0,50.0,0.0)
  worgsRider5.setPosition(worgsRider5Position)
  val worgsRider6Position = Array(60.0,10.0,0.0)
  worgsRider6.setPosition(worgsRider6Position)
  val worgsRider7Position = Array(20.0,11.0,0.0)
  worgsRider7.setPosition(worgsRider7Position)
  val worgsRider8Position = Array(50.0,40.0,0.0)
  worgsRider8.setPosition(worgsRider8Position)
  val worgsRider9Position = Array(10.0,54.0,0.0)
  worgsRider9.setPosition(worgsRider9Position)
  val warLordPosition = Array(40.0,10.0,0.0)
  warLord.setPosition(warLordPosition)
  val barbaresOrc1Position = Array(10.0,50.0,0.0)
  barbaresOrc1.setPosition(barbaresOrc1Position)
  val barbaresOrc2Position = Array(100.0,200.0,0.0)
  barbaresOrc2.setPosition(barbaresOrc2Position)
  val barbaresOrc3Position = Array(15.0,15.0,0.0)
  barbaresOrc3.setPosition(barbaresOrc3Position)
  val barbaresOrc4Position = Array(30.0,15.0,0.0)
  barbaresOrc4.setPosition(barbaresOrc4Position)

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
