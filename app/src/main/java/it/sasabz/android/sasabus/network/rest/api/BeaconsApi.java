package it.sasabz.android.sasabus.network.rest.api;

import it.sasabz.android.sasabus.network.rest.Endpoint;
import it.sasabz.android.sasabus.network.rest.model.ScannedBeacon;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface BeaconsApi {

    @POST(Endpoint.BEACONS)
    Observable<Void> postScannedBeacons(@Body ScannedBeacon beacon);
}