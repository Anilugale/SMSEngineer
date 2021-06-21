package com.moreinfo.sales.util

object UrlUtility {
    private const val baseUrl = "http://simplemobileservice.com/customer/webservice"
    const val login = "$baseUrl/sales/sales_person_logging.php"
    const val cityList = "$baseUrl/cities_webservice.php"
    const val statusList = "$baseUrl/state_webservice.php"
    const val getUserDetails = "$baseUrl/sales/check_cust.php"
    const val registrationUrl = "$baseUrl//registration.php"

}