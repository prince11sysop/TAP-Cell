package com.nitjamshedpur.tapcell.Placements;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nitjamshedpur.tapcell.R;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    static  int cnfStatus;
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
        cnfStatus=student.getCnfStatus();

        return convertView;
    }




}

