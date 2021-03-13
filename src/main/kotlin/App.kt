import services.DatabaseConnectionService
import services.GettingDataFromDatabaseService
import services.TableInitializationService

/**
Задание:
    1) Выбрать предметную область (можно такую же как и в 4 задание, по коллекция )

    2) Создать  три класса данных, не менее 3х полей у каждого. Одно из полей должно
    каким-то образов связывать эти классы.

    3) Реализовать в этих классах связку один ко многим и многие-ко-многим
    (для этого понадобится еще 1 класс( таблица в БД) )

    4) На основе DAO классов созданных выше, написать sql скрипты для создание таблиц в БД

    5) Реализовать в приложение следующие сервисы:

        a. Класс - клиент, который будет отвечать за подключение к БД.
          Всю информацию по подключению можно хранить как внутри класса,
          так и передавать в его конструктор например. Внутри класса
          можно реализовать методы подключения к БД, обработки каких либо sql запросов.

        b. Класс инициализации (создания исходных таблиц) и удаление этих таблиц.
          На основе скриптов п.4 можно создать класс, который будет содержать скрипты
          по созданию и удалению таблиц (реализацию данного класса можно изменить по своему усмотрению)

        c. Класс сервис, который должен содержать методы получения данных из БД по какой-либо логике.
          Он внутри себя должен обращаться к классу Клиенту 5a, передавать запрос в него и потом
          обрабатывать ответ самостоятельно. Необходимо реализовать логику, которая будет описана в п.6

    6) Логика выборки данных из таблиц:

        a. Найти 1 значению в таблице по id (идентификатору)

        b. Найти все элементы в таблице которые имеют идентификатор > 2
        (или какое нить другое поле, и условие на ваш выбор)

        c. Сделать выборку из связанных таблиц (операторы JOIN, LEFT JOIN)
        (Возможно при маппинге из ResultSet понадобится новая сущность (необходимо будет создать))

        d. Сделать выборку с группировкой по какому нибудь полю

        e. Сделать выборку и отсортировать в порядке убывания значений.

    Все операции надо реализовать через SQL, а не обрабатывать результат уже после получения.
    Результат необходимо вывести в консоль приложения.

    Если по какой то причине при работе с БД произошла ошибка (неправильный синтаксис SQL выражения),
    ее необходимо обработать и вывести соотетствующее сообщение, при это программа должна
    продолжить работу а не упасть. (Для обработки исключения, можно создать свое собственное исключение.)

    Перед завершением работы приложения хорошо бы удалить таблицы из базы.

 */


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