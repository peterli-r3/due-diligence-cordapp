package net.corda.samples.duediligence.states;

import net.corda.samples.duediligence.contracts.FinancialDocumentsContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.List;

// *********
// * State *
// *********
@BelongsToContract(FinancialDocumentsContract.class)
public class FinancialDocuments implements ContractState {

    //private variables
    private Boolean qualification = false;
    private Party applicant;
    private Party validater;
    private int numberOfFiles;
    private List<String> namesOfFiles;

    /* Constructor of your Corda state */
    public FinancialDocuments(Party applicant, Party validater, int numberOfFiles, List<String> namesOfFiles) {
        this.applicant = applicant;
        this.validater = validater;
        this.numberOfFiles = numberOfFiles;
        this.namesOfFiles = namesOfFiles;
    }

    //getters
    public Boolean getQualification() {        return qualification;    }
    public Party getApplicant() {        return applicant;    }
    public Party getValidater() {        return validater;    }
    public int getNumberOfFiles() {        return numberOfFiles;    }
    public List<String> getNamesOfFiles() {        return namesOfFiles;    }

    /* This method will indicate who are the participants and required signers when
     * this state is used in a transaction. */
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(applicant,validater);
    }

    public void validatedAndApproved (){
        this.qualification = true;
    }
}