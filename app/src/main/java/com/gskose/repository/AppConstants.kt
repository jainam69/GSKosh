package com.gskose.repository

object AppConstants {
    val BASE_URL = String.format(
        "%s",
        "https://vlogapp.co/"
    ) //UAT
    /*val BASE_URL = String.format(
        "%s",
        "https://stage.vlogapp.co/"
    ) //UAT*/
    val BASE_URL_D = String.format(
        "%s",
        "http://108.161.131.151/tapovanapi/api/Dictionary/"
    ) //UAT

    const val INVALID_CODE = 6
    const val SUCCESS_CODE = 1
    var SKIP_CODE = 7
    var ACTIVITY_CLOSE_CODE = 8
    var FAILED_CODE = 0

    const val CONNECT_TO_WIFI = "WIFI"
    const val NOT_CONNECT = "NOT_CONNECT"

    const val SearchSuggestions = "SearchSuggessions"
    const val SearchResultNew = "SearchResultNew"
    const val Login = "Login"
    const val NewPasswordChange = "NewPasswordChange"
    const val OTPVerification = "OTPVerification"
    const val GetCommonAPI = "GetCommonAPI"
    const val GetSDReceiptList = "GetSDReceiptList"
    const val GetSDBalanceList = "GetSDBalanceList"
    const val GetLedgerReport = "GetLedgerReport"
    const val GetInvoiceReceiptList = "GetInvoiceReceiptList"
    const val GetAllotedCustomerList = "GetAllotedCustomerList"
    const val GetAllotedCustomerListSearch = "GetAllotedCustomerListSearch"
    const val ForgotPasswordEmailRequest = "ForgotPasswordEmailRequest"
    const val ChangePassword = "ChangePassword"
    const val AddOutstandingCollection = "AddOutstandingCollection"
    const val GenerateLinkThroughSendingEmailSMS = "GenerateLinkThroughSendingEmailSMS"
    const val GetAllAreaListData = "GetAllAreaListData"
    const val GetOutstandingCollectedHistory = "GetOutstandingCollectedHistory"
    const val GetOutstandingCollectedHistorySearch = "GetOutstandingCollectedHistorySearch"
    const val KYCAndCustomerMasterDetailsUpdation = "KYCAndCustomerMasterDetailsUpdation"
}