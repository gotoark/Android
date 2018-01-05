package application.arkthepro.com.retrofitexample.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import application.arkthepro.com.retrofitexample.R;
import application.arkthepro.com.retrofitexample.data.model.Items;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {


    private List<Items> items;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleTv;
        PostItemListener mItemListener;

        public ViewHolder(View itemView,PostItemListener mItemListener) {
            super(itemView);
            this.titleTv=(TextView) itemView.findViewById(R.id.tv_list_items);
            this.mItemListener = mItemListener;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public AnswerAdapter(List<Items> items, Context mContext, PostItemListener mItemListener) {
        this.items = items;
        this.mContext = mContext;
        this.mItemListener = mItemListener;
    }

    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        //View postView=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        View postView=inflater.inflate(R.layout.rv_list_items,parent,false);
        ViewHolder viewHolder=new ViewHolder(postView,this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Items item=items.get(position);
        TextView textView=holder.titleTv;
        textView.setText(item.getOwner().getDisplayName());
    }

    public void updateAnswer(List<Items> items){
        this.items=items;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    private Items getItem(int adpterPosition) {
        return items.get(adpterPosition);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public interface PostItemListener {
        void onPostClick(long id);
    }

}