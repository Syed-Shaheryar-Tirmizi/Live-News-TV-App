package com.syed.ary_news;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseHelper
{

    DatabaseReference liveVideoReference = FirebaseDatabase.getInstance().getReference().child("aryVideoId");



    public void getLiveVideoId(final OnVideoIdLoadedListener listener) {

        liveVideoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String newVideoId=dataSnapshot.getValue().toString();


                listener.onLoaded(newVideoId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onLoaded(null);
            }
        });
    }
}

interface OnVideoIdLoadedListener {
    public void onLoaded(String newVideoId);
}
