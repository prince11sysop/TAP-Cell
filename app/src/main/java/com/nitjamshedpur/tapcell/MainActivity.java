package com.nitjamshedpur.tapcell;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitjamshedpur.tapcell.About.AboutMainActivity;
import com.nitjamshedpur.tapcell.Carousel.SliderAdapter;
import com.nitjamshedpur.tapcell.GoogleAuthentication.GoogleSignInActivity;
import com.nitjamshedpur.tapcell.Highlights.HighlightsMainActivity;
import com.nitjamshedpur.tapcell.Internships.InternshipMainActivity;
import com.nitjamshedpur.tapcell.PastStatistics.PastStatistics;
import com.nitjamshedpur.tapcell.Placements.PlacementMainActivity;
import com.nitjamshedpur.tapcell.Profile.ReviewActivity;
import com.nitjamshedpur.tapcell.Recruiters.RecruitersMainActivity;
import com.nitjamshedpur.tapcell.Statistics.BarGraphActivity;
import com.nitjamshedpur.tapcell.Team.TeamMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView androidGridView;
    String[] gridViewString = {
            "Placements", "Internships", "Recruiters", "Statistics", "Past Stats", "Highlights",
            "About", "TAP Members", "+ Review",};
    int[] gridViewImageId = {
            R.drawable.placements,
            R.drawable.internships,
            R.drawable.company,
            R.drawable.statistics,
            R.drawable.paststats,
            R.drawable.highlights,
            R.drawable.about,
            R.drawable.team,
            R.drawable.profile,
    };

    ViewPager viewPager;
    TabLayout indicator;

    List<String> sliderImages;
    List<String> sliderText;
    private FirebaseAuth mAuth;
    TextView mUserName,mUserEmail,mCurrPlacementYear;
    ImageView mUserImage;
    NavigationView mNavigationView;
    View mHeaderView;
    DatabaseReference mPlacementYearReference; //to get current placement year
    String currPlacementYear;
    String sliderText1,sliderText2,sliderText3;
    String sliderImage1,sliderImage2,sliderImage3;
    DatabaseReference mSliderReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //to get tag line of current placement year
        mCurrPlacementYear=(TextView)findViewById(R.id.placementYear);
        getPlacementYear();

       // updateUserDetails(); //To update email, name and pic in side navigation bar
        gridViewFunction(); //to view gridview and setting on click listener on gridview items
        setCarouselViewPager(); //to implement carousel using viewpager

    }

    private void getPlacementYear() {
        mPlacementYearReference=FirebaseDatabase.getInstance().getReference("PlacementYear");
        mPlacementYearReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currPlacementYear=dataSnapshot.getValue().toString();
                    mCurrPlacementYear.setText(currPlacementYear);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //To update user details in side navigation bar
    public void updateUserDetails(){

        mAuth = FirebaseAuth.getInstance();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // NavigationView Header
        mHeaderView =  mNavigationView.getHeaderView(0);

        // View
        mUserName = (TextView) mHeaderView.findViewById(R.id.userName);
        mUserEmail = (TextView) mHeaderView.findViewById(R.id.userEmail);
        mUserImage = (ImageView) mHeaderView.findViewById(R.id.userImage);

        // Set username & email
        mUserName.setText(mAuth.getCurrentUser().getDisplayName());
        mUserEmail.setText(mAuth.getCurrentUser().getEmail());
        Glide.with(getApplicationContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(mUserImage);

        mNavigationView.setNavigationItemSelectedListener(this);

    }

    //all grid view functionalities
    public void gridViewFunction(){
        //gridview
        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(getApplicationContext(), gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {

                if(i==0){
                    Intent intent = new Intent(getApplicationContext(), PlacementMainActivity.class);
                    startActivity(intent);
                }else if(i==8){
                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    startActivity(intent);
                }else if(i==3){
                    Intent intent = new Intent(getApplicationContext(), BarGraphActivity.class);
                    startActivity(intent);
                }else if(i==4){
                    Intent intent = new Intent(getApplicationContext(), PastStatistics.class);
                    startActivity(intent);
                }else if(i==2){
                    Intent intent = new Intent(getApplicationContext(), RecruitersMainActivity.class);
                    startActivity(intent);
                }else if(i==1){
                    Intent intent = new Intent(getApplicationContext(), InternshipMainActivity.class);
                    startActivity(intent);
                }else if(i==5){
                    Intent intent = new Intent(getApplicationContext(), HighlightsMainActivity.class);
                    startActivity(intent);
                }else if(i==7){
                    Intent intent = new Intent(getApplicationContext(), TeamMainActivity.class);
                    startActivity(intent);
                }else if(i==6){
                    Intent intent = new Intent(getApplicationContext(), AboutMainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();

            }
        });
    }

    //carousel/slider implementation function
    public  void setCarouselViewPager(){

        //carousel
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        indicator=(TabLayout)findViewById(R.id.indicator);

        mSliderReference=FirebaseDatabase.getInstance().getReference().child("Carousel");
        mSliderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SliderImageAndText model=dataSnapshot.getValue(SliderImageAndText.class);
                sliderImage1=model.getImage1();
                sliderImage2=model.getImage2();
                sliderImage3=model.getImage3();
                sliderText1=model.getText1();
                sliderText2=model.getText2();
                sliderText3=model.getText3();

                sliderText = new ArrayList<>();
                sliderText.add(sliderText1);
                sliderText.add(sliderText2);
                sliderText.add(sliderText3);

                sliderImages=new ArrayList<>();
                sliderImages.add(sliderImage1);
                sliderImages.add(sliderImage2);
                sliderImages.add(sliderImage3);

                viewPager.setAdapter(new SliderAdapter(MainActivity.this, sliderImages, sliderText));
                indicator.setupWithViewPager(viewPager, true);

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.menu_logout) {
            Toast.makeText(MainActivity.this, "Signed Out successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //sign out from google function
    /*private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        SharedPrefManager shared = new SharedPrefManager(MainActivity.this);
        shared.setIsLoggedIn(false);
        startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
        finishAffinity();
    }*/


    //cariusel image auto-slider
    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < sliderImages.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
