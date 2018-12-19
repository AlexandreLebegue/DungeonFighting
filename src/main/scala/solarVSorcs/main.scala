package solarVSorcs

import java.util.{ArrayList, Collections}

import model.mob._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Edge, Graph}


object main extends App {
  val conf = new SparkConf()
    .setAppName("Dungeon Fight")
    .setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")

  // Create all creatures
  val solar = new Solar()
  println("SOLAR ADD = " + solar)
  val worgsRider1 = new WorgRider()
  val worgsRider2 = new WorgRider()
  val worgsRider3 = new WorgRider()
  val worgsRider4 = new WorgRider()
  val worgsRider5 = new WorgRider()
  val worgsRider6 = new WorgRider()
  val worgsRider7 = new WorgRider()
  val worgsRider8 = new WorgRider()
  val worgsRider9 = new WorgRider()
  val warLord = new WarLord()
  val barbaresOrc1 = new BarbareOrc()
  val barbaresOrc2 = new BarbareOrc()
  val barbaresOrc3 = new BarbareOrc()
  val barbaresOrc4 = new BarbareOrc()

  // Set the positions of the mobs
  val solarPosition = Array(0.0,0.0,50.0)
  solar.setPosition(solarPosition)
  val worgsRider1Position = Array(1000.0,1000.0,1000.0)
  worgsRider1.setPosition(worgsRider1Position)
  val worgsRider2Position = Array(2000.0,2000.0,0.0)
  worgsRider2.setPosition(worgsRider2Position)
  val worgsRider3Position = Array(1500.0,4000.0,0.0)
  worgsRider3.setPosition(worgsRider3Position)
  val worgsRider4Position = Array(7000.0,5000.0,0.0)
  worgsRider4.setPosition(worgsRider4Position)
  val worgsRider5Position = Array(5000.0,5000.0,0.0)
  worgsRider5.setPosition(worgsRider5Position)
  val worgsRider6Position = Array(6000.0,1000.0,0.0)
  worgsRider6.setPosition(worgsRider6Position)
  val worgsRider7Position = Array(2000.0,1100.0,0.0)
  worgsRider7.setPosition(worgsRider7Position)
  val worgsRider8Position = Array(5000.0,4000.0,0.0)
  worgsRider8.setPosition(worgsRider8Position)
  val worgsRider9Position = Array(1000.0,5400.0,0.0)
  worgsRider9.setPosition(worgsRider9Position)
  val warLordPosition = Array(4500.0,1000.0,0.0)
  warLord.setPosition(warLordPosition)
  val barbaresOrc1Position = Array(1000.0,5000.0,0.0)
  barbaresOrc1.setPosition(barbaresOrc1Position)
  val barbaresOrc2Position = Array(1000.0,20000.0,0.0)
  barbaresOrc2.setPosition(barbaresOrc2Position)
  val barbaresOrc3Position = Array(1035.0,1005.0,0.0)
  barbaresOrc3.setPosition(barbaresOrc3Position)
  val barbaresOrc4Position = Array(3000.0,1500.0,0.0)
  barbaresOrc4.setPosition(barbaresOrc4Position)


  // List of all creatures
  var allMobs = new ArrayList[Mob]()
  Collections.addAll(allMobs, solar, worgsRider1, worgsRider2, worgsRider3, worgsRider4, worgsRider5, worgsRider6, worgsRider7, worgsRider8, worgsRider9, warLord, barbaresOrc1, barbaresOrc2, barbaresOrc3, barbaresOrc4)
  Mob.everyone.addAll(allMobs)

  // Creer MobController
  var solarEnemies = new ArrayList[Mob]()
  Collections.addAll(solarEnemies, worgsRider1, worgsRider2, worgsRider3, worgsRider4, worgsRider5, worgsRider6, worgsRider7, worgsRider8, worgsRider9, warLord, barbaresOrc1, barbaresOrc2, barbaresOrc3, barbaresOrc4)
  solar.setEnemies(solarEnemies)
  var worgsRider1Enemies = new ArrayList[Mob]()
  worgsRider1Enemies.add(solar)
  worgsRider1.setEnemies(worgsRider1Enemies)
  var worgsRider2Enemies = new ArrayList[Mob]()
  worgsRider2Enemies.add(solar)
  worgsRider2.setEnemies(worgsRider2Enemies)
  var worgsRider3Enemies = new ArrayList[Mob]()
  worgsRider3Enemies.add(solar)
  worgsRider3.setEnemies(worgsRider3Enemies)
  var worgsRider4Enemies = new ArrayList[Mob]()
  worgsRider4Enemies.add(solar)
  worgsRider4.setEnemies(worgsRider4Enemies)
  var worgsRider5Enemies = new ArrayList[Mob]()
  worgsRider5Enemies.add(solar)
  worgsRider5.setEnemies(worgsRider5Enemies)
  var worgsRider6Enemies = new ArrayList[Mob]()
  worgsRider6Enemies.add(solar)
  worgsRider6.setEnemies(worgsRider6Enemies)
  var worgsRider7Enemies = new ArrayList[Mob]()
  worgsRider7Enemies.add(solar)
  worgsRider7.setEnemies(worgsRider7Enemies)
  var worgsRider8Enemies = new ArrayList[Mob]()
  worgsRider8Enemies.add(solar)
  worgsRider8.setEnemies(worgsRider8Enemies)
  var worgsRider9Enemies = new ArrayList[Mob]()
  worgsRider9Enemies.add(solar)
  worgsRider9.setEnemies(worgsRider9Enemies)
  var warLordEnemies = new ArrayList[Mob]()
  warLordEnemies.add(solar)
  warLord.setEnemies(warLordEnemies)
  var barbaresOrc1Enemies = new ArrayList[Mob]()
  barbaresOrc1Enemies.add(solar)
  barbaresOrc1.setEnemies(barbaresOrc1Enemies)
  var barbaresOrc2Enemies = new ArrayList[Mob]()
  barbaresOrc2Enemies.add(solar)
  barbaresOrc2.setEnemies(barbaresOrc2Enemies)
  var barbaresOrc3Enemies = new ArrayList[Mob]()
  barbaresOrc3Enemies.add(solar)
  barbaresOrc3.setEnemies(barbaresOrc3Enemies)
  var barbaresOrc4Enemies = new ArrayList[Mob]()
  barbaresOrc4Enemies.add(solar)
  barbaresOrc4.setEnemies(barbaresOrc4Enemies)

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
    (10L, new Node(id = 10, mob = warLord)),
    (11L, new Node(id = 11, mob = barbaresOrc1)),
    (12L, new Node(id = 12, mob = barbaresOrc2)),
    (13L, new Node(id = 13, mob = barbaresOrc3)),
    (14L, new Node(id = 14, mob = barbaresOrc4))
  ))

  // Generate random initial positions for the mobs
  // TODO

  // Creating edges (links between mobs)
  var myEdges = sc.makeRDD(Array(
    Edge(1L, 2L, "1"), Edge(1L, 3L, "2"), Edge(1L, 4L, "3"),
    Edge(1L, 5L, "4"), Edge(1L, 6L, "5"),
    Edge(1L, 7L, "6"), Edge(1L, 8L, "7"),
    Edge(1L, 9L, "8"), Edge(1L, 10L, "9"),
    Edge(1L, 11L, "10"), Edge(1L, 12L, "11"),
    Edge(1L, 13L, "12"), Edge(1L, 14L, "13")
  ))

  var myGraph = Graph(myVertices, myEdges)
  val algoColoring = new FightGraph(myGraph)
  //algoColoring.setMobs(allMobs)
  val res = algoColoring.execute(myGraph, 2, sc) // 20
  //println("\nNombre de couleurs trouvées: " + algoColoring.getChromaticNumber(res))
}
