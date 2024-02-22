package pl.nankiewic.fleetappbackend.report.view;

import java.time.LocalDateTime;

public interface ReportView {

    Long getId();

    String getCreatedBy();

    LocalDateTime getCreatedDate();

}
