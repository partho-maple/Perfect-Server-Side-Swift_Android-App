package com.example.parthobiswas.familytree.activities;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.developers.coolprogressviews.DoubleArcProgress;
import com.example.parthobiswas.familytree.R;
import com.example.parthobiswas.familytree.holder.FamilyTreeItemHolder;
import com.example.parthobiswas.familytree.interfaces.IAPICallbacks;
import com.example.parthobiswas.familytree.models.JSON.RelationshipDetails;
import com.example.parthobiswas.familytree.network.FamilyTreeNetworkManager;
import com.example.parthobiswas.familytree.utils.AppUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private DoubleArcProgress m_doubleArcProgress;
    private AndroidTreeView m_tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        // Setting main layout
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding progress loader
        m_doubleArcProgress = (DoubleArcProgress) findViewById(R.id.progress_indicator);
        m_doubleArcProgress.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Initialising and reloading viewes
        this.initViewes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reload) {
            // Reload option tapped
            this.initViewes();
            return true;
        } else if (id == R.id.action_add) {
            // Add used option tapped
            String mySSN = Prefs.getString(AppUtils.MY_SSN, null);
            Intent myIntent = new Intent(MainActivity.this, AddDetailsActivity.class);
            myIntent.putExtra(AppUtils.MY_SSN, mySSN); //Optional parameters
            if (mySSN == null) {
                myIntent.putExtra(AppUtils.IS_ADDIND_RELATION, false);
            } else {
                myIntent.putExtra(AppUtils.IS_ADDIND_RELATION, true);
            }

            // Starting AddDetailsActivity activity
            MainActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViewes() {
        String mySSN = Prefs.getString(AppUtils.MY_SSN, null);
        if (mySSN != null) {
            this.loadFamilyTree(mySSN);
        } else {
            this.showNoDataFoundViewIfNeeded(0);
        }
    }

    private void showNoDataFoundViewIfNeeded(int row) {
        ImageView noData = (ImageView) findViewById(R.id.noDatamageView);

        if (row <= 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }

    private void loadFamilyTree(String ssn) {
        m_doubleArcProgress.setVisibility(View.VISIBLE);
        FamilyTreeNetworkManager.getInstance().getPersonDetails(ssn, this, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                if (response.getClass().equals(RelationshipDetails[].class)) {
                    createTreeWithRelations((RelationshipDetails[]) response);
                }
                m_doubleArcProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Object error) {
                if (error.getClass().equals(String.class)) {
                    Toasty.error(getApplicationContext(), (String)error, Toast.LENGTH_SHORT, true).show();
                }
                m_doubleArcProgress.setVisibility(View.GONE);
            }
        });
    }

    private void createTreeWithRelations(RelationshipDetails[] relationshipDetailses) {

        if (m_tView != null) {
            // Removing existing Family tree
            ViewGroup containerView = (ViewGroup) this.findViewById(R.id.tree_container);
            containerView.removeAllViews();
            m_tView = null;
            System.gc();
        }

        // Creating tree view
        TreeNode rootNode = TreeNode.root();
        TreeNode rNode = createNode(rootNode, relationshipDetailses);

        m_tView = new AndroidTreeView(this, rootNode);
        ViewGroup containerView = (ViewGroup) this.findViewById(R.id.tree_container);
        m_tView.setDefaultAnimation(true);
        m_tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        m_tView.setDefaultViewHolder(FamilyTreeItemHolder.class);
        m_tView.setDefaultNodeClickListener(nodeClickListener);
        containerView.addView(m_tView.getView());
        containerView.setVisibility(View.VISIBLE);

        // Loading tree view
        this.showNoDataFoundViewIfNeeded(1);
        m_doubleArcProgress.setVisibility(View.GONE);
        Toasty.success(this, "You family tree is here !", Toast.LENGTH_SHORT, true).show();
    }

    // Tree node click listener
    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            FamilyTreeItemHolder.FamilyTreeItem item = (FamilyTreeItemHolder.FamilyTreeItem) value;
            String status = "Last clicked: " + item.name;
        }
    };

    // Creating family tree nodes. It's the main method for creating tree data source.
    private TreeNode createNode(TreeNode parentNode, RelationshipDetails[] nodeArray) {

        for (int i = 0; i < nodeArray.length; i++) {
            RelationshipDetails relation = nodeArray[i];
            TreeNode myNode;
            if (relation != null) {
                FamilyTreeItemHolder.FamilyTreeItem familyTreeItem = new FamilyTreeItemHolder.FamilyTreeItem(relation);
                myNode = new TreeNode(familyTreeItem).setViewHolder(new FamilyTreeItemHolder(this));

                if (relation.getRelatives() != null && relation.getRelatives().length > 0) {
                    createNode(myNode, relation.getRelatives());
                }
                parentNode.addChild(myNode);
            }
        }

        return parentNode;
    }

}
