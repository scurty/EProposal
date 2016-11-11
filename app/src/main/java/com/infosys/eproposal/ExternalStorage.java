package com.infosys.eproposal;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by scurt on 10/11/2016.
 */
public class ExternalStorage {

    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";
    private static final String TAG = "ExternalStorage";

    /**
     * @return True if the external storage is available. False otherwise.
     */
    public static boolean isAvailable() {
        String state = Environment.getExternalStorageState();

        //  || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.e("txt", "ta sim");
            return true;
        }
        Log.e("txt", "ta nao");
        return false;
    }

    public static String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * @return True if the external storage is writable. False otherwise.
     */
    public static boolean isWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;

    }

    /**
     * @return A map of all storage locations available
     */

    public static Map<String, File> getAllStorageLocations() {
        Map<String, File> map = new HashMap<String, File>(10);

        List<String> mMounts = new ArrayList<String>(10);
        List<String> mVold = new ArrayList<String>(10);
        mMounts.add("/mnt/sdcard");
        mVold.add("/mnt/sdcard");
        // Log.e(TAG,"Passou1 ");

        try {
            // Log.e(TAG,"Passou2");
            File mountFile = new File("/proc/mounts");
            if (mountFile.exists()) {
                // Log.e(TAG,"Passou3");
                Scanner scanner = new Scanner(mountFile);
                while (scanner.hasNext()) {

                    String line = scanner.nextLine();
                    // Log.e(TAG,"Passou3.4 " + line );
                    if (line.startsWith("/dev/block/vold/")) {
                        // Log.e(TAG,"Passou3.2");
                        String[] lineElements = line.split(" ");
                        String element = lineElements[1];

                        // don't add the default mount path
                        // it's already in the list.
                        // Log.e(TAG,"Passou3.4 " + element);

                        // GS2
                        if (!element.equals("/mnt/sdcard"))
                            mMounts.add(element);

                        // TABab S
                        if (!element.equals("/mnt/media_rw/extSdCard"))
                            mMounts.add(element);
                    }
                }
            }
        } catch (Exception e) {

            Log.e(TAG, "Erro1 " + e);
        }
        // Log.e(TAG,"Passou4");
        try {
            File voldFile = new File("/system/etc/vold.fstab");
            if (voldFile.exists()) {
                Scanner scanner = new Scanner(voldFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("dev_mount")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];

                        if (element.contains(":"))
                            element = element.substring(0, element.indexOf(":"));

                        if (!element.equals("/mnt/sdcard"))
                            mVold.add(element);

                        // TABab S
                        if (!element.equals("/mnt/media_rw/extSdCard"))
                            mVold.add(element);
                    }
                }
            }
        } catch (Exception e) {

            Log.e(TAG, "Erro2 " + e);
        }
        // Log.e(TAG,"Passou5");

        for (int i = 0; i < mMounts.size(); i++) {
            String mount = mMounts.get(i);
            if (!mVold.contains(mount))
                mMounts.remove(i--);
        }
        mVold.clear();

        List<String> mountHash = new ArrayList<String>(10);

        for (String mount : mMounts) {
            // Log.e(TAG,"Passou6");
            File root = new File(mount);
            // Log.e(TAG,"Passou6 root " + root );
            if (root.exists() && root.isDirectory() && root.canWrite()) {
                // Log.e(TAG,"Passou6 root exit" + root );
                File[] list = root.listFiles();
                String hash = "[";
                if (list != null) {
                    // Log.e(TAG,"Passou6 root exit list " + list );
                    for (File f : list) {
                        hash += f.getName().hashCode() + ":" + f.length() + ", ";
                    }
                }
                hash += "]";
                if (!mountHash.contains(hash)) {
                    // Log.e(TAG,"Passou6 root exit hash " + mountHash );
                    String key = SD_CARD + "_" + map.size();
                    // Log.e(TAG,"Passou6 root exit mapsize " + map.size() );
                    if (map.size() == 0) {
                        key = SD_CARD;
                    } else if (map.size() == 1) {
                        key = EXTERNAL_SD_CARD;
                    }
                    // Log.e(TAG,"Passou6 root exit hash " + key );
                    mountHash.add(hash);
                    map.put(key, root);
                }
            }
        }

        mMounts.clear();
        // Log.e(TAG,"Passou7");
        if (map.isEmpty()) {
            // Log.e(TAG,"map.isEmpty()");
            map.put(SD_CARD, Environment.getExternalStorageDirectory());
        }


        return map;
    }
}