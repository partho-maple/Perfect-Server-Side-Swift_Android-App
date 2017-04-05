package com.example.parthobiswas.familytree.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developers.coolprogressviews.DoubleArcProgress;
import com.emmasuzuki.easyform.EasyForm;
import com.emmasuzuki.easyform.EasyTextInputLayout;
import com.example.parthobiswas.familytree.R;
import com.example.parthobiswas.familytree.interfaces.IAPICallbacks;
import com.example.parthobiswas.familytree.models.JSON.NodeDetails;
import com.example.parthobiswas.familytree.models.JSON.PersonDetails;
import com.example.parthobiswas.familytree.network.FamilyTreeNetworkManager;
import com.example.parthobiswas.familytree.utils.AppUtils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class AddDetailsActivity extends AppCompatActivity {

    @BindView(R.id.add_form)
    EasyForm easyForm;

    @BindView(R.id.first_name_input)
    EasyTextInputLayout first_name_input;
    @BindView(R.id.last_name_input)
    EasyTextInputLayout last_name_input;
    @BindView(R.id.ssn_id_input)
    EasyTextInputLayout ssn_id_input;
    @BindView(R.id.gender_input)
    EasyTextInputLayout gender_input;
    @BindView(R.id.birth_date_input)
    EasyTextInputLayout birth_date_input;
    @BindView(R.id.death_date_input)
    EasyTextInputLayout death_date_input;
    @BindView(R.id.relation_input)
    EasyTextInputLayout relation_input;
    @BindView(R.id.submit_button)
    Button submitButton;

    private DoubleArcProgress m_doubleArcProgress;
    private String m_mySSN;
    private Boolean m_isAddingRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.m_mySSN = extras.getString(AppUtils.MY_SSN);
            m_isAddingRelation = extras.getBoolean(AppUtils.IS_ADDIND_RELATION);
        }

        if (m_isAddingRelation == false) {
            relation_input.setVisibility(View.GONE);
            death_date_input.setVisibility(View.GONE);
        }

        m_doubleArcProgress = (DoubleArcProgress) findViewById(R.id.progress_indicator);
        m_doubleArcProgress.setVisibility(View.GONE);
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


    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        // Make sure to call easyForm.validate() when using showErrorOn = UNFOCUS
        easyForm.validate();

        if (easyForm.isValid()) {
            String value = (String) birth_date_input.getEditText().getText().toString();
            System.out.print("-----------Value: " + value);

            String value2 = (String) death_date_input.getEditText().getText().toString();
            System.out.print("-----------Value2: " + value2);

            m_doubleArcProgress.setVisibility(View.VISIBLE);
            String name = first_name_input.getEditText().getText().toString() + " " + last_name_input.getEditText().getText().toString();
            if (m_isAddingRelation) {
                NodeDetails nodeDetails = new NodeDetails();
                nodeDetails.setSocial_security_number(m_mySSN);
                nodeDetails.setRelative_social_security_number(ssn_id_input.getEditText().getText().toString());
                nodeDetails.setName(name);
                nodeDetails.setGender(gender_input.getEditText().getText().toString());
                nodeDetails.setDate_of_birth(Long.valueOf(birth_date_input.getEditText().getText().toString()));
                if (death_date_input.getEditText().getText().toString() != null && (!death_date_input.getEditText().getText().toString().isEmpty())) {
                    nodeDetails.setDate_of_death(Long.valueOf(death_date_input.getEditText().getText().toString()));
                } else {
                    nodeDetails.setDate_of_death(0);
                }
                nodeDetails.setRelation_id(0);
                nodeDetails.setRelation_type(relation_input.getEditText().getText().toString());
                this.createNode(nodeDetails);
            } else {
                PersonDetails personDetails = new PersonDetails();
                personDetails.setSocial_security_number(ssn_id_input.getEditText().getText().toString());
                personDetails.setName(name);
                personDetails.setGender(gender_input.getEditText().getText().toString());
                personDetails.setDate_of_birth(Long.valueOf(birth_date_input.getEditText().getText().toString()));
                personDetails.setDate_of_death(0);
                this.createMe(personDetails);
            }

        } else {
            Toasty.error(getApplicationContext(), "Please enter valid values !", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void createMe(PersonDetails personDetails) {
        FamilyTreeNetworkManager.getInstance().createMe(personDetails, this, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                if (response.getClass().equals(PersonDetails.class)) {
                    PersonDetails personDetails = (PersonDetails) response;
                    Prefs.putString(AppUtils.MY_SSN, personDetails.getSocial_security_number());
                    Toasty.success(getApplicationContext(), "Your details have been added !", Toast.LENGTH_SHORT, true).show();
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

    private void createNode(NodeDetails nodeDetails) {
        FamilyTreeNetworkManager.getInstance().createNode(nodeDetails, this, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                if (response.getClass().equals(NodeDetails.class)) {
                    Toasty.success(getApplicationContext(), "Your relationship details have been added !", Toast.LENGTH_SHORT, true).show();
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

}
