package com.nitjamshedpur.tapcell.Placements;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nitjamshedpur.tapcell.R;

import java.util.List;


public class PlacementAdapter extends RecyclerView.Adapter<PlacementAdapter.PlacementViewHolder> {

    //this context will be used to inflate the layout
    private Context myContext;
    public static int placementPosition = 1;

    //creating list to store all ojass departments
    private List<Placement> placementList;

    //getting the context and ojass department list with constructor
    public PlacementAdapter(Context myContext, List<Placement> placementList) {
        this.myContext = myContext;
        this.placementList = placementList;
    }

    @NonNull
    @Override
    public PlacementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        //inflating and returning our view holder
        LayoutInflater layoutInflater = LayoutInflater.from(myContext);
        View view = layoutInflater.inflate(R.layout.branch_card_format, null);

        return new PlacementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacementViewHolder placementViewHolder, int position) {

        //getting the contents of the department at a specified position
        Placement placement = placementList.get(position);

        //binding the data with view holder
        placementViewHolder.branchName.setText(placement.getBranchName());

        //loading image to Cardview from URI using glide
        Glide.with(myContext)
                .load(placement.getBranchImage())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(placementViewHolder.branchImage);

    }

    @Override
    public int getItemCount() {
        return placementList.size();
    }


    public static class PlacementViewHolder extends RecyclerView.ViewHolder {

        ImageView branchImage;
        TextView branchName;

        public PlacementViewHolder(@NonNull final View itemView) {
            super(itemView);
            branchImage = itemView.findViewById(R.id.branchImageView);
            branchName = itemView.findViewById(R.id.branchTextView);

            //to get the position of recycler viw
            itemView.setTag(getAdapterPosition());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    placementPosition = getAdapterPosition();
                    PlacementMainActivity.mViewPager.setCurrentItem(placementPosition);

                    // scrolling the cardview at first position
                    PlacementMainActivity.layoutManager.scrollToPositionWithOffset(placementPosition, 0);

                }
            });
        }
    }
}