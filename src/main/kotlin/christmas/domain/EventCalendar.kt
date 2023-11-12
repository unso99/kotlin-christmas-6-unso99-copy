package christmas.domain

import java.time.DayOfWeek
import java.time.LocalDate

enum class DiscountType(val event: String, val discountPrice: Int) {
    CHRISTMAS("크리스마스 디데이 할인: ", 1_000), WEEKDAY("평일 할인: ", 2_023), WEEKEND("주말 할인: ", 2_023), SPECIAL(
        "특별 할인: ",
        1_000
    ),
    GIFT("증정 이벤트: ", 25_000)
}

data class Discount(val discountPrice: Int, val type: DiscountType)

class EventCalendar(private val date: Int, private val hasGift: Boolean) {

    private val christmasDiscount = Discount(DiscountType.CHRISTMAS.discountPrice, DiscountType.CHRISTMAS)
    private val weekdayDiscount = Discount(DiscountType.WEEKDAY.discountPrice, DiscountType.WEEKDAY)
    private val weekendDiscount = Discount(DiscountType.WEEKEND.discountPrice, DiscountType.WEEKEND)
    private val specialDiscount = Discount(DiscountType.SPECIAL.discountPrice, DiscountType.SPECIAL)
    private val specialDiscountDays = SPECIAL_DISCOUNT_DAYS
    private val giftDiscount = Discount(DiscountType.GIFT.discountPrice, DiscountType.GIFT)

    private val currentDate = LocalDate.of(CURRENT_YEAR, CURRENT_MONTH, date)


    fun getDiscountInfo(): List<Discount> {
        val discountList = mutableListOf<Discount>()

        if (date <= CHRISTMAS_DAY) {
            val plusDiscount = (date - 1) * CHRISTMAS_DISCOUNT_INCREMENT
            discountList.add(christmasDiscount.copy(discountPrice = DiscountType.CHRISTMAS.discountPrice + plusDiscount))
        }

        if (currentDate.dayOfWeek <= DayOfWeek.THURSDAY || currentDate.dayOfWeek == DayOfWeek.SUNDAY) {
            discountList.add(weekdayDiscount)
        }

        if (currentDate.dayOfWeek == DayOfWeek.FRIDAY || currentDate.dayOfWeek == DayOfWeek.SATURDAY) {
            discountList.add(weekendDiscount)
        }

        if (specialDiscountDays.contains(date)) {
            discountList.add(specialDiscount)
        }

        if (hasGift) {
            discountList.add(giftDiscount)
        }

        return discountList
    }

    companion object {
        val SPECIAL_DISCOUNT_DAYS = setOf(3, 10, 17, 24, 25, 31)
        const val CURRENT_YEAR = 2_023
        const val CURRENT_MONTH = 12
        const val CHRISTMAS_DAY = 25
        const val CHRISTMAS_DISCOUNT_INCREMENT = 100
    }
}