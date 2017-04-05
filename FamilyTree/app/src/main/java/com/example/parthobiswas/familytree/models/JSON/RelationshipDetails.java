package com.example.parthobiswas.familytree.models.JSON;

import java.io.Serializable;

/**
 * Created by parthobiswas on 3/21/17.
 */

public class RelationshipDetails implements Serializable {

    private String social_security_number;
    private String name;
    private String gender;
    private long date_of_birth;
    private long date_of_death;
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

    public RelationshipDetails[] getRelatives() {
        return relatives;
    }

    public void setRelatives(RelationshipDetails[] relatives) {
        this.relatives = relatives;
    }
}
