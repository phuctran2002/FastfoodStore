package com.example.fastfoodstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomePage extends Fragment {

    private RecyclerView homePageRecyclerView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("All_Cakes");

    private FirebaseRecyclerOptions<Cake> options;
    private FirebaseRecyclerAdapter<Cake, CakeViewHolder> adapter;
    private CakePage cakePage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        cakePage = new CakePage();
        homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);
        homePageRecyclerView.setHasFixedSize(true);
        homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        options = new FirebaseRecyclerOptions.Builder<Cake>().setQuery(reference, Cake.class).build();
        adapter = new FirebaseRecyclerAdapter<Cake, CakeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CakeViewHolder holder, int position, @NonNull final Cake model) {
                holder.cakeName.setText(model.getName());
                holder.cakePrice.setText(model.getCakePrice());
                holder.cakeQuantity.setText(model.getCakeQuantity());
                //              holder.cakeDescription.setText(model.getCakeDescription());
                Picasso.get().load(model.getImgUri()).into(holder.cakeImage);

                holder.itemCard.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("cakeImage", model.getImgUri());
                        bundle.putString("cakeName", model.getName());
                        bundle.putString("cakePrice", model.getCakePrice());
                        bundle.putString("cakeQuantity", model.getCakeQuantity());
                        bundle.putString("cakeDescription", model.getCakeDescription());
                        cakePage.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frame, cakePage);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }



            @NonNull
            @Override
            public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cake_item, parent, false);
                return new CakeViewHolder(v);
            }
        };
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        homePageRecyclerView.setLayoutManager(gridLayoutManager);
        homePageRecyclerView.setAdapter(adapter);
        return view;
    }
}