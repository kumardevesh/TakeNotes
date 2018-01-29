package practice.takenotes.application;

import android.app.Application;

import practice.takenotes.db.DbModule;

/**
 * Created by shishir on 1/23/2018.
 */

public class NotesApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .dbModule(new DbModule(this))
                .build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
