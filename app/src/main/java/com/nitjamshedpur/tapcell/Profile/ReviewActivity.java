package com.nitjamshedpur.tapcell.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import  com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitjamshedpur.tapcell.MainActivity;
import com.nitjamshedpur.tapcell.R;


public class ReviewActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private TextInputLayout inputLayoutName, inputLayoutWhatsapp, inputLayoutLinkedIn,inputLayoutCompany,inputLayoutJob;
    private TextInputLayout inputLayoutIntern,inputLayoutProjet,inputLayoutOnline,inputLayoutTech,inputLayoutHr,inputLayoutTips;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    String[] branches = {"-SELECT-","CSE", "ECE", "Mechanical", "Electrical", "Civil","MME","Production","MCA","M.Tech"};
    String[] difficulty={"-SELECT-","Very Easy","Easy","Average","Difficult","Very Difficult"};
    String interviewMode[]={"-SELECT-","College","Applied Online","Referrral"};

    private int mCnfStatus=0;
    private EditText mStudName,mWhatsappNum,mLinkedInId;
    private Switch mIsInternshipReview;
    private EditText mCompanyName,mJobTitle,mInternCompany,mProjectDesc,mOnlineRound,mTechRound,mHrRound,mWordsToJr;
    private Spinner mBranchSpin,mInterviewModeSpin,mInterviewDifficultySpin;
    private ImageButton mPhotoPickerButton;
    private Button mSendButton;
    private DatabaseReference mMessagesDatabaseReference;
    String branchRes,diffRes,modeRes;
    boolean isInternshipReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Review");

        // Initialize references to views
        mStudName = (EditText) findViewById(R.id.studName);
        mBranchSpin=(Spinner) findViewById(R.id.branch);
        mWhatsappNum=(EditText)findViewById(R.id.whatsappNum);
        mLinkedInId=(EditText)findViewById(R.id.linkedInId);
        mIsInternshipReview=(Switch)findViewById(R.id.isInternshipReview);
        mCompanyName = (EditText) findViewById(R.id.companyName);
        mJobTitle = (EditText) findViewById(R.id.jobTitle);
        mInternCompany=(EditText)findViewById(R.id.internCompany);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mProjectDesc=(EditText)findViewById(R.id.projectDesc);
        mOnlineRound=(EditText)findViewById(R.id.onlineRound);
        mTechRound=(EditText)findViewById(R.id.techRound);
        mHrRound=(EditText)findViewById(R.id.hrRound);
        mInterviewModeSpin=(Spinner)findViewById(R.id.interviewMode);
        mInterviewDifficultySpin=(Spinner)findViewById(R.id.interviewDifficulty);
        mWordsToJr=(EditText)findViewById(R.id.wordsToJr);
        mSendButton = (Button) findViewById(R.id.sendButton);



        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutWhatsapp = (TextInputLayout) findViewById(R.id.input_layout_whatsapp);
        inputLayoutLinkedIn = (TextInputLayout) findViewById(R.id.input_layout_linkedin);
        inputLayoutCompany= (TextInputLayout) findViewById(R.id.input_layout_company);
        inputLayoutJob= (TextInputLayout) findViewById(R.id.input_layout_job);
        inputLayoutIntern = (TextInputLayout) findViewById(R.id.input_layout_intern);
        inputLayoutProjet = (TextInputLayout) findViewById(R.id.input_layout_project);
        inputLayoutHr = (TextInputLayout) findViewById(R.id.input_layout_hr);
        inputLayoutTech= (TextInputLayout) findViewById(R.id.input_layout_tech);
        inputLayoutTips= (TextInputLayout) findViewById(R.id.input_layout_tips);
        inputLayoutOnline= (TextInputLayout) findViewById(R.id.input_layout_online);


        mStudName.addTextChangedListener(new MyTextWatcher(mStudName));
        mWhatsappNum.addTextChangedListener(new MyTextWatcher(mWhatsappNum));
        mLinkedInId.addTextChangedListener(new MyTextWatcher(mLinkedInId));
        mCompanyName.addTextChangedListener(new MyTextWatcher(mCompanyName));
        mJobTitle.addTextChangedListener(new MyTextWatcher(mJobTitle));
        mInternCompany.addTextChangedListener(new MyTextWatcher(mInternCompany));
        mProjectDesc.addTextChangedListener(new MyTextWatcher(mProjectDesc));
        mOnlineRound.addTextChangedListener(new MyTextWatcher(mOnlineRound));
        mTechRound.addTextChangedListener(new MyTextWatcher(mTechRound));
        mHrRound.addTextChangedListener(new MyTextWatcher(mHrRound));
        mOnlineRound.addTextChangedListener(new MyTextWatcher(mOnlineRound));

        //checking switch status
        mIsInternshipReview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isInternshipReview=true;
                else
                    isInternshipReview=false;
            }
        });

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker

                Intent intent=new Intent(getApplicationContext(),UploadImage.class);
                startActivity(intent);
            }
        });


        // Enable Send button when there's text to send
    /*   mStudName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mStudName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});*/

        // Send button sends a Review and clears all the input parameters
        mSendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //submitForm();//to check validity of inputs

                String name,whatsappNum,linkedInId,company,jobTitle,internCompany,photoUrl,
                        projectDesc,onlinRound,techRound,hrRound,wordsToJr;

                name=mStudName.getText().toString();
                whatsappNum=mWhatsappNum.getText().toString();
                linkedInId=mLinkedInId.getText().toString();
                company=mCompanyName.getText().toString();
                jobTitle=mJobTitle.getText().toString();
                internCompany=mInternCompany.getText().toString();
                photoUrl=UploadImage.imageUrl;
                projectDesc=mProjectDesc.getText().toString();
                onlinRound=mOnlineRound.getText().toString();
                techRound=mTechRound.getText().toString();
                hrRound=mHrRound.getText().toString();
                wordsToJr=mWordsToJr.getText().toString();

                int flag=0;
                if(name.length()==0)
                    validateName();
                else if(company.length()==0)
                    validateCompanyName();
               else if(jobTitle.length()==0)
                    validateJobTitle();
               else if(onlinRound.length()==0)
                   validateOnline();
               else if(techRound.length()==0)
                   validateTech();
               else if(hrRound.length()==0)
                   validateHr();
               else flag=1;

               if(flag==1) {
                   if (isInternshipReview == true)
                       mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Internships");
                   else
                       mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child(branchRes);

                   String childKey = mMessagesDatabaseReference.push().getKey();

                   Review review = new Review(mCnfStatus, name, branchRes, whatsappNum, linkedInId,
                           isInternshipReview, company, jobTitle, internCompany,
                           photoUrl, projectDesc, onlinRound, techRound, hrRound, modeRes, diffRes, wordsToJr, childKey);

                   mMessagesDatabaseReference.child(childKey).setValue(review);

                   Toast.makeText(getApplicationContext(), "Experience posted successfully!", Toast.LENGTH_LONG).show();

                   mStudName.setText("");
                   mWhatsappNum.setText("");
                   mLinkedInId.setText("");
                   mCompanyName.setText("");
                   mJobTitle.setText("");
                   mInternCompany.setText("");
                   mProjectDesc.setText("");
                   mOnlineRound.setText("");
                   mTechRound.setText("");
                   mHrRound.setText("");
                   mWordsToJr.setText("");
                   UploadImage.imageUrl=null;
                   startActivity(new Intent(ReviewActivity.this,MainActivity.class));
               }else{
                   submitForm();
               }
            }
        });

        //Getting the instance of Spinner and applying OnItemSelectedListener on it


        mBranchSpin.setOnItemSelectedListener(this);
        mInterviewDifficultySpin.setOnItemSelectedListener(this);
        mInterviewModeSpin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter branchAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,branches);
        ArrayAdapter difficultyAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,difficulty);
        ArrayAdapter interviewModeAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,interviewMode);

        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        interviewModeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        mBranchSpin.setAdapter(branchAdapter);
        mInterviewDifficultySpin.setAdapter(difficultyAdapter);
        mInterviewModeSpin.setAdapter(interviewModeAdapter);


    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {

        if(parent.getId()==R.id.branch){
            branchRes=branches[position];
            //Toast.makeText(getApplicationContext(),branches[position] , Toast.LENGTH_LONG).show();
        }
        else if (parent.getId()==R.id.interviewDifficulty){
            diffRes=difficulty[position];
           // Toast.makeText(getApplicationContext(),difficulty[position] , Toast.LENGTH_LONG).show();
        }else if(parent.getId()==R.id.interviewMode){
            modeRes=interviewMode[position];
           // Toast.makeText(getApplicationContext(),modeRes , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if(!validateCompanyName()){
            return;
        }

        if(!validateJobTitle()){
            return;
        }

        if (!validateOnline()) {
            return;
        }

        if (!validateTech()) {
            return;
        }

        if(!validateHr()){
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (mStudName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(mStudName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateOnline() {
        String online = mOnlineRound.getText().toString().trim();

        if (online.isEmpty()) {
            inputLayoutOnline.setError(getString(R.string.err_msg_email));
            requestFocus(mOnlineRound);
            return false;
        } else {
            inputLayoutOnline.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTech() {
        if (mTechRound.getText().toString().trim().isEmpty()) {
            inputLayoutTech.setError(getString(R.string.err_msg_password));
            requestFocus(mTechRound);
            return false;
        } else {
            inputLayoutTech.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateCompanyName() {
        if (mCompanyName.getText().toString().trim().isEmpty()) {
            inputLayoutCompany.setError(getString(R.string.err_msg_password));
            requestFocus(mCompanyName);
            return false;
        } else {
            inputLayoutCompany.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateJobTitle() {
        if (mJobTitle.getText().toString().trim().isEmpty()) {
            inputLayoutJob.setError(getString(R.string.err_msg_password));
            requestFocus(mJobTitle);
            return false;
        } else {
            inputLayoutJob.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateHr() {
        if (mHrRound.getText().toString().trim().isEmpty()) {
            inputLayoutHr.setError(getString(R.string.err_msg_password));
            requestFocus(mHrRound);
            return false;
        } else {
            inputLayoutHr.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.studName:
                    validateName();
                    break;
                case R.id.companyName:
                    validateCompanyName();
                    break;
                case R.id.jobTitle:
                    validateJobTitle();
                    break;
                case R.id.onlineRound:
                    validateOnline();
                    break;
                case R.id.techRound:
                    validateTech();
                    break;
                case R.id.hrRound:
                    validateHr();
                    break;
            }
        }
    }


}

