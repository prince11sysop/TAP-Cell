package com.nitjamshedpur.tapcell.Placements;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjamshedpur.tapcell.Internships.InternshipMainActivity;
import com.nitjamshedpur.tapcell.R;

public class ProfileMainActivity extends AppCompatActivity {

    TextView mStudName,mBranch,mCompanyName,mJobTitle,mInternCompany;
    TextView mProjectDesc,mOnlineRound,mTechRound,mHrRound,mInterviewMode,mInterviewDifficulty,mWordsToJr;
    private ImageView mImageView;
    private Button mStudentLinkedIn,mStudentPhone,mStudentWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mImageView=(ImageView)findViewById(R.id.photoUrl);
        mStudName=(TextView)findViewById(R.id.studName);
        mStudentLinkedIn=(Button) findViewById(R.id.linkedInId);
        mStudentPhone=(Button) findViewById(R.id.phoneNum);
        mBranch=(TextView)findViewById(R.id.branch);
        mCompanyName=(TextView)findViewById(R.id.companyName);
        mJobTitle=(TextView)findViewById(R.id.jobTitle);
        mInternCompany=(TextView)findViewById(R.id.internCompany);
        mProjectDesc=(TextView)findViewById(R.id.projectDesc);
        mOnlineRound=(TextView)findViewById(R.id.onlineRound);
        mTechRound=(TextView)findViewById(R.id.techRound);
        mHrRound=(TextView)findViewById(R.id.hrRound);
        mInterviewDifficulty=(TextView)findViewById(R.id.interviewDifficulty);
        mInterviewMode=(TextView)findViewById(R.id.interviewMode);
        mWordsToJr=(TextView)findViewById(R.id.wordsToJr);
        mStudentWhatsapp=(Button)findViewById(R.id.whatsappNum);

        Glide.with(getApplicationContext())
                .load(PlacementMainActivity.ViewPagerFragment.image)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .fitCenter())
                .into(mImageView);
        mStudName.setText(PlacementMainActivity.ViewPagerFragment.studName);
        mStudentPhone.setText(PlacementMainActivity.ViewPagerFragment.whatsappNum);
        mBranch.setText(PlacementMainActivity.ViewPagerFragment.branchName);
        mCompanyName.setText(PlacementMainActivity.ViewPagerFragment.companyName);
        mJobTitle.setText(PlacementMainActivity.ViewPagerFragment.jobTitle);
        mInternCompany.setText(PlacementMainActivity.ViewPagerFragment.internCompany);
        mProjectDesc.setText(PlacementMainActivity.ViewPagerFragment.projectDesc);
        mOnlineRound.setText(PlacementMainActivity.ViewPagerFragment.onlineRound);
        mTechRound.setText(PlacementMainActivity.ViewPagerFragment.techRound);
        mHrRound.setText(PlacementMainActivity.ViewPagerFragment.hrRound);
        mWordsToJr.setText(PlacementMainActivity.ViewPagerFragment.wordsToJr);
        mInterviewMode.setText(PlacementMainActivity.ViewPagerFragment.interviewMode);
        mInterviewDifficulty.setText(PlacementMainActivity.ViewPagerFragment.interviewDifficulty);
        mStudentLinkedIn.setText(PlacementMainActivity.ViewPagerFragment.linkedInId);

        String whatsappUrl = "https://api.whatsapp.com/send?phone="+"91"+PlacementMainActivity.ViewPagerFragment.whatsappNum;
        mStudentWhatsapp.setText(whatsappUrl);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
