package be.sanderdebleecker.uselections.mvp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import be.sanderdebleecker.uselections.R;
import be.sanderdebleecker.uselections.mvp.model.view.OfficialVM;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Adapter for Officials
 * Created by simulan on 12/05/2017.
 */
public class OfficialsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final PublishSubject<OfficialVM> onClickOfficial = PublishSubject.create();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Disposable mDisposable;
    private List<OfficialVM> mOfficials;
    Context mContext;

    public OfficialsAdapter (Context context) {
        mOfficials = new ArrayList<>();
        mContext = context;
    }
    public void add(List<OfficialVM> officials) {
        mOfficials = officials;
        notifyDataSetChanged();
    }
    public void clear() {
        mOfficials.clear();
        notifyDataSetChanged();
    }

    //interface RecyclerView.ViewHolder
    private class VHOfficial extends RecyclerView.ViewHolder {
        CircleImageView cimgvOfficialPhoto;
        TextView txtvOfficialName;
        TextView txtvOfficialParty;

        VHOfficial (View itemView) {
            super(itemView);
            cimgvOfficialPhoto = (CircleImageView) itemView.findViewById(R.id.cimgvOfficialPhoto);
            txtvOfficialName = (TextView) itemView.findViewById(R.id.txtvOfficialName);
            txtvOfficialParty = (TextView) itemView.findViewById(R.id.txtvOfficialParty);
        }
    }
    private class VHHeader extends RecyclerView.ViewHolder {
        VHHeader (View itemView) {
            super(itemView);
        }
    }

    //interface RecyclerView
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_official, parent, false);
            return new VHOfficial(v);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_official_header, parent, false);
            return new VHHeader(view);
        }
        throw new RuntimeException("there is no type that matches " + viewType + " +.");
    }
    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHOfficial) {
            final OfficialVM official = getItem(position);
            VHOfficial officialViewHolder = (VHOfficial) holder;
            officialViewHolder.txtvOfficialName.setText(official.getName());
            officialViewHolder.txtvOfficialParty.setText(official.getParty());
            Picasso.with(mContext).load(official.getPhotoUrl()).fit().into(officialViewHolder.cimgvOfficialPhoto);
            officialViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    onClickOfficial.onNext(official);
                }
            });
        }
    }
    @Override
    public int getItemCount () {
        return mOfficials.size()+ 1;
    }
    @Override
    public int getItemViewType (int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    //observable event
    public Observable<OfficialVM> getClickObservable () {
        return onClickOfficial;
    }

    //misc
    private OfficialVM getItem (int pos) {
        return mOfficials.get(pos - 1);
    }
    private boolean isPositionHeader (int position) {
        return position == 0;
    }


}
