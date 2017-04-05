package com.example.parthobiswas.familytree;


import org.junit.Test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import android.content.Context;

import com.example.parthobiswas.familytree.interfaces.IAPICallbacks;
import com.example.parthobiswas.familytree.models.JSON.NodeDetails;
import com.example.parthobiswas.familytree.models.JSON.PersonDetails;
import com.example.parthobiswas.familytree.models.JSON.RelationshipDetails;
import com.example.parthobiswas.familytree.network.FamilyTreeNetworkManager;
import com.example.parthobiswas.familytree.utils.AppUtils;
import com.example.parthobiswas.familytree.utils.MockModelsUtil;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.CountDownLatch;

/**
 * Created by parthobiswas on 3/31/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class FamilyTreeNetworkManagerTest  extends TestCase {

    @Mock
    private FamilyTreeNetworkManager familyTreeNetworkManager;
    private Context mContext;
    private PersonDetails mPersonDetails;
    private RelationshipDetails mRelationshipDetails;
    private NodeDetails mNodeDetails;
    private CountDownLatch mLatch;
    private Object mResponse;
    private Object mError;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        familyTreeNetworkManager = FamilyTreeNetworkManager.getInstance();
        mContext = RuntimeEnvironment.application;
        mPersonDetails = MockModelsUtil.createPersonDetails();
        mRelationshipDetails = MockModelsUtil.createRelationshipDetails();
        mNodeDetails = MockModelsUtil.createNodeDetails();
        mLatch = new CountDownLatch(1);
    }

    @After
    public void cleanup() {
        familyTreeNetworkManager = null;
        mContext = null;
        mPersonDetails = null;
        mRelationshipDetails = null;
        mNodeDetails = null;
        mLatch = null;
        mResponse = null;
        mError = null;
    }

    @BeforeClass
    public static void testClassSetup() {
        System.out.println("getting test class ready");
    }

    @AfterClass
    public static void testClassCleanup() {
        System.out.println("done with tests");
    }


    @Test
    public void testGetPersonDetails_Success_OnRealServer()  throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mPersonDetails.setSocial_security_number("UnitTestSSN");   // Prerequisit:  This given user("UnitTestSSN") should already be presend into database to pass this testcase.
        familyTreeNetworkManager.getPersonDetails(mPersonDetails.getSocial_security_number(), mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNotNull("Empty response. Server not working!", mResponse);

        RelationshipDetails[] relations = (RelationshipDetails[]) mResponse;
        assertTrue("Didn't get relationship details", mResponse.getClass().equals(RelationshipDetails[].class));
        assertNull("Got error response. Ssomething wrong!", mError);
    }

    @Test
    public void testGetPersonDetails_Failure_OnRealServer() throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mPersonDetails.setSocial_security_number(AppUtils.GenerateRandomNumber(10));
        familyTreeNetworkManager.getPersonDetails(mPersonDetails.getSocial_security_number(), mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNotNull("Empty response. Server not working!", mResponse);

        RelationshipDetails[] relations = (RelationshipDetails[]) mResponse;
        assertEquals("Didn't get relationship details", 1, relations.length);
        assertNull("Got error response. Ssomething wrong!", mError);
    }

    @Test
    public void testGetRelationshipDetails_Success_OnRealServer()  throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mPersonDetails.setSocial_security_number("UnitTestSSN");   // Prerequisit:  This given user("UnitTestSSN") should already be presend into database to pass this testcase.
        familyTreeNetworkManager.getRelationshipDetails(mPersonDetails, mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNotNull("Empty response. Server not working!", mResponse);

        RelationshipDetails[] relations = (RelationshipDetails[]) mResponse;
        assertTrue("Didn't get relationship details", mResponse.getClass().equals(RelationshipDetails[].class));
        assertNull("Got error response. Ssomething wrong!", mError);
    }

    @Test
    public void testGetRelationshipDetails_Failure_OnRealServer() throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mPersonDetails.setSocial_security_number(AppUtils.GenerateRandomNumber(10));
        familyTreeNetworkManager.getRelationshipDetails(mPersonDetails, mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNotNull("Empty response. Server not working!", mResponse);

        RelationshipDetails[] relations = (RelationshipDetails[]) mResponse;
        assertEquals("Didn't get relationship details", 1, relations.length);
        assertNull("Got error response. Ssomething wrong!", mError);
    }

    @Test
    public void testCreateMe_Success_OnRealServer()  throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mPersonDetails.setSocial_security_number(AppUtils.GenerateRandomNumber(10));
        familyTreeNetworkManager.createMe(mPersonDetails, mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNotNull("Empty response. Server not working!", mResponse);

        assertTrue("Didn't get relationship details", mResponse.getClass().equals(PersonDetails.class));
        assertNull("Got error response. Ssomething wrong!", mError);
    }

    @Test
    public void testCreateMe_Failure_OnRealServer()  throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mPersonDetails.setSocial_security_number("UnitTestSSN");   // Prerequisit:  This given user("UnitTestSSN") should already be presend into database to pass this testcase.
        familyTreeNetworkManager.createMe(mPersonDetails, mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNull("User already exists!", mResponse);
    }

    @Test
    public void testCreateNode_Success_OnRealServer()  throws Exception {
        assertNotNull(familyTreeNetworkManager);

        mNodeDetails.setSocial_security_number("UnitTestSSN");   // Prerequisit:  This given user("UnitTestSSN") should already be presend into database to pass this testcase.
        mNodeDetails.setRelative_social_security_number(AppUtils.GenerateRandomNumber(10));
        familyTreeNetworkManager.createNode(mNodeDetails, mContext, new IAPICallbacks() {
            @Override
            public void onResponse(Object response) {
                mResponse = response;
                mLatch.countDown();
            }

            @Override
            public void onError(Object error) {
                mError = error;
                mLatch.countDown();
            }
        });
        mLatch.await();
        assertNotNull("Empty response. Server not working!", mResponse);

        assertTrue("Didn't get relationship details", mResponse.getClass().equals(NodeDetails.class));
        assertNull("Got error response. Ssomething wrong!", mError);
    }

}
