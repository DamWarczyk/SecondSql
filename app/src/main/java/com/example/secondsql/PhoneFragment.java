package com.example.secondsql;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.secondsql.databinding.FragmentItemListBinding;
import com.example.secondsql.entities.Phone;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * A fragment representing a list of Items.
 */
public class PhoneFragment extends Fragment implements MyPhoneRecyclerViewAdapter.onItemClickListener {

    private FragmentItemListBinding binding;
    private PhoneViewModel phoneViewModel;
    private MyPhoneRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    private ItemTouchHelper itemTouchHelper;

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
                        new Phone(null,"Pixel", "7 Pro", "14", "https://store.google.com/us/product/pixel_7_pro?hl=en-US"),
                        new Phone(null, "Samsung", "Galaxy a51", "13", "https://www.samsung.com/pl/smartphones/galaxy-a/galaxy-a53-5g-awesome-blue-128gb-sm-a536blbneue/"),
                        new Phone(null, "Huawei", "Mate50 PRO", "10", "https://consumer.huawei.com/pl/phones/mate50-pro/"),
                        new Phone(null, "Samsung", "Galaxy S23 Ultra", "13", "https://www.samsung.com/pl/smartphones/galaxy-s23-ultra/buy/"),
                        new Phone(null,"SAMSUNG ", "Galaxy Z Fold4", "12", "https://www.samsung.com/pl/smartphones/galaxy-z-fold4/")
                );
                for (Phone p : phones) {
                    phoneViewModel.addPhone(p);
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PhoneFragment.this)
                        .navigate(R.id.action_phoneFragment_to_SecondFragment);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                phoneViewModel.deletePhone(adapter.getPhones().get(adapterPosition));
                Toast toast = Toast.makeText(getContext(), "Phone deleted", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(position -> {
            Phone phone = adapter.getPhones().get(position);
            final Bundle bundle = new Bundle();
            bundle.putLong("ID", phone.getId());
            bundle.putString("PRODUCER", phone.getProducer());
            bundle.putString("MODEL", phone.getModel());
            bundle.putString("VERSION", phone.getAndroidVer());
            bundle.putString("WEBSITE", phone.getWeb());
            Toast toast = Toast.makeText(view.getContext(), "Phone " + phone.getModel().toString(), Toast.LENGTH_SHORT);
            toast.show();

            NavHostFragment.findNavController(PhoneFragment.this).navigate(R.id.action_phoneFragment_to_SecondFragment, bundle);
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

    @Override
    public void onItemClickListener(int position) {
    }
}