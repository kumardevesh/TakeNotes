package practice.takenotes.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.bumptech.glide.Glide;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import practice.takenotes.db.DBHelper;
import practice.takenotes.db.NotesDatabase;

/**
 * Created by shishir on 1/23/2018.
 */
@Module
public class AppModule {

    private Context context;
    public static String DEFAULT_PREF = "default_preferences";


    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context getApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    Resources getResources(Application application) {
        return application.getResources();
    }

    @Provides
    @Singleton
    @Named("default_preferences")
    SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE);
    }
}
