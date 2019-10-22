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

    public GroceryRecyclerViewAdapter(ArrayList<Grocery> groceryList) {
        this.groceryList = groceryList;
    }


    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, int i) {
        groceryViewHolder.title.setText(groceryList.get(i).getTitle());
        groceryViewHolder.fee.setText(groceryList.get(i).getFee());
        groceryViewHolder.tanggal.setText(groceryList.get(i).getTanggal());
    }

    @Override
    public int getItemCount() {
        return (groceryList != null) ? groceryList.size() : 0;
    }

    public void updateData(List<Grocery> groceryList) {
        this.groceryList = groceryList;
        notifyDataSetChanged();
    }

    static class GroceryViewHolder extends RecyclerView.ViewHolder {

        private TextView title, fee, tanggal;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tPengeluaran);
            fee = itemView.findViewById(R.id.tHarga);
            tanggal = itemView.findViewById(R.id.tTanggal);
        }
    }
}