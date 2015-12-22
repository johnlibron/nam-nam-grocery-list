package com.example.john.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ArchiveListActivity extends AppCompatActivity {

    private RecyclerView archiveListView;
    private ArchiveListAdapter archiveListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        archiveListView = (RecyclerView) findViewById(R.id.archiveListView);
        archiveListView.setLayoutManager(new LinearLayoutManager(this));
        archiveListAdapter = new ArchiveListAdapter();
        archiveListView.setAdapter(archiveListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!archiveListAdapter.isSelectedItemsEmpty()) {
                    archiveListAdapter.delete();
                    Snackbar.make(view, "Delete selected items", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Please select items to delete", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(ArchiveListActivity.this, "Item " + archiveListAdapter.getName(viewHolder.getLayoutPosition()) + " restored", Toast.LENGTH_SHORT).show();
                archiveListAdapter.remove(viewHolder.getLayoutPosition());
                archiveListAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                if(archiveListAdapter.isEmpty()) {
                    Toast.makeText(ArchiveListActivity.this, "Archive List is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(archiveListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_archive_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grocery_list:
                Intent intent = new Intent(ArchiveListActivity.this, GroceryListActivity.class);
                startActivity(intent);
                return true;
            case R.id.delete_list:
                archiveListAdapter.deleteCheckList();
                Toast.makeText(this, "Archive List is empty!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
