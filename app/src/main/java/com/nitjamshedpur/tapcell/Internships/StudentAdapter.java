package com.nitjamshedpur.tapcell.Internships;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nitjamshedpur.tapcell.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentAdapter extends ArrayAdapter<Student> {

    List<Student> modellist;
    ArrayList<Student> arrayList;


    public StudentAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.student_list_format, parent, false);
        }

        ImageView photoImageView =  convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        Student student = getItem(position);

            messageTextView.setText(student.getCompanyName());
            Glide.with(photoImageView.getContext())
                    .load(student.getPhotoUrl())
                    .into(photoImageView);

        authorTextView.setText(student.getStudName());

        return convertView;

    }


    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }
        else {
            for (Student model : arrayList){
                if ((model.getStudName().toLowerCase(Locale.getDefault()).contains(charText)) ||
                        (model.getCompanyName().toLowerCase(Locale.getDefault()).contains(charText))  ){
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

}

