package net.corda.samples.duediligence.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.node.services.Vault;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.samples.duediligence.contracts.DueDChecklistContract;
import net.corda.samples.duediligence.states.CopyOfCoporateRecordsAuditRequest;
import net.corda.samples.duediligence.states.CorporateRecordsAuditRequest;
import net.corda.samples.duediligence.states.DueDChecklist;

import java.util.Arrays;
import java.util.UUID;

public class ComsumeTheCopy {


    @InitiatingFlow
    @StartableByRPC
    public static class ComsumeTheCopyInitiator extends FlowLogic<String> {

        private UniqueIdentifier linearId;

        public ComsumeTheCopyInitiator( UniqueIdentifier linearId) {
            this.linearId = linearId;
        }

        @Override
        @Suspendable
        public String call() throws FlowException {

            //Query the input
            QueryCriteria.LinearStateQueryCriteria inputCriteria = new QueryCriteria.LinearStateQueryCriteria()
                    .withUuid(Arrays.asList(UUID.fromString(linearId.toString())))
                    .withStatus(Vault.StateStatus.UNCONSUMED)
                    .withRelevancyStatus(Vault.RelevancyStatus.RELEVANT);



//Error
//            QueryCriteria.LinearStateQueryCriteria inputCriteria = new QueryCriteria.LinearStateQueryCriteria()
//                    .withExternalId(Arrays.asList(linearId.toString()));


            StateAndRef inputStateAndRef = getServiceHub().getVaultService().queryBy(ContractState.class, inputCriteria).getStates().get(0);
            ContractState result = inputStateAndRef.getState().getData();
            return result.toString();
        }
    }
}
