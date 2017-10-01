package fr.polytech.unice.pfe

import java.io.{File, PrintWriter, FileInputStream}
import java.text.SimpleDateFormat
import scala.collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}



object Main extends App {

  final val START = 2
  final val STOP =  53

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
    val pid = f"Y1718-S${row.getRowNum}%03d"
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
    val majors = buildMajors(row.getCell(31).getStringCellValue)
//    val majors = buildMajors(row.getCell(11).getStringCellValue,
//    	row.getCell(12).getStringCellValue,
//    	row.getCell(13).getStringCellValue,
//    	row.getCell(14).getStringCellValue,
//    	row.getCell(15).getStringCellValue,
//    	row.getCell(16).getStringCellValue)
    val description = row.getCell(19).getStringCellValue
    val descriptionDetaillee = row.getCell(23).getStringCellValue
    val skills = row.getCell(20).getStringCellValue

    val common = Common(pid, date, hour, firstName, lastName, email, title, majors, description, descriptionDetaillee, skills)

    // Research or engineering
    if (row.getCell(24).getStringCellValue == "Recherche") {
      val team = row.getCell(26).getStringCellValue
      val biblio = buildBiblio(row)
      Research(common, team, biblio)
    } else {
      val requirements = row.getCell(21).getStringCellValue
      val results = row.getCell(22).getStringCellValue
      Engineering(common, requirements, results)
    }
    }
    else{
    null
    }
    

  }


  def buildMajors(data: String): Set[String] = {
    (data.split(",") filter { _.contains(":") } map { d => d.split(":")(0) }).toSet
  }

//  def buildMajors(isAL: String, isCaspar: String, isSD: String, isIHM: String, isIAM: String, isWEB: String): Set[String] = {
//  	var res = Set.empty[String]
//	val res = scala.collection.mutable.Map[String, String]()
//  	if (isAL == "yes") {
//  		res put ("AL","")
//		res += "AL"
//  	}
//  	if (isCaspar == "yes") {
//  		res put ("CASPAR","")
//		res += "CASPAR"
//  	}
//  	if (isSD == "yes") {
//  		res put ("SD","")
//		res += "SD"
//  	}
//  	if (isIHM == "yes") {
//  		res put ("IHM","")
//		res += "IHM"
//  	}
//  	if (isIAM == "yes") {
//  		res put ("IAM","")
//		res += "IAM"
//  	}
//  	if (isWEB == "yes") {
//  		res put ("WEB","")
//		res += "WEB"
//  	}
//  	res
//  }

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
       |#### Informations Administratives
       |  * Contact : ${common.firstName} ${common.lastName} <${common.email}>
       |  * Identifiant sujet : `${common.pid}`
       |  * Type : ${this.getClass.getSimpleName}
       |  * Parcours Recommandés : ${(common.majors map {_.toUpperCase}).mkString(",")}
     """.stripMargin

   cartouche + body
  }

  def specific: String

}

case class Common( pid: String, date: String, hour: String, firstName: String, lastName: String, email: String,
  title : String, majors: Set[String], description: String, descriptionDetaillee: String, skills: String)

case class Research(common: Common, team: String, biblio: Set[String]) extends Project {
  override def specific: String = {
    val head = s"""
       |#### Références
       |
       |  * Équipe: $team
       |""".stripMargin
    head +  (biblio map { ref => s"  * [$ref]($ref)" }).mkString("\n")
  }

}

case class Engineering(common: Common, reqs: String, results: String) extends Project  {
  override def specific: String =
    s"""
       |#### Besoins Clients
       |$reqs
       |
       |#### Résultats Attendus
       |$results
     """.stripMargin
}