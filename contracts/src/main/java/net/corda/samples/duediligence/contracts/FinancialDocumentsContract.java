package net.corda.samples.duediligence.contracts;

import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

public class FinancialDocumentsContract implements Contract {
    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {

    }
}
