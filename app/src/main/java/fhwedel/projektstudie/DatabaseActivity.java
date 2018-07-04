package fhwedel.projektstudie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {
    private DataSource dataSource;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dataSource = new DataSource(this);
        activateAddButton();
initializeContextualActionBar();
    }

    private void showAllListEntries() {
        List<Dataset> DatasetList = dataSource.getAllDatasets();

        ArrayAdapter<Dataset> DatasetArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                DatasetList);

        ListView DatasetsListView = (ListView) findViewById(R.id.listview_datasets);
        DatasetsListView.setAdapter(DatasetArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    private void activateAddButton() {
        FloatingActionButton buttonAddProduct = findViewById(R.id.fab);
        final EditText editTextRestaurant = (EditText) findViewById(R.id.editText_restaurant);
        final EditText editTextProduct = (EditText) findViewById(R.id.editText_product);
        /*
        final EditText editTextLatitude = (EditText) findViewById(R.id.editText_latitude);
        final EditText editTextLongitude = (EditText) findViewById(R.id.editText_longitude);
        */

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String restaurant = editTextRestaurant.getText().toString();
                String product = editTextProduct.getText().toString();
                /*
                double latitude = Double.parseDouble(editTextLatitude.getText().toString());
                double longitude = Double.parseDouble(editTextLongitude.getText().toString());
                */

                if (TextUtils.isEmpty(restaurant)) {
                    editTextRestaurant.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(product)) {
                    editTextProduct.setError(getString(R.string.editText_errorMessage));
                    return;
                }

                //TODO Überprüfung
                /*
                if (TextUtils.isEmpty(latitude)) {
                    editTextProduct.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(longitude)) {
                    editTextProduct.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                */

                editTextRestaurant.setText("");
                editTextProduct.setText("");
                /*
                editTextLatitude.setText("");
                editTextLongitude.setText("");
                */

                //dataSource.createShoppingMemo(product, restaurant, latitude, longitude);
                dataSource.createShoppingMemo(product, restaurant);

                InputMethodManager inputMethodManager;
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                showAllListEntries();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }


    private void initializeContextualActionBar() {

        final ListView DatasetsListView = (ListView) findViewById(R.id.listview_datasets);
        DatasetsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        DatasetsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.menu_database, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.dataset_delete:
                        SparseBooleanArray touchedDatasetsPositions = DatasetsListView.getCheckedItemPositions();
                        for (int i = 0; i < touchedDatasetsPositions.size(); i++) {
                            boolean isChecked = touchedDatasetsPositions.valueAt(i);
                            if (isChecked) {
                                int postitionInListView = touchedDatasetsPositions.keyAt(i);
                                Dataset dataset = (Dataset) DatasetsListView.getItemAtPosition(postitionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + postitionInListView + " Inhalt: " + dataset.toString());
                                dataSource.deleteDataset(dataset);
                            }
                        }
                        showAllListEntries();
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }


}
