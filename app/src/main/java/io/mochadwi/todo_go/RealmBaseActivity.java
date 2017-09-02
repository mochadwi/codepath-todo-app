package io.mochadwi.todo_go;

import android.support.v7.app.AppCompatActivity;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by mochadwi on 9/1/17.
 */

public abstract class RealmBaseActivity extends AppCompatActivity {

    private RealmConfiguration realmConfiguration;

    protected RealmConfiguration getRealmConfig() {
        if (realmConfiguration == null) {
            realmConfiguration = new RealmConfiguration
                    .Builder()
                    .schemaVersion(2)
                    .migration(new RealmMigration() {
                        @Override
                        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                            final RealmSchema schema = realm.getSchema();

                            if (oldVersion == 1) {
                                final RealmObjectSchema userSchema = schema.get("TodoItem");
                                userSchema.addField("title", String.class);
                                userSchema.addField("date", String.class);
                            }
                        }
                    })
                    .deleteRealmIfMigrationNeeded()
                    .build();
        }
        return realmConfiguration;
    }

    protected void resetRealm() {
        Realm.deleteRealm(getRealmConfig());
    }
}
