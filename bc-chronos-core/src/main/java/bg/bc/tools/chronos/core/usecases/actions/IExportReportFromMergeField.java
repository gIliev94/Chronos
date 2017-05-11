package bg.bc.tools.chronos.core.usecases.actions;

import java.time.LocalDate;
import java.util.Map;

public interface IExportReportFromMergeField {

    // TODO: Finish up
    void exportReport(Map<String, Object> replacementValues);

    void exportReport(Map<String, Object> replacementValues, LocalDate from, LocalDate to);
}
