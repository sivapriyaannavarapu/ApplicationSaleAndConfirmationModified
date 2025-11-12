package com.application.dto;
 
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
public class CombinedAnalyticsDTO {
    private GraphDTO graphData;
    private MetricsDataDTO metricsData;
}