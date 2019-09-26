package com.nitjamshedpur.tapcell.Internships;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjamshedpur.tapcell.Internships.Student;
import com.nitjamshedpur.tapcell.Internships.StudentAdapter;
import com.nitjamshedpur.tapcell.Profile.Review;
import com.nitjamshedpur.tapcell.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InternshipMainActivity extends AppCompatActivity {

    DatabaseReference mMessagesDatabaseReference;
    ListView mMessageListView;
    StudentAdapter mMessageAdapter;
    //public static String name,email,contact,branchName,company,job,apt,logical,prog,gd,technical,hr,que,diff;
   public  List<Student> friendlyMessages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Internships");

   /*     String firebaseRef[]=new String[9];
        firebaseRef[0]="CSE";
        firebaseRef[1]="ECE";
        firebaseRef[2]="Mechanical";
        firebaseRef[3]="EEE";
        firebaseRef[4]="Civil";
        firebaseRef[5]="MME";
        firebaseRef[6]="Production";
        firebaseRef[7]="MCA";
        firebaseRef[8]="MTech";
*/

        mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Internships");
        mMessageListView = (ListView) findViewById(R.id.messageListView);

        attachDatabaseReadListener();

        mMessageAdapter = new StudentAdapter(this, R.layout.student_list_format, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        //reading complete database
      /*  for(int i=0;i<9;i++) {
            mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child(firebaseRef[i]);
            attachDatabaseReadListener();
        }*/
        //adding click listener
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Student stud=friendlyMessages.get(position);
                Toast.makeText(getApplicationContext(), stud.getStudName(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  void attachDatabaseReadListener()
    {
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Student friendlyMessage = dataSnapshot.getValue(Student.class);
                    mMessageAdapter.add(friendlyMessage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    mMessageAdapter.filter("");
                    mMessageListView.clearTextFilter();
                }
                else {
                    mMessageAdapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings){
            //do your functionality here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
