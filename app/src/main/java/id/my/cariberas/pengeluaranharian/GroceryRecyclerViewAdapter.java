package id.my.cariberas.pengeluaranharian;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroceryRecyclerViewAdapter extends RecyclerView.Adapter<GroceryRecyclerViewAdapter.GroceryViewHolder> {

    private List<Grocery> groceryList;

    private ArrayList id_pengeluaran; //Digunakan untuk Nama
    private ArrayList title; //Digunakan untuk Jurusan
    private ArrayList fee; //Digunakan untuk Jurusan
    private ArrayList tanggal; //Digunakan untuk Jurusan

    //Membuat Konstruktor pada Class RecyclerViewAdapter
//    GroceryRecyclerViewAdapter(ArrayList id_pengeluaran, ArrayList title, ArrayList fee, ArrayList tanggal){
//        this.id_pengeluaran = id_pengeluaran;
//        this.title = title;
//        this.fee = fee;
//        this.tanggal = tanggal;
//    }
    public GroceryRecyclerViewAdapter(ArrayList<Grocery> groceryList) {
        this.groceryList = groceryList;
    }


    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list, viewGroup, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, int i) {
        Grocery grocery = groceryList.get(i);

        groceryViewHolder.title.setText(grocery.getTitle());
        groceryViewHolder.fee.setText(String.valueOf(grocery.getFee()));
        groceryViewHolder.tanggal.setText(String.valueOf(grocery.getTanggal()));
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public void updateData(List<Grocery> groceryList) {
        this.groceryList = groceryList;
        notifyDataSetChanged();
    }

    static class GroceryViewHolder extends RecyclerView.ViewHolder {

        private TextView id_pengeluaran, title, fee, tanggal;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tPengeluaran);
            fee = itemView.findViewById(R.id.tHarga);
            tanggal = itemView.findViewById(R.id.tTanggal);
        }
    }
}