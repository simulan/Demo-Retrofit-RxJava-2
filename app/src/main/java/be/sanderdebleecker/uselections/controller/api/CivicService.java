package be.sanderdebleecker.uselections.controller.api;

import be.sanderdebleecker.uselections.model.data.envelope.ElectionsEnvelope;
import be.sanderdebleecker.uselections.model.data.envelope.OfficialsEnvelope;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sander De Bleecker on 08/05/2017.
 * Uses observable as events, inner observables as collections
 * Outer observable thus gets instantly disposed from memory after API call completion
 *
 * Waiting for Retrofit to update Factory.Converter that solves the classcast issue when returning an List of Object when an Object is expected...
 */
public interface CivicService {
    @GET("elections")
    Observable<ElectionsEnvelope> listElections();
    @GET("representatives/ocdId")
    Observable<OfficialsEnvelope> listOfficials(@Query("ocdId") String ocdId);
}


