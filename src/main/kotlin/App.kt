import services.DatabaseConnectionService
import services.GettingDataFromDatabaseService
import services.TableInitializationService

fun main() {
    DatabaseConnectionService.connectWithDatabase("jdbc:postgresql://localhost:5432/postgres", "ilia", "gjkbnt[")

    TableInitializationService.createTables()
    TableInitializationService.insertDataInTables()

    println("Рабочий под номером 2: ${GettingDataFromDatabaseService.getEmployeeById(2)}")
    println("Отдел под номером 3: ${GettingDataFromDatabaseService.getDepartmentById(3)}")
    println("Проект под номером 5: ${GettingDataFromDatabaseService.getProjectById(5)}")
    println()


    println("Проекты у которых номер больше 2:")
    GettingDataFromDatabaseService.getProjectsAfterSecond().forEach {
        println("    $it")
    }
    println()

    println("Рабочие вместе с отделом:")
    GettingDataFromDatabaseService.getEmployeesWithDepartment().forEach {
        println("    $it")
    }
    println()

    println("Рабочие по проектам:")
    GettingDataFromDatabaseService.getEmployeesWithProjects().forEach {
        println("    $it")
    }
    println()

    println("Рабочие сгруппированные по отделам:")
    GettingDataFromDatabaseService.groupEmployeesByDepartment().forEach { pair ->
        println("    Department ${pair.key}: ")
        pair.value.forEach { employee -> println("        $employee") }
    }
    println()

    println("Отделы отсортированнеы по номеру телефона, по убыванию:")
    for (department in GettingDataFromDatabaseService.sortDepartmentByPhone()) {
        println("    $department")
    }
    println()

    TableInitializationService.deleteTables()

    DatabaseConnectionService.closeConnection()
}