package com.gskose.utils

import android.content.Context

open class PrefUtils {
    companion object {
        private const val LOGIN_IN_NEW = "islogins"
        private const val USERID = "userid"
        private const val GAID = "gaid"
        private const val TOKEN = "token"
        private const val KEY = "key"
        private const val NAME = "name"
        private const val CONSUMERNUMBER = "consumernumber"
        private const val CONSUMERNAME = "consumername"
        private const val CUSTOMERDATA = "customerdata"
        private const val PAYMENT_MODE = "paymentmode"
        private const val BANK_RESPONSE = "bankresponse"
        private const val AREA_RESPONSE = "arearesponse"
        private const val DRAACTIVITY_TYPE_LIST = "draactivitytypelist"
        private const val DRA_DOCUMENT_TYPE_LIST = "dradocumenttypelist"
        private const val METER_COMPANY_LIST = "metercompanylist"
        private const val METERTYPELIST = "metertypelist"
        private const val OMC_NAME_RESPONSE = "omcnameresponse"
        private const val RECEIPT_TYPE_LIST = "receipttypelist"
        private const val TYPEOF_HOUSE_LIST = "typeofhouselist"
        private const val TYPEOF_OWNERSHIP_LIST = "typeofownershiplist"
        private const val GA_DETAIL = "ga_detail"
        private const val AGENCYID = "AGENCYID"
        private const val MOBILENUMBER = "mobilenumber"

        fun setLoggedIn(ctx: Context?, value: Boolean) {
            Prefs.with(ctx!!).save(LOGIN_IN_NEW, value)
        }

        fun isUserLoggedIn(ctx: Context?): Boolean {
            return Prefs.with(ctx!!).getBoolean(LOGIN_IN_NEW, false)
        }

        fun setUserId(ctx: Context?, value: Long) {
            Prefs.with(ctx!!).save(USERID, value)
        }

        fun getUserId(ctx: Context?): Long {
            return Prefs.with(ctx!!).getLong(USERID, 0)
        }

        fun setToken(ctx: Context?, value: String) {
            Prefs.with(ctx!!).save(TOKEN, value)
        }

        fun getToken(ctx: Context?): String? {
            return Prefs.with(ctx!!).getString(TOKEN, "")
        }

        fun setKey(ctx: Context?, value: String) {
            Prefs.with(ctx!!).save(KEY, value)
        }

        fun getKey(ctx: Context?): String? {
            return Prefs.with(ctx!!).getString(KEY, "")
        }

        fun setGAId(ctx: Context?, value: Int) {
            Prefs.with(ctx!!).save(GAID, value)
        }

        fun getGAId(ctx: Context?): Int {
            return Prefs.with(ctx!!).getInt(GAID, 0)
        }

        fun setName(ctx: Context?, value: String) {
            Prefs.with(ctx!!).save(NAME, value)
        }

        fun getName(ctx: Context?): String? {
            return Prefs.with(ctx!!).getString(NAME, "")
        }

        fun setAgencyId(ctx: Context?, value: Long) {
            Prefs.with(ctx!!).save(AGENCYID, value)
        }

        fun getAgencyId(ctx: Context?): Long {
            return Prefs.with(ctx!!).getLong(AGENCYID, 0)
        }

        fun setMobileNumber(ctx: Context?, value: String) {
            Prefs.with(ctx!!).save(MOBILENUMBER, value)
        }

        fun getMobileNumber(ctx: Context?): String? {
            return Prefs.with(ctx!!).getString(MOBILENUMBER, "")
        }

        fun setConsumerNo(ctx: Context?, value: Long) {
            Prefs.with(ctx!!).save(CONSUMERNUMBER, value)
        }

        fun getConsumerNo(ctx: Context?): Long {
            return Prefs.with(ctx!!).getLong(CONSUMERNUMBER, 0)
        }

        fun setConsumerName(ctx: Context?, value: String) {
            Prefs.with(ctx!!).save(CONSUMERNAME, value)
        }

        fun getConsumerName(ctx: Context?): String? {
            return Prefs.with(ctx!!).getString(CONSUMERNAME, "")
        }

    }
}
