package com.example.john.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder> {

    private List<GroceryListItem> items;

    public GroceryListAdapter() {
        items = new ArrayList<>(
                Arrays.asList(
                        new GroceryListItem("Pickles", "1 pc.", R.drawable.pickles),
                        new GroceryListItem("Noodles", "2 packs", R.drawable.noodles),
                        new GroceryListItem("Egg", "1 pc.", R.drawable.egg),
                        new GroceryListItem("Rice", "1 sack", R.drawable.rice),
                        new GroceryListItem("Ice Cream", "1 scoop", R.drawable.cream),
                        new GroceryListItem("Tomato Sauce", "1 pouch", R.drawable.tomatosauce),
                        new GroceryListItem("Garlic", "2 pcs.", R.drawable.garlic),
                        new GroceryListItem("Chicken", "1 whole", R.drawable.chicken)
                )
        );
    }

    public String getName(int position) {
        return items.get(position).getName();
    }

    public void remove(int position) {
        items.remove(position);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public GroceryListAdapter.GroceryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View oneRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grocery_list, parent, false);
        return new GroceryListViewHolder(oneRow);
    }

    @Override
    public void onBindViewHolder(GroceryListAdapter.GroceryListViewHolder holder, int position) {
        GroceryListItem item = items.get(position);

        holder.textView.setText(item.getName());
        holder.textViewQuantity.setText(item.getQuantity());
        holder.imageView.setImageResource(item.getImgResourceId());
    }

    public int getItemCount() {
        return items.size();
    }

    public class GroceryListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView textView;
        private TextView textViewQuantity;
        private ImageView imageView;
        private CardView cardview;

        public GroceryListViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textViewQuantity = (TextView) itemView.findViewById(R.id.textViewQuantity);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewPic);
            cardview = (CardView) itemView.findViewById(R.id.card_view);

            cardview.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(final View v) {

            LayoutInflater myLayout = LayoutInflater.from(v.getContext());
            View dialogView = myLayout.inflate(R.layout.change_quantity, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext())
                    .setView(dialogView)
                    .setTitle("Change Ingredient Quantity")
                    .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "Successfully changed ingredient quantity!", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return false;
        }
    }
}
