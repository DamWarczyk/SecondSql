package com.example.secondsql;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.secondsql.databinding.FragmentSecondBinding;
import com.example.secondsql.entities.Phone;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private PhoneViewModel phoneViewModel;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

        binding.saveButton.setOnClickListener(view1 -> {
            String manufactur = binding.manufacturerEditText.getText().toString();
            String model = binding.modelEditText.getText().toString();
            String android = binding.androidEditText.getText().toString();
            String web = binding.webSiteEditText.getText().toString();
            Phone phone = new Phone(null, manufactur, model, android, web);
            phoneViewModel.addPhone(phone);
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_phoneFragment);
        });

        binding.cancelButton.setOnClickListener(view1 -> {
            binding.manufacturerEditText.setText("");
            binding.modelEditText.setText("");
            binding.androidEditText.setText("");
            binding.webSiteEditText.setText("");
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_phoneFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}