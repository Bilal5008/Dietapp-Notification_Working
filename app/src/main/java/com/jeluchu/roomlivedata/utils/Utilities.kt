package com.jeluchu.roomlivedata.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings

import android.text.TextUtils
import android.text.format.DateUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.jeluchu.roomlivedata.utils.Constants.Companion.kPARENT_ID
import com.jeluchu.roomlivedata.utils.Constants.Companion.kUSER_ID

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class Utilities : Application() {

    internal fun convertMilesToMeters(miles: Float): Float {
        return miles / 0.00062137f
    }

    companion object {


        fun goToActivity(context: Context, ActivityToOpen: Class<out Activity>) {
            val i = Intent(context, ActivityToOpen)
            context.startActivity(i)
        }

        fun startActivityAndClearStack(context: Context, ActivityToOpen: Class<out Activity>) {
            val intent = Intent(context, ActivityToOpen)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)

        }

        fun convertPixelsToDp(px: Float): Float {
            val metrics = Resources.getSystem().displayMetrics
            val dp = px / (metrics.densityDpi / 160f)
            return Math.round(dp).toFloat()
        }

        fun convertDpToPixel(dp: Float): Int {
            val metrics = Resources.getSystem().displayMetrics
            val px = dp * (metrics.densityDpi / 160f)
            return Math.round(px)
        }


        fun isJSONValid(test: String): Boolean {
            try {
                JSONObject(test)
            } catch (ex: JSONException) {
                // edited, to include @Arthur's comment
                // e.g. in case JSONArray is valid as well...
                try {
                    JSONArray(test)
                } catch (ex1: JSONException) {
                    return false
                }

            }

            return true
        }

        //    public static void uploadImage(final String filePath, final String quoteId, final OnUploadImageListener listener) {
        //        final OkHttpClient client = new OkHttpClient.Builder()
        //                .connectTimeout(20, TimeUnit.SECONDS)
        //                .writeTimeout(20, TimeUnit.SECONDS)
        //                .readTimeout(30, TimeUnit.SECONDS)
        //                .build();
        //        if (filePath != null && !filePath.isEmpty()) {
        //            new AsyncTask<Void, Void, Void>() {
        //                @Override
        //                protected Void doInBackground(Void... params) {
        //                    try {
        //                        File file = new File(filePath); //provide a valid file
        //                        String response = ApiCall.POST(client, Constants.kAPI_BASEURL + "quote/image/save", RequestBuilder.uploadRequestBody(file, quoteId));
        //                        //Gson gson = new GsonBuilder().create();
        //                        //JsonObject result = gson.fromJson(response, JsonObject.class);
        //                        //Log.d("IMAGE-UPLOAD-RESP", response);
        //                        //if (!result.get("status").getAsString().equals("0")) {
        //                        //Log.d("UPLOAD-IMAGE-REQ", "Upload successful: " + response);
        //                        //String imageUrl = result.get("uploadedImagePath").getAsString();
        //                        listener.onImageUploaded(filePath);
        //                        /*} else {
        //                            String errorMsg = result.get("message").getAsString();
        //                            listener.onImageFailed(errorMsg);
        //                            Log.d("UPLOAD-IMAGE-REQ", "Upload Failed: " + errorMsg);*/
        //                        //}
        //                    } catch (IOException e) {
        //                        e.printStackTrace();
        //                        listener.onImageFailed(e.getMessage());
        //                        Log.d("UPLOAD-IMAGE-REQ", "Upload Failed: " + e.getMessage());
        //                    }
        //                    return null;
        //                }
        //            }.execute();
        //        }
        //    }

        fun isEmailValid(email: String): Boolean {
            var isValid = false

            val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }


        fun formatExchangeKey(sourceCurrency: String, destinationCurrency: String): String {
            return "$sourceCurrency-$destinationCurrency"
        }

        fun getFormattedDouble(aDouble: Double?): String {
            return String.format("%.0f", aDouble)
        }

        fun hasPermissions(context: Context?, vararg allPermissionNeeded: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && context != null && allPermissionNeeded != null)
                for (permission in allPermissionNeeded)
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                        return false
            return true
        }


        fun isValidPassword(pass: String): Boolean {
            /*
        ^                 # start-of-string
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
(?=\\S+$)          # no whitespace allowed in the entire string
.{4,}             # anything, at least six places though
$

        */
            var isValid = false

            val PASSWORD_PATTERN = "^(?=.*[a-zA-Z0-9.@#$%^&+=])(?=\\S+$).{4,}$"

            val pattern = Pattern.compile(PASSWORD_PATTERN)
            val matcher = pattern.matcher(pass)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }


        fun isNameValid(name: String): Boolean {
            var name = name
            var isValid = false

            name = name.replace("\\s+".toRegex(), "")

            //^(?=[a-zA-Z0-9~@#$^()_+=[]{}|\,.?: -]$)(?!.*[<>'"/;`%])
            val expression = "^[a-z0-9. -]{3,50}$"

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(name)
            if (matcher.matches()) {
                isValid = true
            }

            return isValid
        }

        fun isNumberValid(number: String): Boolean {
            var isValid = false

            val expression = "\\d+(\\.\\d+)?"

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(number)
            if (matcher.matches()) {
                isValid = true
            }

            return isValid
        }

        fun isUsernameValid(username: String): Boolean {
            var isValid = false

            val expression = "^[a-z0-9.-@]{2,50}$"

            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(username)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        fun hideKeyboard(view: View, context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showKeyboard(view: View, context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }

        fun getResizedBitmap(targetW: Int, imagePath: String): Bitmap {

            // Get the dimensions of the bitmap
            val bmOptions = BitmapFactory.Options()
            //inJustDecodeBounds = true <-- will not load the bitmap into memory
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, bmOptions)
            val photoW = bmOptions.outWidth
            //int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            val scaleFactor = photoW / targetW

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor

            return BitmapFactory.decodeFile(imagePath, bmOptions)
        }

        /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)*/
        @SuppressLint("NewApi")
        fun getPath(context: Context, uri: Uri): String? {
            val isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))

                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }// MediaProvider
                // DownloadsProvider
            } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
                return getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
                return uri.path
            }// File
            // MediaStore (and general)

            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        private fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                                  selectionArgs: Array<String>?): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(column_index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        private fun convertMetersToMiles(meters: Double): Double {
            return meters * 0.00062137f
        }

        @SuppressLint("DefaultLocale")
        fun formatMeters(meters: Double, shouldDisplayUnitFullName: Boolean): String {
            var distance = ""


            if (meters < 500) {

                if (shouldDisplayUnitFullName) {
                    distance = String.format("%.0f Meters", Math.ceil(meters))
                } else {
                    distance = String.format("%dm", Math.ceil(meters).toInt())
                }

            } else {
                val miles = convertMetersToMiles(meters)

                if (miles <= 1) {
                    if (shouldDisplayUnitFullName) {
                        distance = String.format("%.1f Mile", miles)
                    } else {
                        distance = String.format("%.1fMi", miles)
                    }

                } else {
                    if (shouldDisplayUnitFullName) {
                        distance = String.format("%.1f Miles", miles)
                    } else {
                        distance = String.format("%.1fMi", miles)
                    }
                }
            }

            return distance
        }

        @Throws(ParseException::class)
        fun getDayNameFromDate(date: String): String {

            /*String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
        Date newDate = format.parse(strCurrentDate);

        format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
        String date = format.format(newDate);*/


            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            val format = SimpleDateFormat("yyyy-MM-dd")
            val dt1 = format.parse(date)
            val format2 = SimpleDateFormat("EEEE", Locale.US)
            return format2.format(dt1)
        }

        @Throws(ParseException::class)
        fun getStringFromDate(date: Date, pattern: String): String {
            val format = SimpleDateFormat(pattern)
            return format.format(date)
        }

        @Throws(ParseException::class)
        fun getStringFromDate(date: Date, pattern: String, gmt: String): String {

            val format = SimpleDateFormat(pattern)
            format.timeZone = TimeZone.getTimeZone(gmt)
            return format.format(date)
        }

        val lastDayOfMonth: Date
            get() {
                val calendar = Calendar.getInstance()

                val lastDate = calendar.getActualMaximum(Calendar.DATE)


                calendar.set(Calendar.DATE, lastDate)

                return calendar.time
            }

        val firstDayOfMonth: Date
            get() {
                val calendar = Calendar.getInstance()
                val FirstDate = calendar.getActualMinimum(Calendar.DATE)

                calendar.set(Calendar.DATE, FirstDate)

                return calendar.time
            }

        @Throws(ParseException::class)
        fun getDate(date: String, pattern: String): Date {

            val format = SimpleDateFormat(pattern)

            return format.parse(date)
        }

        @Throws(ParseException::class)
        fun getDateUTC(date: String, pattern: String): Date {

            val format = SimpleDateFormat(pattern)
            format.timeZone = TimeZone.getTimeZone("UTC")

            return format.parse(date)
        }

        fun getGmtFromString(dateTime: String): String {
            var gmt = ""
            if (dateTime.contains("+")) {
                val index = dateTime.lastIndexOf('+')

                gmt = dateTime.substring(index)
            } else if (dateTime.contains("-")) {
                val index = dateTime.lastIndexOf('-')

                gmt = dateTime.substring(index)
            }
            return gmt
        }


        fun removeLastChar(s: String?): String? {
            return if (s == null || s.length == 0) {
                s
            } else s.substring(0, s.length - 1)
        }

        fun validateRequiredField(value: String?): Boolean {
            return if (value == null || value == "" || value.length == 0) {
                false
            } else true
        }

        fun setPrefValue(key: String, value: String?, context: Context) {
            val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(key, value).commit()
        }

        fun getPrefValue(key: String, context: Context): String? {
            val pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString(key, null)
        }

        val currentDate: Date
            @Throws(ParseException::class)
            get() {
                val c = Calendar.getInstance()
                println("Current time => " + c.time)

                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formattedDate = df.format(c.time)
                return df.parse(formattedDate)
            }

        @Throws(ParseException::class)
        fun getIncrementeDateBy(minutes: Int): String {
            val now = Calendar.getInstance()
            now.add(Calendar.MINUTE, minutes / 60)
            val teenMinutesFromNow = now.time

            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            return df.format(teenMinutesFromNow)
        }

        val timeStamp: Long
            get() = Date().time

        fun showToast(context: Context, text: String, view: View) {
            Toast.makeText(context, text,
                    Toast.LENGTH_LONG).show()
        }


        fun hasLollipop(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        fun isLocationEnabled(context: Context): Boolean {
            var locationMode = 0
            val locationProviders: String

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)

                } catch (e: Settings.SettingNotFoundException) {
                    e.printStackTrace()
                }

                return locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                locationProviders = Settings.Secure.getString(context.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
                return !TextUtils.isEmpty(locationProviders)
            }
        }

        fun isUserLoggedIn(context: Context): Boolean {
            var isloggedIn = true

            if (getUserID(context) == null && getParentID(context) == null) {
                isloggedIn = false
            }
            return isloggedIn
        }

        val currencyTypeID: String
            get() = "1"

        fun getRequestTypeID(type: String): String {
            return "1"
        }

        fun getPackageTypeID(type: String): String {
            return if (type.equals("box", ignoreCase = true)) {
                "1"
            } else "0"
        }

        fun getUserID(context: Context): String? {
            return getPrefValue(kUSER_ID, context)
        }

        fun getParentID(context: Context): String? {
            return getPrefValue(kPARENT_ID, context)
        }


        //    public static boolean isTokenExpired(Context context) {
        //
        //        if (Utilities.getPrefValue(kTOKEN_EXPIRY, context) == null) {
        //            return true;
        //        }
        //
        //        try {
        //            Date expiryDate = Utilities.getDate(Utilities.getPrefValue(kTOKEN_EXPIRY, context), kDATE_TOKEN_EXPIRY);
        //
        //            Date currentTime = Calendar.getInstance().getTime();
        //
        //            int compare = currentTime.compareTo(expiryDate);
        //
        //            if (compare > 0 || compare == 0) { // expired
        //                return true;
        //            } else {
        //                return false;
        //            }
        //
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //
        //        return true;
        //    }

        fun parseLatLong(latLong: String?): Array<String>? {
            if (latLong != null) {
                if (latLong.contains(",")) {
                    return latLong.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
            }
            return null
        }


        fun getDeviceUniqueId(context: Context): String {
            var android_id: String? = Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ANDROID_ID)
            if (android_id == null) {
                android_id = "MaybeEmulator"
            }
            return android_id
        }


        fun showMinutesInGmt(offset: String): Boolean {
            val separated = offset.split("\\:".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (separated.size > 1) {
                if (separated[1] != null && separated[1].length > 0) {
                    val minutes = java.lang.Double.parseDouble(separated[1])
                    if (minutes > 0) { // show minutes
                        return true
                    }
                }
            }
            return false
        }

        fun getOffsetHoursOnly(offset: String): String {
            val separated = offset.split("\\:".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (separated.size > 0) {
                if (separated[0] != null && separated[0].length > 0) {
                    return separated[0]
                }
            }
            return ""
        }

        fun getFormattedGmt(offset: String): String {
            //        if (!offset.contains("-")) {
            //            return "(GMT+" + offset + ")";
            //        }
            return "(GMT$offset)"
        }


        fun getHumanReadableTime(date: Long): CharSequence {
            val now = System.currentTimeMillis()
            return DateUtils.getRelativeTimeSpanString(date,
                    now, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE)
        }

        fun getLocaleCountryCode(context: Context): String {
            //        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            //        if (tm != null) {
            //            return tm.getNetworkCountryIso();
            //        }

            return context.resources.configuration.locale.country
        }

        @Throws(JSONException::class)
        fun jsonToMap(json: JSONObject): Map<String, Any> {
            var retMap: Map<String, Any> = HashMap()

            if (json !== JSONObject.NULL) {
                retMap = toMap(json)
            }
            return retMap
        }

        @Throws(JSONException::class)
        fun toMap(`object`: JSONObject): Map<String, Any> {
            val map = HashMap<String, Any>()

            val keysItr = `object`.keys()
            while (keysItr.hasNext()) {
                val key = keysItr.next()
                var value = `object`.get(key)

                if (value is JSONArray) {
                    value = toList(value)
                } else if (value is JSONObject) {
                    value = toMap(value)
                }
                map[key] = value
            }
            return map
        }

        @Throws(JSONException::class)
        fun toList(array: JSONArray): List<Any> {
            val list = ArrayList<Any>()
            for (i in 0 until array.length()) {
                var value = array.get(i)
                if (value is JSONArray) {
                    value = toList(value)
                } else if (value is JSONObject) {
                    value = toMap(value)
                }
                list.add(value)
            }
            return list
        }

        fun prepareMediaFileName(fileExtension: String): String {
            return timeStamp.toString() + "_gearsunlimited." + fileExtension
        }

        //    public static String dateConverter(String title) {
        //
        //        Date d = null;
        //        SimpleDateFormat sdf = new SimpleDateFormat(kDATE_FORMAT_YEAR_MONTH_TTIMEZ);
        //        try {
        //            d = sdf.parse(title);
        //        } catch (ParseException ex) {
        //            Log.v("Exception", ex.getLocalizedMessage());
        //        }
        //
        //        String StringDate = null;
        //        try {
        //
        //            StringDate = Utilities.getStringFromDate(d, kDATE_FORMAT_DATE_MONTH_YEAR);
        //
        //        } catch (ParseException e) {
        //            e.printStackTrace();
        //        }
        //
        //        return StringDate;
        //    }



        fun sendEmail(context: Context) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:feedback@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }
    }

}