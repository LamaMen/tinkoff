class FinancialAssistant(
    var name: String = "",
    var incomes: MutableList<Int> = mutableListOf()
) {

    fun addIncomeAndCalculate(income: Int): Int {
        if (income < 0) throw IllegalArgumentException()
        incomes.add(income)
        return incomes.sum()
    }
}

fun financialAssistant(block: FinancialAssistant.() -> Unit): FinancialAssistant = FinancialAssistant().apply(block)