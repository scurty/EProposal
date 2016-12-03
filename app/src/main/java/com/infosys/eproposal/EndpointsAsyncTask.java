package com.infosys.eproposal;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import endpoints.backend.myApi.MyApi;

/**
 * Created by scurt on 28/11/2016.
 */
public class EndpointsAsyncTask extends AsyncTask<Object, Void, String> {

    private static MyApi myApiService = null;

    @Override
    protected String doInBackground(Object... objects) {

        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //   .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://eproposal-150911.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            // package endpointsnew.backend;
            myApiService = builder.build();
        }

        String type = (String) objects[0];

        try {
            switch (type) {
                case "lst":
                    return myApiService.listar(" ").execute().getData();
                case "isr":
                    Proposal prop = (Proposal) objects[1];
                    JSONObject obj = new JSONObject();
                    obj.put("nome", prop.getName());
                    obj.put("descricao", prop.getDescription());
                    obj.put("senha", prop.getSenha());
                    obj.put("imagepath", prop.getImagepath());
                    String nome = obj.toString();
                    //    String nome = prop.getName();
                    return myApiService.inserir(nome).execute().getData();
                case "lstitem":
                    String txprop_item = (String) objects[1];
                    return myApiService.listaritem(txprop_item).execute().getData();
                case "isritem":
                    ProposalItem propitem = (ProposalItem) objects[1];
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id_prop", propitem.getId_prop());
                    obj2.put("nome", propitem.getName());
                    obj2.put("seq", propitem.getSeq());
                    obj2.put("menu", propitem.getMenu());
                    obj2.put("type", propitem.getType());
                    obj2.put("imagepath", propitem.getImagepath());
                    String stgobj = obj2.toString();
                    //    String nome = prop.getName();
                    return myApiService.inseriritem(stgobj).execute().getData();
                case "sel":
                    Proposal propa = (Proposal) objects[1];
                    JSONObject objs = new JSONObject();
                    objs.put("nome", propa.getName());
                    objs.put("senha", propa.getSenha());
                    String nomes = objs.toString();
                    //    String nome = prop.getName();
                    return myApiService.selecionar(nomes).execute().getData();
            }
        } catch (IOException e) {
            return e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        //     Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
