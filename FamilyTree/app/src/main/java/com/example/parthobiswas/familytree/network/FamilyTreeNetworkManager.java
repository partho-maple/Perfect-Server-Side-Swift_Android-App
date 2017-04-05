package com.example.parthobiswas.familytree.network;

import android.content.Context;

import com.example.parthobiswas.familytree.conf.ServerConfig;
import com.example.parthobiswas.familytree.interfaces.IAPICallbacks;
import com.example.parthobiswas.familytree.models.JSON.NodeDetails;
import com.example.parthobiswas.familytree.models.JSON.PersonDetails;
import com.example.parthobiswas.familytree.models.JSON.Relations;
import com.example.parthobiswas.familytree.models.JSON.RelationshipDetails;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by parthobiswas on 3/21/17.
 */

public class FamilyTreeNetworkManager {
    private static final FamilyTreeNetworkManager ourInstance = new FamilyTreeNetworkManager();

    public static FamilyTreeNetworkManager getInstance() {
        return ourInstance;
    }

    private Retrofit retrofit;
    private IFamilyTreeAPI familyTreeAPI;

    private FamilyTreeNetworkManager() {

        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

//        Timber.i("Base URL: %s", ServerConfig.BASE_URL);

        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        familyTreeAPI = retrofit.create(IFamilyTreeAPI.class);

    }

    public IFamilyTreeAPI getFamilyTreeAPI() {
        return  this.familyTreeAPI;
    }



    public void getPersonDetails(String ssn, final Context context, final IAPICallbacks events) {

        try {
            Call<PersonDetails> request = this.familyTreeAPI.getPerson(ssn);
            request.enqueue(new Callback<PersonDetails>() {
                @Override
                public void onResponse(Call<PersonDetails> call, Response<PersonDetails> response) {
                    if (response.body() != null) {
                        PersonDetails personDetails = (PersonDetails) response.body();
                        if (personDetails.getSocial_security_number() != null) {
                            getRelationshipDetails(personDetails, context, events);
                        } else {
                            events.onError("Faild to fetch personal details !");
                        }
                    } else {
                        events.onError("Faild to fetch personal details !");
                    }
                }

                @Override
                public void onFailure(Call<PersonDetails> call, Throwable t) {
                    Timber.d(t);
                    events.onError("Faild to fetch personal details !");
                }
            });

        } catch (Error e) {
            Timber.d(e);
            events.onError("Faild to fetch personal details !");
        }

    }

    public void getRelationshipDetails(final PersonDetails personDetails, final Context context, final IAPICallbacks events) {

        try {
            Call<Relations> request = this.familyTreeAPI.getRelations(personDetails.getSocial_security_number());
            request.enqueue(new Callback<Relations>() {
                @Override
                public void onResponse(Call<Relations> call, Response<Relations> response) {

                    if (response.body() != null) {
                        Relations relations = (Relations) response.body();
                        if (relations.getRelations() != null) {
                            //MARK: Creating family tree here.
                            RelationshipDetails myRelationshipDetails = personDetails.convertToRelationDetails("It's Me", relations.getRelations());

                            RelationshipDetails[] relationshipInfoArr = new RelationshipDetails[1];
                            relationshipInfoArr[0] = myRelationshipDetails;

                            events.onResponse(relationshipInfoArr);
                        } else {
                            events.onError("Faild to fetch relation details !");
                        }
                    } else {
                        events.onError("Faild to fetch relation details !");
                    }
                }

                @Override
                public void onFailure(Call<Relations> call, Throwable t) {
                    Timber.d(t);
                    events.onError("Faild to fetch relation details !");
                }
            });

        } catch (Error e) {
            Timber.d(e);
            events.onError("Faild to fetch relation details !");
        }
    }


    public void createMe(PersonDetails personDetails, final Context context, final IAPICallbacks events) {

        try {
            Call<PersonDetails> request = this.familyTreeAPI.createMe(personDetails);
            request.enqueue(new Callback<PersonDetails>() {
                @Override
                public void onResponse(Call<PersonDetails> call, Response<PersonDetails> response) {

                    if (response.body() != null) {
                        PersonDetails personDetails = (PersonDetails) response.body();
                        if (personDetails.getSocial_security_number() != null) {
                            events.onResponse(personDetails);
                        } else {
                            events.onError("Faild to create personal details !");
                        }

                    } else {
                        events.onError("Faild to create personal details !");
                    }
                }

                @Override
                public void onFailure(Call<PersonDetails> call, Throwable t) {
                    Timber.d(t);
                    events.onError("Faild to create personal details !");
                }
            });

        } catch (Error e) {
            Timber.d(e);
            events.onError("Faild to create personal details !");
        }

    }

    public void createNode(NodeDetails nodeDetails, final Context context, final IAPICallbacks events) {

        try {
            Call<NodeDetails> request = this.familyTreeAPI.createNode(nodeDetails);
            request.enqueue(new Callback<NodeDetails>() {
                @Override
                public void onResponse(Call<NodeDetails> call, Response<NodeDetails> response) {

                    if (response.body() != null) {
                        NodeDetails node = (NodeDetails) response.body();
                        if (node.getSocial_security_number() != null) {
                            events.onResponse(node);
                        } else {
                            events.onError("Faild to create relationship details !");
                        }
                    } else {
                        events.onError("Faild to create relationship details !");
                    }
                }

                @Override
                public void onFailure(Call<NodeDetails> call, Throwable t) {
                    Timber.d(t);
                    events.onError("Faild to create relationship details !");
                }
            });

        } catch (Error e) {
            Timber.d(e);
            events.onError("Faild to fetch personal details !");
        }

    }

}
