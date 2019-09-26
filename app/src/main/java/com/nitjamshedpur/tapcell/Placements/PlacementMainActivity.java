package com.nitjamshedpur.tapcell.Placements;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitjamshedpur.tapcell.Profile.Review;
import com.nitjamshedpur.tapcell.R;

import java.util.ArrayList;
import java.util.List;

//Main Activity of Department model
public class PlacementMainActivity extends AppCompatActivity {

    static ViewPager mViewPager;
    static List<Placement> placementList;
    RecyclerView recyclerView;
    static GridLayoutManager layoutManager;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_main);

        setTitle("Placements");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dialog = new ProgressDialog(PlacementMainActivity.this);
        dialog.setMessage("Please Wait...");
        dialog.setTitle("Loading");
        dialog.setCancelable(false);


        //for list of departments
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        recyclerView.setHasFixedSize(true);
        placementList = new ArrayList<>();

        //adding departments to the list
        placementList.add(new Placement("CSE", "https://firebasestorage.googleapis.com/v0/b/tap-cell-c23d1.appspot.com/o/computer.jpg?alt=media&token=8fad9950-31fb-43ff-a2b3-87d115af442b"));
        placementList.add(new Placement("ECE", "https://firebasestorage.googleapis.com/v0/b/tap-cell-c23d1.appspot.com/o/electronics.jpg?alt=media&token=1ac402fd-0a87-41ba-a1dd-7a2dbb17410e"));
        placementList.add(new Placement("Mechanical", "https://firebasestorage.googleapis.com/v0/b/tap-cell-c23d1.appspot.com/o/mechanical.jpg?alt=media&token=14eda005-3ff8-49fe-b504-a4ef744b38f1"));
        placementList.add(new Placement("EEE", "https://firebasestorage.googleapis.com/v0/b/tap-cell-c23d1.appspot.com/o/electrical.jpg?alt=media&token=2e86ce83-a24c-4b3e-828a-10d09ea78c2e"));
        placementList.add(new Placement("Civil", "https://firebasestorage.googleapis.com/v0/b/tap-cell-c23d1.appspot.com/o/civil.jpg?alt=media&token=cb1d7d92-0eba-4cd3-bb79-d98ba3f802a3"));
        placementList.add(new Placement("MME", "https://images.pexels.com/photos/112460/pexels-photo-112460.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        placementList.add(new Placement("PIE", "http://im.rediff.com/money/2017/dec/07car1.jpg"));
        placementList.add(new Placement("MCA", "https://www.popsci.com/sites/popsci.com/files/styles/1000_1x_/public/images/2018/03/senna.jpg?itok=eYNPMGjA&fc=50,50"));
        placementList.add(new Placement("M.Tech", "https://cdn1.autoexpress.co.uk/sites/autoexpressuk/files/styles/article_main_image/public/2018/10/bmw_i4_frontquarter_dark_copy.jpg?itok=Q2kxDpKo"));


        PlacementAdapter placementAdapter = new PlacementAdapter(this, placementList);
        recyclerView.setAdapter(placementAdapter);

        // adapter that will return a fragment for each of the 16
        ViewPagerFragmentAdapter mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mViewPagerFragmentAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                layoutManager.scrollToPositionWithOffset(position, 0);
            }
            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            // Called when the scroll state changes:
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static class ViewPagerFragment extends Fragment {


        DatabaseReference mMessagesDatabaseReference;
        ChildEventListener mChildEventListener=null;
        ListView mMessageListView;
        StudentAdapter mMessageAdapter;
        String branch;
        protected static String studName,branchName,whatsappNum,linkedInId,companyName,jobTitle,internCompany,projectDesc;
        protected static String onlineRound,techRound,hrRound,interviewMode,interviewDifficulty,wordsToJr,childKey;
        boolean isInternshipReview;
        int cnfStatus;
        static String image;
        List<Review> studentDetails=new ArrayList<>();
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ViewPagerFragment() {
        }

        public static ViewPagerFragment newInstance(int sectionNumber) {
            ViewPagerFragment fragment = new ViewPagerFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_member, container, false);

            //for display all the students in particular branch adapter from top horizontal fragment
           switch (getArguments().getInt(ARG_SECTION_NUMBER)){
               case 0:
                   branch="CSE";
                   break;
               case 1:
                   branch="ECE";
                   break;
               case 2:
                   branch="Mechanical";
                   break;
               case 3:
                   branch="EEE";
                   break;
               case 4:
                   branch="Civil";
                   break;
               case 5:
                   branch="MME";
                   break;
               case 6:
                   branch="Production";
                   break;
               case 7:
                   branch="MCA";
                   break;
               case 8:
                   branch="MTech";
                   break;
           }

            mMessagesDatabaseReference= FirebaseDatabase.getInstance().getReference().child(branch);
            mMessageListView = (ListView) rootView.findViewById(R.id.messageListView);

           final  List<Student> friendlyMessages = new ArrayList<>();

            mMessageAdapter = new StudentAdapter(getContext(), R.layout.student_list_format, friendlyMessages);
            mMessageListView.setAdapter(mMessageAdapter);

            attachDatabaseReadListener();

           //adding click listener on list view item
            mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Review studentReview=studentDetails.get(position);

                    //sending details to ProfileMainActivity
                    cnfStatus=studentReview.getCnfStatus();
                    studName=studentReview.getStudName();
                    branchName=studentReview.getBranch();
                    whatsappNum=studentReview.getWhatsappNum();
                    linkedInId=studentReview.getLinkedInId();
                    isInternshipReview=studentReview.getInternOrJob();
                    companyName=studentReview.getCompanyName();
                    jobTitle=studentReview.getJobTitle();
                    internCompany=studentReview.getInternCompany();
                    image=studentReview.getPhotoUrl();
                    projectDesc=studentReview.getProjectDesc();
                    onlineRound=studentReview.getOnlineRound();
                    techRound=studentReview.getTechRound();
                    hrRound=studentReview.getHrRound();
                    interviewMode=studentReview.getInterviewMode();
                    interviewDifficulty=studentReview.getInterviewDifficulty();
                    wordsToJr=studentReview.getWordsToJr();

                    Intent intent=new Intent(getActivity(),ProfileMainActivity.class);
                    getContext().startActivity(intent);
                }
            });
            return rootView;
        }

        private  void attachDatabaseReadListener()
        {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    //storing  all the details to student in studentDetails arrayList
                    Review review=dataSnapshot.getValue(Review.class);
                    Student friendlyMessage = dataSnapshot.getValue(Student.class);


                    if(review.getCnfStatus()==1 && friendlyMessage.getCnfStatus()==1){
                        studentDetails.add(review);
                        mMessageAdapter.add(friendlyMessage);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Student friendlyMessage = dataSnapshot.getValue(Student.class);
                    mMessageAdapter.add(friendlyMessage);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Student friendlyMessage = dataSnapshot.getValue(Student.class);
                    mMessageAdapter.add(friendlyMessage);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Student friendlyMessage = dataSnapshot.getValue(Student.class);
                    mMessageAdapter.add(friendlyMessage);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }

        private  void detachDatabaseReadListener(){
            if(mChildEventListener!=null) {
                mMessagesDatabaseReference.removeEventListener(mChildEventListener);
                mChildEventListener=null;
            }
        }

    }

    public  class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

        public ViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return ViewPagerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 9; // 9 total pages.
        }
    }

    //for navigating back
    @Override
    public void onBackPressed() {
        finish();
    }



}
