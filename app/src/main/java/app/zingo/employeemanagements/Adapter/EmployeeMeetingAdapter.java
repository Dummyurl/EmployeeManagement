package app.zingo.employeemanagements.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import app.zingo.employeemanagements.Model.Employee;
import app.zingo.employeemanagements.Model.Meetings;
import app.zingo.employeemanagements.R;
import app.zingo.employeemanagements.UI.Admin.EmployeesDashBoard;

/**
 * Created by ZingoHotels Tech on 17-10-2018.
 */

public class EmployeeMeetingAdapter extends RecyclerView.Adapter<EmployeeMeetingAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Meetings> list;

    public EmployeeMeetingAdapter(Context context, ArrayList<Meetings> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        try {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_meeting_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;

        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Meetings dto = list.get(position);

        if(dto!=null){

            holder.mMeetingCount.setText("Meeting "+(position+1));
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


            holder.mView.setBackgroundColor(color);
            holder.mMeetingCount.setTextColor(color);

            if(dto.getMeetingPersonDetails().contains("%")){

                String person[] = dto.getMeetingPersonDetails().split("%");
                holder.mContact.setText(""+person[1]);
                holder.mClient.setText(""+person[0]);
            }else{
                holder.mClient.setText(""+dto.getMeetingPersonDetails());
            }

            holder.mPurpose.setText(""+dto.getMeetingAgenda());
            holder.mRemarks.setText(""+dto.getMeetingDetails());
        }

        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        String startLocation = dto.getStartLocation();
        String endLocation = dto.getEndLocation();

        String checkDetails="",outDetails="";

        if(startTime!=null){
            checkDetails = checkDetails+"Meeting Start Time:\n"+startTime;
        }

        if(startLocation!=null){

            checkDetails = checkDetails+"\n\nMeeting Location:\n"+startLocation;
        }

        if(endTime!=null){
            outDetails = outDetails+"Meeting End Time:\n"+endTime;
        }

        if(endLocation!=null){

            outDetails = outDetails+"\n\nMeeting Location:\n"+endLocation;
        }


        if(checkDetails!=null&&!checkDetails.isEmpty()){

            holder.mCheckInDetails.setText(checkDetails);
        }

        if(outDetails!=null&&!outDetails.isEmpty()){

            holder.mCheckOutDetails.setText(outDetails);
        }


    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        EditText mRemarks,mCheckInDetails,mCheckOutDetails;
        TextInputEditText mClient,mContact,mPurpose;
        TextView mMeetingCount;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            mRemarks = (EditText) itemView.findViewById(R.id.meeting_remarks);
            mMeetingCount = (TextView) itemView.findViewById(R.id.meeting_count);
            mCheckInDetails = (EditText) itemView.findViewById(R.id.check_in_details);
            mCheckOutDetails = (EditText) itemView.findViewById(R.id.check_out_details);
            mClient = (TextInputEditText) itemView.findViewById(R.id.client_name);
            mContact = (TextInputEditText) itemView.findViewById(R.id.client_contact);
            mPurpose = (TextInputEditText) itemView.findViewById(R.id.purpose_meeting);
            mView = (View) itemView.findViewById(R.id.view_color);



        }
    }
}
