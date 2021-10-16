package com.example.myrecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecyclerview.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvContacts = binding.rvContacts;
        TextView textView = binding.textviewFirst;

        // Initialize contacts
        ArrayList<Contact> contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        CustomAdapter adapter = new CustomAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContacts.setLayoutManager(llm);


        rvContacts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

//                if (llm.findLastVisibleItemPosition() >10) {
//                    Log.e("position", "" + llm.findLastVisibleItemPosition());
//                }
//                Log.e("findLastVisible", "" + llm.findLastVisibleItemPosition());
//                Log.e("findLastCompletelyVisib", "" + llm.findLastCompletelyVisibleItemPosition());
//                Log.e("findFirstVisible", "" + llm.findFirstVisibleItemPosition());
//                Log.e("findFirstCompletelyVisi", "" + llm.findFirstCompletelyVisibleItemPosition());

                //Number of completly visible items are:
                int firstItemPosition = llm.findFirstCompletelyVisibleItemPosition();
                int lastItemPosition = llm.findLastCompletelyVisibleItemPosition();
                StringBuilder sb = new StringBuilder();
                for(int i = firstItemPosition; i<=lastItemPosition; i++)
                    sb.append(contacts.get(i).getName()+ " ");

//                Snackbar.make(view, "Visible contacts are: "+ sb.toString(), Snackbar.LENGTH_SHORT)
////                        .setAction("Action", null).show();
                textView.setText("Completely visible:"+sb.toString());
                Log.e("Visible contacts are:", sb.toString());

                if (llm.findLastVisibleItemPosition() == adapter.getItemCount() - 5) {
                    ArrayList<Contact> newdata = Contact.createContactsList(20);
                    contacts.addAll(contacts.size(), newdata);
                    adapter.notifyItemRangeInserted(contacts.size(), newdata.size());
                    newdata.clear();
                    Log.e("New Data Added", "To the RecyclerView");
                }


                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}