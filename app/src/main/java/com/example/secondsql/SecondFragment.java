package com.example.secondsql;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    Long id;
    boolean available;
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
        available = true;

        Bundle bundle = getArguments();
        if (bundle != null){
            id = bundle.getLong("ID");
            binding.manufacturerEditText.setText(bundle.getString("PRODUCER"));
            binding.modelEditText.setText(bundle.getString("MODEL"));
            binding.androidEditText.setText(bundle.getString("VERSION"));
            binding.webSiteEditText.setText(bundle.getString("WEBSITE"));
        }

        if(binding.manufacturerEditText.getText().toString().isEmpty()){
            binding.manufacturerEditText.setError("Musisz uzupełnić");
            available =false;
        }

        if(binding.modelEditText.getText().toString().isEmpty()){
            binding.modelEditText.setError("Musisz uzupełnić");
            available =false;
        }

        if(binding.androidEditText.getText().toString().isEmpty()){
            binding.androidEditText.setError("Musisz uzupełnić");
            available =false;
        }

        if(binding.webSiteEditText.getText().toString().isEmpty()){
            binding.webSiteEditText.setError("Musisz uzupełnić");
            available =false;
        }

        binding.saveButton.setOnClickListener(view1 -> {
            if (available) {
                String manufacture = binding.manufacturerEditText.getText().toString();
                String model = binding.modelEditText.getText().toString();
                String android = binding.androidEditText.getText().toString();
                String web = binding.webSiteEditText.getText().toString();
                Phone phone = new Phone(id, manufacture, model, android, web);
                if (id == null) {
                    phoneViewModel.addPhone(phone);
                } else {
                    phoneViewModel.updatePhone(phone);
                }
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_phoneFragment);
            }
            else {
                Toast toast = Toast.makeText(view.getContext(), "Musisz uzupełnić wszystkie pola", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        binding.cancelButton.setOnClickListener(view1 -> {
            id = null;
            binding.manufacturerEditText.setText("");
            binding.modelEditText.setText("");
            binding.androidEditText.setText("");
            binding.webSiteEditText.setText("");
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_phoneFragment);
        });

        binding.webSiteButton.setOnClickListener(view1 -> {
            Intent zamiarPrzegladarki = new Intent("android.intent.action.VIEW", Uri.parse(binding.webSiteEditText.getText().toString()));
            startActivity(zamiarPrzegladarki);
        });


        binding.manufacturerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    binding.manufacturerEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    binding.manufacturerEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }

            }
        });

        binding.modelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    binding.modelEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    binding.modelEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }
            }
        });

        binding.androidEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    binding.androidEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    binding.androidTextLabel.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }
            }
        });

        binding.webSiteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    binding.webSiteEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    binding.webSiteEditText.setError("Musisz uzupełnić");
                    available = false;
                }
                else {
                    available = true;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}