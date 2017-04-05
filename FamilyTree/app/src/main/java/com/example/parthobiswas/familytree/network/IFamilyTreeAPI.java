package com.example.parthobiswas.familytree.network;

import com.example.parthobiswas.familytree.conf.ApiEndpoint;
import com.example.parthobiswas.familytree.conf.JsonKeys;
import com.example.parthobiswas.familytree.models.JSON.NodeDetails;
import com.example.parthobiswas.familytree.models.JSON.PersonDetails;
import com.example.parthobiswas.familytree.models.JSON.Relations;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by parthobiswas on 3/21/17.
 */

public interface IFamilyTreeAPI {

    @GET(ApiEndpoint.GET_PERSON)
    Call<PersonDetails>  getPerson(@Query(JsonKeys.SSN) String ssn);

    @GET(ApiEndpoint.GET_RELATION)
    Call<Relations>  getRelations(@Query(JsonKeys.SSN) String ssn);

    @POST(ApiEndpoint.CREATE_ME)
    Call<PersonDetails>  createMe(@Body PersonDetails personDetails);

    @POST(ApiEndpoint.CREATE_NODE)
    Call<NodeDetails>  createNode(@Body NodeDetails nodeDetails);
}
