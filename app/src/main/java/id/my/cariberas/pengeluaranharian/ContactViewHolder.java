package id.my.cariberas.pengeluaranharian;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView title,fee,tanggal;
    public ImageView deleteContact;
    public  ImageView editContact;

    public ContactViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.tPengeluaran);
        fee = (TextView)itemView.findViewById(R.id.tHarga);
        tanggal = (TextView)itemView.findViewById(R.id.tTanggal);
//        deleteContact = (ImageView)itemView.findViewById(R.id.delete_contact);
//        editContact = (ImageView)itemView.findViewById(R.id.edit_contact);
    }
}
