package be.sanderdebleecker.uselections.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.sanderdebleecker.uselections.R;
import be.sanderdebleecker.uselections.model.data.envelope.ElectionsEnvelope;
import be.sanderdebleecker.uselections.model.view.ElectionVM;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Adapter for RecyclerView
 * Initialize with List of Election
 * Bind Clicks with RxObservable from public method getClickObservable
 * <p>
 * Created by Sander De Bleecker on 09/05/2017.
 */

//todo connectionFailure snackbar notification
//todo empty list decorator

public class ElectionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Observer<ElectionsEnvelope> {
    private final PublishSubject<ElectionVM> onClickElection = PublishSubject.create();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Disposable disposable;
    private List<ElectionVM> elections;

    public ElectionsAdapter () {
        elections = new ArrayList<>();
    }

    @Override
    public void onSubscribe (Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext (ElectionsEnvelope electionsEnvelope) {
        elections = electionsEnvelope.getElections();
        notifyDataSetChanged();
    }

    @Override
    public void onError (Throwable e) {
        //todo: meaningful error
        System.out.println(e.getMessage());
    }

    @Override
    public void onComplete () {
        disposable.dispose();
    }

    private class VHElection extends RecyclerView.ViewHolder {
        TextView txtvElectionName;
        TextView txtvElectionDay;
        TextView txtvElectionDivision;

        VHElection (View itemView) {
            super(itemView);
            txtvElectionName = (TextView) itemView.findViewById(R.id.txtvElectionName);
            txtvElectionDay = (TextView) itemView.findViewById(R.id.txtvElectionDay);
            txtvElectionDivision = (TextView) itemView.findViewById(R.id.txtvElectionDivision);
        }
    }

    private class VHHeader extends RecyclerView.ViewHolder {
        VHHeader (View itemView) {
            super(itemView);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_election, parent, false);
            return new VHElection(v);
        } else if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_election_header, parent, false);
            return new VHHeader(v);
        }
        throw new RuntimeException("there is no type that matches " + viewType + " +.");
    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHElection) {
            VHElection electionHolder = (VHElection) holder;
            final ElectionVM electionVM = getItem(position);
            electionHolder.txtvElectionName.setText(electionVM.getName());
            electionHolder.txtvElectionDay.setText(electionVM.getElectionDay());
            electionHolder.txtvElectionDivision.setText(electionVM.getOcdDivisionId());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    onClickElection.onNext(electionVM);
                }
            });
        }
    }

    @Override
    public int getItemCount () {
        return (elections == null) ? 0 : (elections.size() + 1);
    }

    @Override
    public int getItemViewType (int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public Observable<ElectionVM> getClickObservable () {
        return onClickElection;
    }

    private boolean isPositionHeader (int position) {
        return position == 0;
    }

    private ElectionVM getItem (int position) {
        return elections.size()==0 ? null : elections.get(position - 1);
    }
}
