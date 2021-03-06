/*
 * Copyright (C) 2016 David Dejori, Alex Lardschneider
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.sasabz.android.sasabus.data.realm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import it.sasabz.android.sasabus.R;
import it.sasabz.android.sasabus.data.realm.busstop.BusStop;
import it.sasabz.android.sasabus.data.realm.busstop.BusStopModule;
import it.sasabz.android.sasabus.data.realm.busstop.SadBusStop;
import it.sasabz.android.sasabus.data.vdv.model.VdvBusStop;
import it.sasabz.android.sasabus.util.Utils;
import timber.log.Timber;

public final class BusStopRealmHelper {

    private static final String TAG = "BusStopRealmHelper";

    private static final int DB_VERSION = 2016121801; // YY MM DD Rev.
    private static final String DB_NAME = "busstops.realm";

    public static RealmConfiguration CONFIG;

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private BusStopRealmHelper() {
    }

    /**
     * Initializes the bus stop realm instance. This database holds all SASA and SAD bus stops
     * needed throughout the app.
     * <p>
     * As using a {@link io.realm.RealmMigration} in this case makes no sense, a empty
     * {@link io.realm.RealmMigration} is provided and the old database will be deleted if it was
     * upgraded in the new app version. On first app start or after every update which shipped a
     * never database the database is copied from the app assets.
     *
     * @param context Context to build the {@link RealmConfiguration}
     */
    public static void init(Context context) {
        sContext = context;

        CONFIG = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(DB_VERSION)
                .assetFile(DB_NAME)
                .modules(new BusStopModule())
                .migration((realm, oldVersion, newVersion) -> {
                    // Provide no migration.
                })
                .build();

        DynamicRealm dynamicRealm = DynamicRealm.getInstance(CONFIG);
        long version = dynamicRealm.getVersion();
        dynamicRealm.close();

        Timber.w("Realm db version: %s, should be %s", version, DB_VERSION);

        if (version < DB_VERSION || version > DB_VERSION) {
            Timber.e("Deleting old realm");

            Realm.deleteRealm(CONFIG);
        }

        Realm.getInstance(CONFIG).close();
    }

    public static String getName(int id) {
        String locale = Utils.locale(sContext);

        Realm realm = Realm.getInstance(CONFIG);
        BusStop busStop = realm.where(BusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            Timber.e("Missing SASA station: %s", id);
            Utils.logException(new Throwable("getName SASA station = 0"));

            return sContext.getString(R.string.unknown);
        }

        String name = locale.contains("de") ? busStop.getNameDe() : busStop.getNameIt();

        realm.close();
        return name;
    }

    public static String getSadName(int id) {
        String locale = Utils.locale(sContext);

        Realm realm = Realm.getInstance(CONFIG);
        SadBusStop busStop = realm.where(SadBusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            Timber.e("Missing SASA station: %s", id);
            Utils.logException(new Throwable("getSadName SAD station = 0"));

            return sContext.getString(R.string.unknown);
        }

        String name = locale.contains("de") ? busStop.getNameDe() : busStop.getNameIt();

        realm.close();
        return name;
    }

    public static String getMunic(int id) {
        String locale = Utils.locale(sContext);

        Realm realm = Realm.getInstance(CONFIG);
        BusStop busStop = realm.where(BusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            missingBusStop(id);
            return sContext.getString(R.string.unknown);
        }

        String name = locale.contains("de") ? busStop.getMunicDe() : busStop.getMunicIt();

        realm.close();
        return name;
    }

    public static String getSadMunic(int id) {
        String locale = Utils.locale(sContext);

        Realm realm = Realm.getInstance(CONFIG);
        SadBusStop busStop = realm.where(SadBusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            missingBusStop(id);
            return sContext.getString(R.string.unknown);
        }

        String name = locale.contains("de") ? busStop.getMunicDe() : busStop.getMunicIt();

        realm.close();
        return name;
    }

    public static BusStop getBusStop(int id) {
        Realm realm = Realm.getInstance(CONFIG);
        BusStop busStop = realm.where(BusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            missingBusStop(id);
            busStop = new BusStop(id, String.valueOf(id), String.valueOf(id), 0, 0, 0);
        } else {
            busStop = realm.copyFromRealm(busStop);
        }

        realm.close();

        return busStop;
    }

    @Nullable
    public static BusStop getBusStopOrNull(int id) {
        Realm realm = Realm.getInstance(CONFIG);
        BusStop busStop = realm.where(BusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            return null;
        }

        busStop = realm.copyFromRealm(busStop);

        realm.close();

        return busStop;
    }

    public static SadBusStop getSadBusStop(int id) {
        Realm realm = Realm.getInstance(CONFIG);
        SadBusStop busStop = realm.where(SadBusStop.class).equalTo("id", id).findFirst();

        if (busStop == null) {
            missingBusStop(id);
            busStop = new SadBusStop(id, String.valueOf(id), String.valueOf(id), 0, 0);
        } else {
            busStop = realm.copyFromRealm(busStop);
        }

        realm.close();

        return busStop;
    }

    public static BusStop getBusStopOrNullFromGroup(int family) {
        Realm realm = Realm.getInstance(CONFIG);
        BusStop busStop = realm.where(BusStop.class).equalTo("family", family).findFirst();

        if (busStop == null) {
            return null;
        }

        busStop = realm.copyFromRealm(busStop);

        realm.close();

        return busStop;
    }

    public static List<Integer> getBusStopIdsFromGroup(int group) {
        Realm realm = Realm.getInstance(CONFIG);
        RealmResults<BusStop> results = realm.where(BusStop.class).equalTo("family", group).findAll();

        List<Integer> resultIds = new ArrayList<>();
        for (BusStop busStop : results) {
            resultIds.add(busStop.getId());
        }

        realm.close();

        return resultIds;
    }

    public static int getBusStopGroup(int id) {
        Realm realm = Realm.getInstance(CONFIG);
        BusStop busStop = realm.where(BusStop.class).equalTo("id", id).findFirst();

        int result;

        if (busStop == null) {
            result = 0;
        } else {
            result = busStop.getFamily();
        }

        realm.close();

        return result;
    }

    /**
     * Returns all stations in the same family (having the same title and municipality).
     *
     * @return all stations having the same title and municipality in an {@link ArrayList}
     */
    public static Collection<VdvBusStop> getBusStopsFromFamily(int family) {
        Realm realm = Realm.getInstance(CONFIG);
        List<BusStop> busStops = realm.where(BusStop.class).equalTo("family", family).findAll();

        if (busStops.isEmpty()) {
            missingBusStopFamily(family);
            return Collections.emptyList();
        }

        Collection<VdvBusStop> stops = new ArrayList<>();

        for (BusStop busStop : busStops) {
            stops.add(new VdvBusStop(busStop.getId()));
        }

        realm.close();

        return stops;
    }


    // ==================================== MISSING BUS STOPS ======================================

    private static void missingBusStop(int id) {
        String message = "Missing bus stop: " + id;

        Timber.e(message);
        Utils.logException(new NoSuchElementException(message));
    }

    private static void missingBusStopFamily(int id) {
        String message = "Missing bus stop family: " + id;

        Timber.e(message);
        Utils.logException(new NoSuchElementException(message));
    }
}
