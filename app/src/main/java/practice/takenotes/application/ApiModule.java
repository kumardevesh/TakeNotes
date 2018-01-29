package practice.takenotes.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import practice.takenotes.network.VolleyHelper;

/**
 * Created by shishir on 1/23/2018.
 */
@Module
public class ApiModule {

    public ApiModule() {
    }

    @Provides
    @Singleton
    RequestQueue getRequestQueue(Application application) {
        return Volley.newRequestQueue(application);
    }

    @Provides
    @Singleton
    VolleyHelper getVolleyHelper() {
        return new VolleyHelper();
    }
}
