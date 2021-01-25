package net.corda.samples.duediligence;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.samples.duediligence.flows.ComsumeTheCopy;
import net.corda.samples.duediligence.flows.RequestToValidateCorporateRecords;
import net.corda.samples.duediligence.flows.Responder;
import net.corda.testing.node.MockNetwork;
import net.corda.testing.node.MockNetworkParameters;
import net.corda.testing.node.StartedMockNode;
import net.corda.testing.node.TestCordapp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FlowTests {
    private MockNetwork network;
    private StartedMockNode a;
    private StartedMockNode b;

    @Before
    public void setup() {
        network = new MockNetwork(new MockNetworkParameters().withCordappsForAllNodes(ImmutableList.of(
                TestCordapp.findCordapp("net.corda.samples.duediligence.contracts"),
                TestCordapp.findCordapp("net.corda.samples.duediligence.flows"))));
        a = network.createPartyNode(null);
        b = network.createPartyNode(null);
        network.runNetwork();
    }

    @After
    public void tearDown() {
        network.stopNodes();
    }

    @Test
    public void dummyTest() throws ExecutionException, InterruptedException {
        RequestToValidateCorporateRecords.RequestToValidateCorporateRecordsInitiator flow1 =
                new RequestToValidateCorporateRecords.RequestToValidateCorporateRecordsInitiator(b.getInfo().getLegalIdentities().get(0),10);
        Future<String> future = a.startFlow(flow1);
        network.runNetwork();
        String result1 = future.get();
        System.out.println(result1);
        int subString = result1.indexOf("Case Id: ");
        String ApproalID = result1.substring(subString+9);
        System.out.println("-"+ ApproalID+"-");

        UniqueIdentifier id = UniqueIdentifier.Companion.fromString(ApproalID);
        ComsumeTheCopy.ComsumeTheCopyInitiator flow2 = new ComsumeTheCopy.ComsumeTheCopyInitiator(id);
        Future<String> future2 = a.startFlow(flow2);
        network.runNetwork();
        String result2 = future2.get();
        System.out.println(result2);


    }
}
