package com.example.secondsql;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.secondsql.entities.Phone;
import com.example.secondsql.repo.PhonesRepo;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhonesRepo repository;
    private final LiveData<List<Phone>> phones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        repository = new PhonesRepo(application);
        phones = repository.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones(){
        return phones;
    }

    public void addPhone(Phone phone){
        repository.addPhone(phone);
    }

    public void updatePhone(Phone phone){
        repository.updatePhone(phone);
    }

    public void deletePhone(Phone phone) {
        repository.deletePhone(phone);
    }

    public LiveData<List<Phone>> getPhones(){
        return repository.getPhones();
    }

}
