package adminpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hussnain.authinticationfirebase.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Admin;
import model.User;

public class OurAdaptr extends RecyclerView.Adapter<OurAdaptr.ViewHolder> {
    private Context context;
    private List<User> uploads;

    public OurAdaptr(Context context, List<User> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        User upload = uploads.get(position);
        holder.textViewName.setText(upload.getEmail());
        //holder.passwordView.setText(upload.getPassword());
        holder.type.setText(upload.getType());
       // Glide.with(context).load(upload.getPassword()).into(holder.passwordView);
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView emailView;
        public TextView type;
        public CircleImageView userImage;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.emailput);
            emailView = (TextView) itemView.findViewById(R.id.emailput);
            type=itemView.findViewById(R.id.type);
            userImage=itemView.findViewById(R.id.recycler_Image);
        }
    }

}
