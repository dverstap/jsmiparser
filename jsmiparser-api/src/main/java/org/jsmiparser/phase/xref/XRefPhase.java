package org.jsmiparser.phase.xref;

import org.jsmiparser.phase.Phase;
import org.jsmiparser.phase.PhaseException;
import org.jsmiparser.util.problem.ProblemReporterFactory;
import org.jsmiparser.smi.SmiMib;
import org.jsmiparser.smi.SmiTable;
import org.jsmiparser.smi.SmiModule;

/**
 * Created by IntelliJ IDEA.
 * User: stappend
 * Date: Aug 3, 2006
 * Time: 12:23:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class XRefPhase implements Phase {

    private ProblemReporterFactory m_problemReporterFactory;

    public XRefPhase(ProblemReporterFactory problemReporterFactory) {
        m_problemReporterFactory = problemReporterFactory;
    }

    public Object getOptions() {
        return null;
    }

    public Object process(Object input) throws PhaseException {
        SmiMib mib = (SmiMib) input;

        for (SmiModule module : mib.getModules()) {
            module.fillTables();
        }

        mib.fillTables();

        // TODO
        return mib;
    }
}
