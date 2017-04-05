package com.example.parthobiswas.familytree.models.JSON;

/**
 * Created by parthobiswas on 3/21/17.
 */

public class NodeDetails {

    private String social_security_number;
    private String name;
    private String gender;
    private double date_of_birth;
    private double date_of_death;
    private String relative_social_security_number;
    private String relation_type;
    private int relation_id;

    private RelationshipDetails[] relatives;

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

    public double getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(double date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public double getDate_of_death() {
        return date_of_death;
    }

    public void setDate_of_death(double date_of_death) {
        this.date_of_death = date_of_death;
    }

    public String getRelative_social_security_number() {
        return relative_social_security_number;
    }

    public void setRelative_social_security_number(String relative_social_security_number) {
        this.relative_social_security_number = relative_social_security_number;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
    }

    public int getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(int relation_id) {
        this.relation_id = relation_id;
    }
}
