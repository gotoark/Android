package thinkers.com.marvin.Screens.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thinkers.com.marvin.R;
import thinkers.com.marvin.Screens.Modal.Entertainment.Result;
import thinkers.com.marvin.Screens.Modal.Issue;




public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {


    private List<Result> issues;
    private Context mContext;
    private PostItemListener mItemListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        //View postView=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        View postView=inflater.inflate(R.layout.list_item_movies,parent,false);
        ViewHolder viewHolder=new ViewHolder(postView,this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result item=issues.get(position);
        TextView textTitle=holder.titleTv;
        textTitle.setText(item.getDisplayTitle());
        TextView textDate=holder.dateTv;
        textDate.setText(item.getByline());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleTv;
        public TextView dateTv;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener mItemListener) {
            super(itemView);
            this.titleTv=(TextView) itemView.findViewById(R.id.tv_issue_title);
            this.dateTv=(TextView)itemView.findViewById(R.id.tv_issue_date);
            this.mItemListener = mItemListener;
        }


        @Override
        public void onClick(View view) {

        }
    }

    public MoviesAdapter(List<Result> issues, Context mContext, PostItemListener mItemListener) {
        this.issues = issues;
        this.mContext = mContext;
        this.mItemListener = mItemListener;
    }
    public void updateAnswer(List<Result> issues){
        this.issues=issues;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    /*private Issue getItem(int adpterPosition) {
        return issues.get(adpterPosition);
    }*/

    @Override
    public int getItemCount() {
        return issues.size();
    }



    public interface PostItemListener {
        void onPostClick(long id);
    }

}
