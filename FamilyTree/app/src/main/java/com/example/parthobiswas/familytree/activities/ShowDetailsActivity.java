package com.example.parthobiswas.familytree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parthobiswas.familytree.R;
import com.example.parthobiswas.familytree.models.JSON.RelationshipDetails;
import com.example.parthobiswas.familytree.utils.AppUtils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_name_value)       TextView tv_name_value;
    @BindView(R.id.tv_ssn_value)        TextView tv_ssn_value;
    @BindView(R.id.tv_gender_value)     TextView tv_gender_value;
    @BindView(R.id.tv_bd_value)         TextView tv_bd_value;
    @BindView(R.id.tv_dd_value)         TextView tv_dd_value;
    @BindView(R.id.tv_relation_value)   TextView tv_relation_value;
    @BindView(R.id.layout_dd)           LinearLayout layout_dd;
    @BindView(R.id.layout_relation)     LinearLayout layout_relation;

    private RelationshipDetails relationshipDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Intent i = getIntent();
        relationshipDetails = (RelationshipDetails)i.getSerializableExtra(AppUtils.SELECTED_PERSON);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.showDetails(relationshipDetails);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDetails(RelationshipDetails relationshipDetails) {
        tv_name_value.setText(relationshipDetails.getName().toString());
        tv_ssn_value.setText(relationshipDetails.getSocial_security_number().toString());
        tv_gender_value.setText(relationshipDetails.getGender().toString());

        tv_bd_value.setText(String.valueOf(relationshipDetails.getDate_of_birth()));

        String mySSN = Prefs.getString(AppUtils.MY_SSN, null);
        if (mySSN.equalsIgnoreCase(relationshipDetails.getSocial_security_number())) {
            layout_dd.setVisibility(View.GONE);
            layout_relation.setVisibility(View.GONE);
        } else {
            tv_dd_value.setText(String.valueOf(relationshipDetails.getDate_of_death()));
            tv_relation_value.setText(relationshipDetails.getRelation_type().toString());
        }
    }
}
