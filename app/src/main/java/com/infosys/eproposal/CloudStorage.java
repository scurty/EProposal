package com.infosys.eproposal;

/**
 * Created by scurt on 30/11/2016.
 * <p>
 * Created by scurt on 18/11/2016.
 */


/**
 * Created by scurt on 18/11/2016.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Simple wrapper around the Google Cloud Storage API
 */
public class CloudStorage {

    private static final String APPLICATION_NAME_PROPERTY = "EProposal";
    private static final String ACCOUNT_ID_PROPERTY = "eproposal-150911@appspot.gserviceaccount.com"; //107134891189332924893"; //224664608724.apps.googleusercontent.com";
    private static final String PROJECT_ID_PROPERTY = "eproposal-150911";
    private static final String PRIVATE_KEY_PATH_PROPERTY = "private.key.path";
    private static Properties properties;
    private static Storage storage;
    private static Context mcontext = null;

    //  private static final String PRIVATE_KEY_PATH_PROPERTY = "private.key.path";

    /**
     * Uploads a file to a bucket. Filename and content type will be based on
     * the original file.
     *
     * @param bucketName
     *            Bucket where file will be uploaded
     * @param filePath
     *            Absolute path of the file to upload
     * @param stg
     * @throws Exception
     */
    public static void uploadFile(String bucketName, String filePath, String stg)
            throws Exception {

        new UploadFileTask().execute(bucketName, filePath, stg);

      /*  Storage storage = getStorage();

        StorageObject object = new StorageObject();
        object.setBucket(bucketName);

        File file = new File(filePath);

        InputStream stream = new FileInputStream(file);
        try {
            String contentType = URLConnection
                    .guessContentTypeFromStream(stream);
            InputStreamContent content = new InputStreamContent(contentType,
                    stream);

            Storage.Objects.Insert insert = storage.objects().insert(
                    bucketName, null, content);
            insert.setName(file.getName());

            insert.execute();
        } finally {
            stream.close();
        }*/
    }

    public static void downloadFile(String bucketName, String fileName, String destinationDirectory) throws Exception {

        File directory = new File(destinationDirectory);
        if (!directory.isDirectory()) {
            throw new Exception("Provided destinationDirectory path is not a directory");
        }
        File file = new File(directory.getAbsolutePath() + "/" + fileName);

        Storage storage = getStorage();

        Storage.Objects.Get get = storage.objects().get(bucketName, fileName);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // If you're not in AppEngine, download the whole thing in one request, if possible.
        get.getMediaHttpDownloader().setDirectDownloadEnabled(false);
        get.executeMediaAndDownloadTo(out);

        FileOutputStream fos = new FileOutputStream(file);
        try {

            // Put data in your baos

            out.writeTo(fos);
        } catch (IOException ioe) {
            // Handle exception here
            ioe.printStackTrace();
        } finally {
            fos.close();
        }

       /* ByteArrayOutputStream out = new ByteArrayOutputStream();
        // If you're not in AppEngine, download the whole thing in one request, if possible.
        get.getMediaHttpDownloader().setDirectDownloadEnabled(false);
        get.executeMediaAndDownloadTo(out);*/
        /* FileOutputStream stream = new FileOutputStream(file);
        try {
            // get.executeAndDownloadTo(stream);
            get.executeMedia();
        } finally {
            stream.close();
        }*/
    }

    /**
     * Deletes a file within a bucket
     *
     * @param bucketName
     *            Name of bucket that contains the file
     * @param fileName
     *            The file to delete
     * @throws Exception
     */
    public static void deleteFile(String bucketName, String fileName)
            throws Exception {

        Storage storage = getStorage();

        storage.objects().delete(bucketName, fileName).execute();
    }

    /**
     * Creates a bucket
     *
     * @param bucketName
     *            Name of bucket to create
     * @throws Exception
     */
    public static void createBucket(String bucketName) throws Exception {

        new DownloadFilesTask().execute(bucketName);


    }

    /**
     * Deletes a bucket
     *
     * @param bucketName
     *            Name of bucket to delete
     * @throws Exception
     */
    public static void deleteBucket(String bucketName) throws Exception {

        Storage storage = getStorage();

        storage.buckets().delete(bucketName).execute();
    }

    /**
     * Lists the objects in a bucket
     *
     * @param bucketName bucket name to list
     * @return Array of object names
     * @throws Exception
     */
    public static List<String> listBucket(String bucketName) throws Exception {

        Storage storage = getStorage();

        List<String> list = new ArrayList<String>();

        List<StorageObject> objects = storage.objects().list(bucketName).execute().getItems();
        if (objects != null) {
            for (StorageObject o : objects) {
                list.add(o.getName());
            }
        }

        return list;
    }

    /**
     * List the buckets with the project
     * (Project is configured in properties)
     *
     * @return
     * @throws Exception
     */
    public static List<String> listBuckets() throws Exception {

        Storage storage = getStorage();

        List<String> list = new ArrayList<String>();

        List<Bucket> buckets = storage.buckets().list(PROJECT_ID_PROPERTY).execute().getItems();
        if (buckets != null) {
            for (Bucket b : buckets) {
                list.add(b.getName());
            }
        }

        return list;
    }

    private static Properties getProperties() throws Exception {

        if (properties == null) {
            properties = new Properties();
            InputStream stream = CloudStorage.class
                    .getResourceAsStream("/cloudstorage.properties");
            try {
                properties.load(stream);
            } catch (IOException e) {
                throw new RuntimeException(
                        "cloudstorage.properties must be present in classpath",
                        e);
            } finally {
                stream.close();
            }
        }
        return properties;
    }

    private static Storage getStorage() throws Exception {

        if (storage == null) {

            HttpTransport httpTransport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();

            List<String> scopes = new ArrayList<String>();
            scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);


            AssetManager assetManager = mcontext.getAssets();
            InputStream input = assetManager.open("EProposal-f0f009616ce0.p12");
            File tempFile = File.createTempFile("EProposal-f0f009616ce0", "p12");
            tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(input, out);


            Credential credential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setServiceAccountId(ACCOUNT_ID_PROPERTY)
                    .setServiceAccountPrivateKeyFromP12File(tempFile)
                    .setServiceAccountScopes(scopes).build();

            storage = new Storage.Builder(httpTransport, jsonFactory,
                    credential).setApplicationName(APPLICATION_NAME_PROPERTY)
                    .build();
        }

        return storage;
    }

    public static void Context(Context context) {
        mcontext = context;
    }

    private static class UploadFileTask extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {

            try {
                Storage storage = CloudStorage.getStorage();

                StorageObject object = new StorageObject();
                object.setBucket(strings[0]);

                File file = new File(strings[1]);

                InputStream stream = new FileInputStream(file);
                try {
                    String contentType = URLConnection
                            .guessContentTypeFromStream(stream);
                    InputStreamContent content = new InputStreamContent(contentType,
                            stream);

                    Storage.Objects.Insert insert = storage.objects().insert(
                            strings[0], null, content);

                    String extension = "";
                    int i = file.getName().lastIndexOf('.');
                    if (i >= 0) {
                        extension = file.getName().substring(i + 1);
                    }

                    String newfile = strings[2] + "." + extension;
                    insert.setName(newfile);
                    // insert.setName(file.getName());

                    String teste = file.getName();
                    String teste1 = file.getAbsolutePath();
                    String teste2 = file.getCanonicalPath();
                    String teste3 = file.getPath();
                    String teste4 = file.getParent();
                    String teste5 = file.toString();

                    insert.execute();
                } finally {
                    stream.close();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ;

         /*   Storage storage = null;
            try {
                storage = CloudStorage.getStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bucket bucket = new Bucket();
            bucket.setName(String.valueOf(strings[0]));


            try {
                storage.buckets().insert(PROJECT_ID_PROPERTY, bucket).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
            return null;
        }

        protected void onPostExecute(Long result) {

        }
    }

    private static class DownloadFilesTask extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {
            Storage storage = null;
            try {
                storage = CloudStorage.getStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bucket bucket = new Bucket();
            bucket.setName(String.valueOf(strings[0]));


            try {
                storage.buckets().insert(PROJECT_ID_PROPERTY, bucket).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Long result) {

        }
    }
}
