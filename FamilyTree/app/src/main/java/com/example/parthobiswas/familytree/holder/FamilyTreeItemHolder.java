package com.example.parthobiswas.familytree.holder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parthobiswas.familytree.activities.ShowDetailsActivity;
import com.example.parthobiswas.familytree.models.JSON.RelationshipDetails;
import com.example.parthobiswas.familytree.utils.AppUtils;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.example.parthobiswas.familytree.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by parthobiswas on 3/21/17.
 */
public class FamilyTreeItemHolder extends TreeNode.BaseNodeViewHolder<FamilyTreeItemHolder.FamilyTreeItem> {
    private TextView m_tvValue;
    private TextView m_tvSubValue;
    private PrintView m_arrowView;

    public FamilyTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, FamilyTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(R.layout.layout_icon_node, null, true);

        m_tvValue = (TextView) view.findViewById(R.id.node_value);
        m_tvValue.setText(value.name);

        m_tvSubValue = (TextView) view.findViewById(R.id.node_subvalue);
        m_tvSubValue.setText(value.relation);

        m_arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        view.findViewById(R.id.btn_showDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FamilyTreeItem selectedItem = (FamilyTreeItem) node.getValue();
                String status = "Showing details for  " + selectedItem.relationshipDetails.getName();
                Toasty.info(context, status, Toast.LENGTH_SHORT, true).show();

                Intent myIntent = new Intent(context, ShowDetailsActivity.class);
                myIntent.putExtra(AppUtils.SELECTED_PERSON, selectedItem.relationshipDetails);
                context.startActivity(myIntent);
            }
        });

        return view;
    }

    @Override
    public void toggle(boolean active) {
        m_arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }

    @SuppressWarnings("serial")
    public static class FamilyTreeItem {
        public RelationshipDetails relationshipDetails;
        public String name;
        public String relation;

        public FamilyTreeItem(RelationshipDetails relationshipDetails) {
            this.relationshipDetails = relationshipDetails;
            this.name = relationshipDetails.getName();
            this.relation = relationshipDetails.getRelation_type();
        }
    }
}
