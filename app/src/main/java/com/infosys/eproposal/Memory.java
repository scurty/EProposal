package com.infosys.eproposal;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Map;

/**
 * Created by scurt on 10/11/2016.
 */
public class Memory {

    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "extSdCard";
    private static final String TAG = "Memory";
    static Map<String, File> externalLocations = ExternalStorage
            .getAllStorageLocations();
    static File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
    static File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);

    public static String GetDirPrinc(String origem, Context context) {


        // Log.e(TAG, "ccc externalLocations");
        Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
        // Log.e(TAG, "ccc externalLocations buscou");


        if (origem.equals("E")) {

            for (int n = 0; n < externalLocations.size(); n++) {
                // Log.e(TAG,"GetDirPrinc externalLocations origem " + origem + " n " + n + "externalLocations " + externalLocations.get(n));
            }

            //  if (externalLocations.containsKey(ExternalStorage.EXTERNAL_SD_CARD)) {
            if (isExternalStorageWritable()) {
                // ExternalStorage.isAvailable()) {


                // Log.e(TAG, "zzz e externalSdCard.toString() " + externalSdCard.toString());
                // File dir2 = new File(Environment.getExternalStorageDirectory(), null);

                //    final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");

                //  final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
                //   File file =  new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "EU");

                File file[] = ContextCompat.getExternalFilesDirs(context, null);
                Map<String, File> externalLocationsa = ExternalStorage.getAllStorageLocations();
                File sdCard = externalLocationsa.get(ExternalStorage.SD_CARD);
                File externalSdCard = externalLocationsa.get(ExternalStorage.EXTERNAL_SD_CARD);
                //    String txt2 = dir2.toString();
// Assume thisActivity is the current activity
                int permissionCheck = ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                File root = getDir(context);
                String vvv = Environment.getExternalStorageDirectory().getPath();
                String txt3 = FindSD(context);
// "/storage/extSdCard"
                return txt3 + "/EProposal";


            } else {


                String value = System.getenv("SECONDARY_STORAGE");
                if (!TextUtils.isEmpty(value)) {
                    String[] paths = value.split(":");

                    for (String path : paths) {
                        File file = new File(path);


                        Log.e(TAG, "Files store " + file.toString() + " file.getAbsolutePath " + file.getAbsolutePath());
                        if (file.isDirectory()) {
                            // Log.e(TAG, "zzz e  file.toString() " +  file.toString());
                            return file.toString() + "/EProposal";
                        }
                    }
                }

            }
        }

        if (origem.equals("I")) {

            // Log.e(TAG, "zzz i sdCard.toString() " + sdCard.toString());
            // Log.e(TAG, "zzz i System.getenv(\"EXTERNAL_STORAGE\") " +  System.getenv("EXTERNAL_STORAGE"));

            return sdCard.toString() + "/EProposal";

        }


        return null;

    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String FindSD(Context oApplicationContext) {
        // Get the list of possible paths
        Resources oResources = oApplicationContext.getResources();
        String[] arrPaths = oResources.getStringArray(R.array.sd_card_paths);
        String strSDCardPath = "/mnt";
        String strSDKPath = Environment.getExternalStorageDirectory().getAbsolutePath();
// Loop through the possible paths and test each one
        for (String strPathPart1 : arrPaths) {
// Test this path
            String strTestPath = strSDCardPath + strPathPart1;
            if (strSDKPath.compareToIgnoreCase(strTestPath) != 0 && mTestPath(strTestPath))
                return strTestPath;
// Try combinations
            for (String strPathPart2 : arrPaths) {
// Test this combined path
                strTestPath = strSDCardPath + strPathPart1 + strPathPart2;
                if (strSDKPath.compareToIgnoreCase(strTestPath) != 0 && mTestPath(strTestPath))
                    return strTestPath;
            }
        }
// Try it with the path provided from SDK (if it is different)
        strSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (!strSDCardPath.equals("/mnt")) {
// Loop through the possible paths and test each one
            for (String strPathPart1 : arrPaths) {
// Test this path
                String strTestPath = strSDCardPath + strPathPart1;
                if (strSDKPath.compareToIgnoreCase(strTestPath) != 0 && mTestPath(strTestPath))
                    return strTestPath;
// Try combinations
                for (String strPathPart2 : arrPaths) {
// Test this combined   path
                    strTestPath = strSDCardPath + strPathPart1 + strPathPart2;
                    if (strSDKPath.compareToIgnoreCase(strTestPath) != 0 && mTestPath(strTestPath))
                        return strTestPath;
                }
            }
        }
        // Try just the path provided from SDK
        strSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (mTestPath(strSDCardPath)) return strSDCardPath;
        // Failed
        return null;
    }

    private static File getDir(Context context) {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "EProposal");
        else
            cacheDir = context.getCacheDir();
        return cacheDir;
    }

    private static boolean mTestPath(String strSDCardPath) {
        Log.d(TAG, "TEST SDCard = " + strSDCardPath);
        try {
// Check if there is an sd card sub folder
            File oFile = new File(strSDCardPath);
            if (oFile.exists()) {
                Log.d(TAG, "Exists");
                if (oFile.isDirectory()) {
                    Log.d(TAG, "Directory");
                    if (!oFile.canRead()) {
                        Log.d(TAG, "Cannot read");
                    } else {
                        if (!oFile.canWrite()) {
                            Log.d(TAG, "Cannot write");
                        }
                        return true;
                    }
                } else {
                    Log.d(TAG, "NOT Directory");
                }
            } else {
                Log.d(TAG, "Does not exist");
            }
            return false;
        } catch (Exception ex) {
            Log.e(TAG, "mTestPath Exception " + ex.getLocalizedMessage());
            return false;
        }
    }

    public static String FindDir(String tipo, Context context) {
        // TODO Auto-generated method stub
        String saida = " ";

        Map<String, File> externalLocations = ExternalStorage
                .getAllStorageLocations();

        // File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
        // File externalSdCard =
        // externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
        // File externalSdCard =
        // externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);

        String strUserName = settings.getString("storage", "1");

        String state = android.os.Environment.getExternalStorageState();
        // Log.e(TAG, "storage " + strUserName );
        if (strUserName.equals("0")) {
            boolean bol = isExternalStorageWritable();
            File dir = context.getExternalFilesDir(null);
            String txt = dir.toString();

            //  if (externalLocations.containsKey(ExternalStorage.EXTERNAL_SD_CARD)) {
            if (isExternalStorageWritable()) {
                // ExternalStorage.isAvailable()) {
                File dir4 = Environment.getExternalStorageDirectory();
                File dir5 = new File(Environment.getExternalStorageDirectory(), "Sidney");
                File primaryExtSd = Environment.getExternalStorageDirectory();
                final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
                File dir2 = new File(primaryExtSd.getParent());
                String txt2 = dir2.toString();
                final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
                File file4 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String txt4 = file4.toString();
                String txt3 = FindSD(context);
                saida = txt3 + "/EProposal" + tipo;
                //     saida = externalSdCard.toString() + "/EProposal" + tipo;
            } else {

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("storage", "1");
                editor.commit();
                saida = sdCard.toString() + "/EProposal" + tipo;

            }

        } else {

            saida = sdCard.toString() + "/EProposal" + tipo;
        }
        // Log.e("dir", saida);

        // Log.e(TAG, "saisd: " + saida);
        return saida;

    }
}
