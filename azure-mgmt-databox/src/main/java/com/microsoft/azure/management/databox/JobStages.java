/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.databox;

import org.joda.time.DateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job stages.
 */
public class JobStages {
    /**
     * Name of the job stage. Possible values include: 'DeviceOrdered',
     * 'DevicePrepared', 'Dispatched', 'Delivered', 'PickedUp', 'AtAzureDC',
     * 'DataCopy', 'Completed', 'CompletedWithErrors', 'Cancelled',
     * 'Failed_IssueReportedAtCustomer', 'Failed_IssueDetectedAtAzureDC',
     * 'Aborted'.
     */
    @JsonProperty(value = "stageName", access = JsonProperty.Access.WRITE_ONLY)
    private StageName stageName;

    /**
     * Display name of the job stage.
     */
    @JsonProperty(value = "displayName", access = JsonProperty.Access.WRITE_ONLY)
    private String displayName;

    /**
     * Status of the job stage. Possible values include: 'None', 'InProgress',
     * 'Succeeded', 'Failed', 'Cancelled', 'Cancelling', 'SucceededWithErrors'.
     */
    @JsonProperty(value = "stageStatus", access = JsonProperty.Access.WRITE_ONLY)
    private StageStatus stageStatus;

    /**
     * Time for the job stage in UTC ISO 8601 format.
     */
    @JsonProperty(value = "stageTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime stageTime;

    /**
     * Job Stage Details.
     */
    @JsonProperty(value = "jobStageDetails", access = JsonProperty.Access.WRITE_ONLY)
    private Object jobStageDetails;

    /**
     * Error details for the stage.
     */
    @JsonProperty(value = "errorDetails", access = JsonProperty.Access.WRITE_ONLY)
    private List<JobErrorDetails> errorDetails;

    /**
     * Get name of the job stage. Possible values include: 'DeviceOrdered', 'DevicePrepared', 'Dispatched', 'Delivered', 'PickedUp', 'AtAzureDC', 'DataCopy', 'Completed', 'CompletedWithErrors', 'Cancelled', 'Failed_IssueReportedAtCustomer', 'Failed_IssueDetectedAtAzureDC', 'Aborted'.
     *
     * @return the stageName value
     */
    public StageName stageName() {
        return this.stageName;
    }

    /**
     * Get display name of the job stage.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Get status of the job stage. Possible values include: 'None', 'InProgress', 'Succeeded', 'Failed', 'Cancelled', 'Cancelling', 'SucceededWithErrors'.
     *
     * @return the stageStatus value
     */
    public StageStatus stageStatus() {
        return this.stageStatus;
    }

    /**
     * Get time for the job stage in UTC ISO 8601 format.
     *
     * @return the stageTime value
     */
    public DateTime stageTime() {
        return this.stageTime;
    }

    /**
     * Get job Stage Details.
     *
     * @return the jobStageDetails value
     */
    public Object jobStageDetails() {
        return this.jobStageDetails;
    }

    /**
     * Get error details for the stage.
     *
     * @return the errorDetails value
     */
    public List<JobErrorDetails> errorDetails() {
        return this.errorDetails;
    }

}