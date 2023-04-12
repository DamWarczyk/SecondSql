package com.example.secondsql;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.secondsql.databinding.FragmentItemListBinding;
import com.example.secondsql.entities.Phone;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * A fragment representing a list of Items.
 */
public class PhoneFragment extends Fragment {

    private FragmentItemListBinding binding;
    private PhoneViewModel phoneViewModel;
    private MyPhoneRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public PhoneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        recyclerView = binding.list;
        adapter = new MyPhoneRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        phoneViewModel.getAllPhones().observe(getViewLifecycleOwner(), phoneListUpdateObserver);
        Executors.newSingleThreadExecutor().execute(() -> {
            if (phoneViewModel.getAllPhones().getValue() == null) {
                List<Phone> phones = List.of(
                        new Phone(null, "Samsung", "Galaxy S23 Ultra", "13", "https://www.samsung.com/pl/smartphones/galaxy-s23-ultra/buy/"),
                        new Phone(null,"Huawei", "Mate 20 lite", "10", "https://www.mgsm.pl/pl/katalog/huawei/mate20lite/"),
                        new Phone(null, "Nokia", "C12 PRO", "12 Go", "https://www.mgsm.pl/pl/katalog/nokia/c12pro/"),
                        new Phone(null, "Sony", "Xperia J", "4.0 Ice Cream Sandwich", "https://www.mgsm.pl/pl/katalog/sony/xperiaj/"),
                        new Phone(null,"OnePlus", "Nord 2T 5G", "12", "https://www.gadgets360.com/oneplus-nord-2t-5g-price-in-india-106885")
                );
                for (Phone p : phones) {
                    phoneViewModel.addPhone(p);
                }
            }
        });
    }

    Observer<List<Phone>> phoneListUpdateObserver = new Observer<>() {
        @Override
        public void onChanged(List<Phone> phones) {
            adapter.setPhonesList(phones);
        }
    };


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_Database) {
            phoneViewModel.deleteAllPhones();
        }

        return super.onOptionsItemSelected(item);
    }
}