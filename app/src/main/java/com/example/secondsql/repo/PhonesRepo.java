package com.example.secondsql.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.secondsql.DAO.PhoneDAO;
import com.example.secondsql.database.PhonesDB;
import com.example.secondsql.entities.Phone;

import java.util.List;

public class PhonesRepo {
    private PhoneDAO mPhoneDAO;
    private LiveData<List<Phone>> mAllPhones;

    public PhonesRepo(Application application){
        PhonesDB phonesDB = PhonesDB.getDatabase(application);
        mPhoneDAO = phonesDB.phoneDAO();
        mAllPhones = mPhoneDAO.getAll();
    }



    public LiveData<List<Phone>> getAllPhones() {
        return mPhoneDAO.getAll();
    }

    public void addPhone(Phone phone){
        mPhoneDAO.insert(phone);
    }

    public void updatePhone(Phone phone){
        mPhoneDAO.update(phone);
    }

    public void deletePhone(Phone phone){
        mPhoneDAO.delete(phone);
    }

    public LiveData<List<Phone>> getPhones(){
        return mPhoneDAO.getAll();
    }

    public void deleteAllPhones(){
        mPhoneDAO.deleteAll();
    }
}
