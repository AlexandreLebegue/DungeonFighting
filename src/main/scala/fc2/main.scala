package fc2

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
    (1L, new Node(id = 1, position = solar.getPosition, mob = solar)),
    (2L, new Node(id = 1, position = worgsRider1.getPosition, mob = worgsRider1)),
    (3L, new Node(id = 1, position = worgsRider2.getPosition, mob = worgsRider2)),
    (4L, new Node(id = 1, position = worgsRider3.getPosition, mob = worgsRider3)),
    (5L, new Node(id = 1, position = worgsRider4.getPosition, mob = worgsRider4)),
    (6L, new Node(id = 1, position = worgsRider5.getPosition, mob = worgsRider5)),
    (7L, new Node(id = 1, position = worgsRider6.getPosition, mob = worgsRider6)),
    (8L, new Node(id = 1, position = worgsRider7.getPosition, mob = worgsRider7)),
    (9L, new Node(id = 1, position = worgsRider8.getPosition, mob = worgsRider8)),
    (10L, new Node(id = 1, position = warLord.getPosition, mob = warLord)),
    (11L, new Node(id = 1, position = barbaresOrc1.getPosition, mob = barbaresOrc1)),
    (12L, new Node(id = 1, position = barbaresOrc2.getPosition, mob = barbaresOrc2)),
    (13L, new Node(id = 1, position = barbaresOrc3.getPosition, mob = barbaresOrc3)),
    (14L, new Node(id = 1, position = barbaresOrc4.getPosition, mob = barbaresOrc4))
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
  val algoColoring = new FightGraph()
  val res = algoColoring.execute(myGraph, 20, sc)
  //println("\nNombre de couleurs trouvées: " + algoColoring.getChromaticNumber(res))
}
