val translationHistoryDay = 0 //история переводов за день
val translationHistoryMonth = 0 //история переводов за месяц
val transferAmount = 10_000 //сумма перевода
val card = "VK" //карты - VK, VISA, MAESTRO, MASTERCARD, MIR
val minComissionVISAMIR = 35 //минимальная комиссия для VISA и MIR
val commissionVISAMIR = 0.075 //комиссия VISA и MIR
val commissionMAESTROMASTERCARD = 0.06 //комиссия MAESTRO и MASTERCARD
val addCommissionMAESTROMASTERCARD = 20 //дополнительная комиссия по картам MAESTRO и MASTERCARD

fun main() {

println(calcCommission(limits()))

}

fun calcCommission(limits: Boolean): String {

    val comissionVK = 0 //комиссия по карте VK
    val transferAmountCommissionVK = transferAmount - comissionVK //вычет комиссии по карте VK
    val actionComissionMAESTROMASTERCARD = 0 //комиссия по карте MAESTRO и MASTERCARD по акции
    val actionTransferAmountCommissionMAESTROMASTERCARD = transferAmount - actionComissionMAESTROMASTERCARD //вычет комиссии по карте MAESTRO и MASTERCARD по акции
    val transferAmountCommissionMAESTROMASTERCARD = transferAmount * commissionMAESTROMASTERCARD + addCommissionMAESTROMASTERCARD //вычет комиссии по картам MAESTRO и MASTERCARD без акции
    val totalComissionMAESTROMASTERCARD = transferAmount - transferAmountCommissionMAESTROMASTERCARD
    val minCommissionVISAMIR = transferAmount - minComissionVISAMIR //минимальная комиссия по картам VISA и MIR
    val totalCommissionVISAMIR = transferAmount * commissionVISAMIR //стандартная комиссия по картам VISA и MIR


        return when {
    limits == true && card == "VK" ->
        "Сумма перевода: $transferAmount \nПереведено: $transferAmountCommissionVK руб \nКомиссия за перевод: $comissionVK руб" //перевод по карте VK подпадающий под лимит
    limits == true && card == "MAESTRO" || card == "MASTERCARD" ->
        "Сумма перевода: $transferAmount \nПереведено: $actionTransferAmountCommissionMAESTROMASTERCARD руб \nКомиссия за перевод: $actionComissionMAESTROMASTERCARD руб" //акция на переводы по картам MAESTRO и MASTERCARD
    limits == false && card == "MAESTRO" || card == "MASTERCARD" ->
        "Сумма перевода: $transferAmount \nПереведено: $transferAmountCommissionMAESTROMASTERCARD руб \nКомиссия за перевод: $totalComissionMAESTROMASTERCARD руб" //переводы по картам MAESTRO и MASTERCARD не подпадающие под акции
    limits == true && card == "VISA" || card == "MIR" && transferAmount * commissionVISAMIR < minComissionVISAMIR ->
        "Переведено $minCommissionVISAMIR руб" //переводы по картам VISA и MIR с минимальной комиссией
    limits == true && card == "VISA" || card == "MIR" && transferAmount * commissionVISAMIR >= minComissionVISAMIR ->
        "Переведено $totalCommissionVISAMIR руб" //переводы по картам VISA и MIR со стандартной комиссией
    limits == false ->
        "Отмена перевода. Превышены лимиты." //переводы не подпадающие под лимиты
    else ->
        "Ошибка перевода" //прочие возможные ошибки
    }
}

fun limits(): Boolean {

    val maxTranslationHistoryMonth = 40_000 //максимальная сумма перевода за месяц по карте VK
    val maxTranslationVK = 15_000 //максимальная сумма перевода по карте VK
    val actionMinTranslationHistoryMonth = 300 //минимальная сумма перевода за месяц по акции по картам MAESTRO и MASTERCARD
    val actionMaxTranslationHistoryMonth = 75_000 //максимальная сумма перевода за месяц по акции по картам MAESTRO и MASTERCARD
    //val translationDayMAESTROMASTERCARD = 150_000 //максимальная сумма перевода за день по картам MAESTRO и MASTERCARD
    //val translationMonthMAESTROMASTERCARD = 600_000 //максимальная сумма перевода за месяц по картам MAESTRO и MASTERCARD

    return when {
        card == "VK" && translationHistoryMonth <= maxTranslationHistoryMonth && transferAmount <= maxTranslationVK -> true
        card == "MAESTRO" || card == "MASTERCARD" && translationHistoryMonth >= actionMinTranslationHistoryMonth && translationHistoryMonth <= actionMaxTranslationHistoryMonth -> true
     //   card == "VISA" || card == "MIR" && translationHistoryDay <= translationDayMAESTROMASTERCARD && translationHistoryMonth <= translationMonthMAESTROMASTERCARD && transferAmount <= translationDayMAESTROMASTERCARD -> true
        else -> false
    }
}