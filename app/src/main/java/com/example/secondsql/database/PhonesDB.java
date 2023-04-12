package com.example.secondsql.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.secondsql.DAO.PhoneDAO;
import com.example.secondsql.entities.Phone;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 2)
public abstract class PhonesDB extends RoomDatabase {
    public abstract PhoneDAO phoneDAO();

    private static volatile PhonesDB INSTANCE;

    public static PhonesDB getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (PhonesDB.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PhonesDB.class, "PhoneDataBase")
                            .addCallback(phonesDatabaseCallback )
                            .fallbackToDestructiveMigration()
                            //TODO: Jest tanio? Jest tanio, Jest dobrze? Jest tanio, tego tutaj nie powinno się stosować
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final RoomDatabase.Callback phonesDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PhoneDAO dao = INSTANCE.phoneDAO();
                List<Phone> phones = List.of(
                        new Phone(null, "Samsung", "Galaxy S23 Ultra", "13", "https://www.samsung.com/pl/smartphones/galaxy-s23-ultra/buy/"),
                        new Phone(null, "Huawei", "Mate 20 lite", "10", "https://www.mgsm.pl/pl/katalog/huawei/mate20lite/"),
                        new Phone(null, "Nokia", "C12 PRO", "12 Go", "https://www.mgsm.pl/pl/katalog/nokia/c12pro/"),
                        new Phone(null,"Sony", "Xperia J", "4.0 Ice Cream Sandwich", "https://www.mgsm.pl/pl/katalog/sony/xperiaj/"),
                        new Phone(null, "OnePlus", "Nord 2T 5G", "12", "https://www.gadgets360.com/oneplus-nord-2t-5g-price-in-india-106885"),
                        new Phone(null, "DUPA DUPA", "DUPA", "DUPA", "DUPA")
                );
                for(Phone p: phones){
                    dao.insert(p);
                }
            });
        }
    };

}
