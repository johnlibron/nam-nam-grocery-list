package com.example.john.grocerylist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchiveListAdapter extends RecyclerView.Adapter<ArchiveListAdapter.ArchiveListViewHolder> {

    private List<GroceryListItem> items;
    private List<GroceryListItem> selectedItems;

    public ArchiveListAdapter() {
        items = new ArrayList<>(
                Arrays.asList(
                        new GroceryListItem("Potato", "2 pcs.", R.drawable.potato),
                        new GroceryListItem("Cheese", "1 box", R.drawable.cheese),
                        new GroceryListItem("Bacon", "1 pack", R.drawable.bacon),
                        new GroceryListItem("Ham", "1 can", R.drawable.ham),
                        new GroceryListItem("Ampalaya", "1 pc.", R.drawable.ampalaya),
                        new GroceryListItem("Mango", "4 pcs.", R.drawable.mango)
                )
        );

        selectedItems = new ArrayList<>();
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

    public void deleteCheckList() {
        items.clear();
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public boolean isSelectedItemsEmpty() {
        return selectedItems.isEmpty();
    }

    public void delete() {
        int size = selectedItems.size();
        for (int x = 0; x < size; x++) {
            items.remove(selectedItems.get(x));
        }

        notifyDataSetChanged();
        selectedItems.clear();
    }

    @Override
    public ArchiveListAdapter.ArchiveListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View oneRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_archive_list, parent, false);
        return new ArchiveListViewHolder(oneRow);
    }

    @Override
    public void onBindViewHolder(ArchiveListAdapter.ArchiveListViewHolder holder, int position) {
        GroceryListItem item = items.get(position);

        holder.textView.setText(item.getName());
        holder.textViewQuantity.setText(item.getQuantity());
        holder.imageView.setImageResource(item.getImgResourceId());

        if (selectedItems.contains(item)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ArchiveListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private TextView textViewQuantity;
        private ImageView imageView;
        private CheckBox checkBox;
        private CardView cardView;

        public ArchiveListViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textViewQuantity = (TextView) itemView.findViewById(R.id.textViewQuantity);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewPic);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            GroceryListItem item = items.get(adapterPosition);

            if (!selectedItems.contains(item)) {
                checkBox.setChecked(true);
                selectedItems.add(item);
            } else {
                checkBox.setChecked(false);

                if (selectedItems.size() == 1) {
                    selectedItems.clear();
                } else {
                    selectedItems.remove(item);
                }
            }
        }


    }
}
