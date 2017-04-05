package com.example.parthobiswas.familytree.utils;

import com.example.parthobiswas.familytree.models.JSON.NodeDetails;
import com.example.parthobiswas.familytree.models.JSON.PersonDetails;
import com.example.parthobiswas.familytree.models.JSON.RelationshipDetails;

/**
 * Created by parthobiswas on 3/30/17.
 */

public class MockModelsUtil {

    public static PersonDetails createPersonDetails() {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setSocial_security_number("Partho007");
        personDetails.setName("Partho");
        personDetails.setGender("Male");
        personDetails.setDate_of_birth(000);
        personDetails.setDate_of_death(000);
        return personDetails;
    }

    public static RelationshipDetails createRelationshipDetails() {
        RelationshipDetails relationshipDetails = new RelationshipDetails();
        relationshipDetails.setSocial_security_number("Partho007");
        relationshipDetails.setName("Partho");
        relationshipDetails.setGender("Male");
        relationshipDetails.setDate_of_birth(000);
        relationshipDetails.setDate_of_death(000);
        return relationshipDetails;
    }

    public static NodeDetails createNodeDetails() {
        NodeDetails nodeDetails = new NodeDetails();
        nodeDetails.setSocial_security_number("Partho007");
        nodeDetails.setName("Partho");
        nodeDetails.setGender("Male");
        nodeDetails.setRelation_type("test");
        nodeDetails.setRelative_social_security_number("Chaity007");
        nodeDetails.setDate_of_birth(000);
        nodeDetails.setDate_of_death(000);
        return nodeDetails;
    }
}
