package com.example.parthobiswas.familytree.models.JSON;

/**
 * Created by parthobiswas on 3/21/17.
 */

public class PersonDetails {

    private String social_security_number;
    private String name;
    private String gender;
    private long date_of_birth;
    private long date_of_death;

    public String getSocial_security_number() {
        return social_security_number;
    }

    public void setSocial_security_number(String social_security_number) {
        this.social_security_number = social_security_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(long date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public long getDate_of_death() {
        return date_of_death;
    }

    public void setDate_of_death(long date_of_death) {
        this.date_of_death = date_of_death;
    }

    public RelationshipDetails convertToRelationDetails(String relation, RelationshipDetails[] relatives) {
        RelationshipDetails relationshipDetails = new RelationshipDetails();
        relationshipDetails.setSocial_security_number(this.getSocial_security_number());
        relationshipDetails.setName(this.getName());
        relationshipDetails.setGender(this.getGender());
        relationshipDetails.setDate_of_birth(this.getDate_of_birth());
        relationshipDetails.setDate_of_death(this.getDate_of_death());
        relationshipDetails.setRelation_type(relation);
        relationshipDetails.setRelatives(relatives);
        return relationshipDetails;
    }
}
