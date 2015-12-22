package com.example.john.grocerylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class GroceryListActivity extends AppCompatActivity {

    private RecyclerView groceryListView;
    private GroceryListAdapter groceryListAdapter;
    private GroceryListAdapter.GroceryListViewHolder groceryListViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabGroceryList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence choose[] = new CharSequence[] {"Choose from the Recipe", "Choose from the Ingredient", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Add Ingredients");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choose[which].equals("Choose from the Recipe")) {
                            Toast.makeText(view.getContext(), "Add Ingredients from recipe section!", Toast.LENGTH_SHORT).show();
                        } else if (choose[which].equals("Choose from the Ingredient")) {
                            Toast.makeText(view.getContext(), "Add Ingredient section from ingredients section!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });

        groceryListView = (RecyclerView) findViewById(R.id.groceryListView);
        groceryListView.setLayoutManager(new LinearLayoutManager(this));
        groceryListAdapter = new GroceryListAdapter();
        groceryListView.setAdapter(groceryListAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(GroceryListActivity.this, "Item " + groceryListAdapter.getName(viewHolder.getLayoutPosition()) + " archived", Toast.LENGTH_SHORT).show();
                groceryListAdapter.remove(viewHolder.getLayoutPosition());
                groceryListAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                if(groceryListAdapter.isEmpty()) {
                    Toast.makeText(GroceryListActivity.this, "Grocery List is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(groceryListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grocery_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.archive_list :
                Intent intent = new Intent(GroceryListActivity.this, ArchiveListActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addIngredient(final View view) {
        LayoutInflater myLayout = LayoutInflater.from(this);
        View dialogView = myLayout.inflate(R.layout.change_quantity, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext())
                .setView(dialogView)
                .setTitle("Add Ingredient")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(), "Successfully added ingredient!", Toast.LENGTH_SHORT).show();
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
    }
}
