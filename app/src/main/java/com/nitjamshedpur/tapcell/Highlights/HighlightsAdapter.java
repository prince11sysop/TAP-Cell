package com.nitjamshedpur.tapcell.Highlights;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjamshedpur.tapcell.Internships.InternshipMainActivity;
import com.nitjamshedpur.tapcell.R;

import java.net.URI;
import java.util.List;

import static com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler.TAG;

public class HighlightsAdapter extends RecyclerView.Adapter<HighlightsAdapter.HighlightsViewHolder> {

    //this context will be used to inflate the layout
    private Context myContext;
    private String fullScreenInd;

    //creating list to store all ojass departments
    private List<Highlights> placementList;

    //getting the context and ojass department list with constructor
    public HighlightsAdapter(Context myContext, List<Highlights> placementList){
        this.myContext = myContext;
        this.placementList = placementList;
    }

    @NonNull
    @Override
    public HighlightsAdapter.HighlightsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //inflating and returning our view holder
        LayoutInflater layoutInflater = LayoutInflater.from(myContext);
        View view = layoutInflater.inflate(R.layout.highlights_card_format, null);

        return new HighlightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HighlightsViewHolder placementViewHolder, final int position) {

        //getting the contents of the department at a specified position
        final Highlights placement = placementList.get(position);

        //binding the data with view holder
        placementViewHolder.mTitle.setText(placement.getTitle());
        placementViewHolder.mDescription.setText(placement.getDescription());
        placementViewHolder.mLink.setText(placement.getLink());

        //loading image to Cardview from URI using glide
        Glide.with(myContext)
                .load(placement.getImage())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(placementViewHolder.mImage);


        placementViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(myContext,FullScreenImageActivity.class);
                intent.putExtra("Thumbnail",placementList.get(position).getImage());
                myContext.startActivity(intent);

               // ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(myContext,placementViewHolder.mImage,"cardTransition");
            }
        });
    }


    @Override
    public int getItemCount() {
        return placementList.size();
    }


    public static class HighlightsViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mTitle,mDescription,mLink;


        public HighlightsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.highlightsImage);
            mTitle = itemView.findViewById(R.id.highlightsTitle);
            mDescription = itemView.findViewById(R.id.highlightsDescription);
            mLink= itemView.findViewById(R.id.highlightsLink);

           // mImage.setOnTouchListener(new ImageMatrixTouchHandler(itemView.getContext())); to pinch nd zoom image
            //to get the position of recycler viw
            itemView.setTag(getAdapterPosition());

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}