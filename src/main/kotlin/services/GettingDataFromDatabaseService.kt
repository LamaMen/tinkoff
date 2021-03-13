package services

import dao.Employee

object GettingDataFromDatabaseService {

    fun getEmployeeByPersonnelNumber(personnelNumber: Int): Employee? {
        val sql = "SELECT personnel_number, name, department FROM employee WHERE personnel_number = ?"
        val resultSet = DatabaseConnectionService.getDataById(sql, personnelNumber)

        var employee: Employee? = null

        while(resultSet.next()){
            val number = resultSet.getInt("personnel_number")
            val name = resultSet.getString("name")
            val department = resultSet.getInt("department")
            employee = Employee(number, name, department)
        }

        resultSet.close()

        return employee
    }

}