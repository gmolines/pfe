package fr.polytech.unice.pfe

import java.io.{File, PrintWriter, FileInputStream}
import java.text.SimpleDateFormat
import scala.collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}



object Main extends App {

  final val START = 1
  final val STOP =  61

  final val OUTPUT_DIR = "./outputs"

  val inp = new FileInputStream("input.xlsx")
  val wb = WorkbookFactory.create(inp)
  val sheet = wb.getSheetAt(0)


  val outputs = new File(OUTPUT_DIR)
  if (! outputs.exists){ outputs.mkdir() }


  val dataset = sheet.rowIterator().toSeq.slice(START,STOP)

  val projects = dataset foreach { row =>
    val project = ProjectFactory(row)
    if (project != null) {
    	project.toMarkdown
	  }
  }

}


object ProjectFactory {


  def apply(row: Row): Project = {
    val pid = f"Y1819-S${row.getRowNum}%03d"
    println(s"## Handling project [$pid]")

    if (row.getCell(17).getStringCellValue == "yes") {
    // Common
    val rawDate = row.getCell(0).getDateCellValue
    val date = new SimpleDateFormat("yyyy-MM-dd").format(rawDate)
    val hour = new SimpleDateFormat("HH:mm:ss").format(rawDate)
    val firstName = row.getCell(2).getStringCellValue.capitalize
    val lastName = row.getCell(1).getStringCellValue.capitalize
    val email = row.getCell(3).getStringCellValue
    val title = row.getCell(18).getStringCellValue
    val majors = buildMajors(row.getCell(33).getStringCellValue)
    val description = row.getCell(19).getStringCellValue
    val descriptionDetaillee = row.getCell(23).getStringCellValue
    val skills = row.getCell(20).getStringCellValue
    val team = row.getCell(26).getStringCellValue
    val biblio = buildBiblio(row)
    val requirements = row.getCell(21).getStringCellValue
    val results = row.getCell(22).getStringCellValue
    val effectifMin = row.getCell(31).getStringCellValue
    val effectifMax = row.getCell(32).getStringCellValue

    val common = Common(pid, date, hour, firstName, lastName, email, title, majors, description, descriptionDetaillee, skills, team, biblio, requirements, results, effectifMin, effectifMax)

    // Research or engineering
    if (row.getCell(24).getStringCellValue == "Recherche") {
      Research(common)
    } else {
      Engineering(common)
    }
    }
    else{
    null
    }
    

  }


  def buildMajors(data: String): Set[String] = {
      println(s"## Found data [$data]")
    val majors = (data.split(",") filter { _.contains(":") } map { d => d.split(":")(0) }).toSet
    println(s"## Extracted majors [$majors]")
    majors
  }


  def buildBiblio(row: Row): Set[String] = {
    (Set[String]() /: Seq(27,28,29,30)) { (acc, n) =>
      try {
        val data = row.getCell(n).getStringCellValue
        acc + data
      } catch { case e: Exception => acc }
    }
  }

}


trait Project {

  val common: Common

  final def toMarkdown  = {
    val fileName = s"${common.date}-${common.pid}.markdown"
    val writer = new PrintWriter(new File(s"${Main.OUTPUT_DIR}/$fileName" ))
    writer.write(source)
    writer.close()
  }

  final def source: String = {
    val cartouche =
      s"""---
         |layout: post
         |title: \"${common.title}\"
         |date: ${common.date} ${common.hour}
         |categories: [dispo, ${(common.majors map {_.toLowerCase}).mkString(",")}]
         |pid: ${common.pid}
         |type: ${this.getClass.getSimpleName}
         |contact: ${common.firstName} ${common.lastName}
         |---
       """.stripMargin
       
    val body =
    s"""
       |${common.description}
       |
       |${common.descriptionDetaillee}
       |
       |#### Compétences Requises
       |${common.skills}
       |
       |${this.specific}
       |
       |#### Besoins Clients
       |${common.reqs}
       |
       |#### Résultats Attendus
       |${common.results}
       |
       |#### Références
       |
       |${(common.biblio map { ref => s"  * [$ref]($ref)" }).mkString("\n")}
       |
       |#### Informations Administratives
       |  * Contact : ${common.firstName} ${common.lastName} <${common.email}>
       |  * Identifiant sujet : `${common.pid}`
       |  * Effectif : entre ${common.effectifMin} et ${common.effectifMax} étudiant(e)s
       |  * Parcours Recommandés : ${(common.majors map {_.toUpperCase}).mkString(",")}
       |  * Équipe: ${common.team}
       |
     """.stripMargin

   cartouche + body
  }

  def specific: String

}

case class Common( pid: String, date: String, hour: String, firstName: String, lastName: String, email: String,
  title : String, majors: Set[String], description: String, descriptionDetaillee: String, skills: String,
  team: String, biblio: Set[String], reqs: String, results: String, effectifMin: String, effectifMax: String)

case class Research(common: Common) extends Project {
  override def specific: String = {
	s"""
       |
     """.stripMargin
  }

}

case class Engineering(common: Common) extends Project  {
  override def specific: String =
    s"""
       |
     """.stripMargin
}