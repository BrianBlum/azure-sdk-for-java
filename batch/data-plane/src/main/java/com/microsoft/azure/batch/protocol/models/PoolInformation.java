// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator

package com.microsoft.azure.batch.protocol.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies how a job should be assigned to a pool.
 */
public class PoolInformation {
    /**
     * The ID of an existing pool. All the tasks of the job will run on the
     * specified pool.
     * You must ensure that the pool referenced by this property exists. If the
     * pool does not exist at the time the Batch service tries to schedule a
     * job, no tasks for the job will run until you create a pool with that id.
     * Note that the Batch service will not reject the job request; it will
     * simply not run tasks until the pool exists. You must specify either the
     * pool ID or the auto pool specification, but not both.
     */
    @JsonProperty(value = "poolId")
    private String poolId;

    /**
     * Characteristics for a temporary 'auto pool'. The Batch service will
     * create this auto pool when the job is submitted.
     * If auto pool creation fails, the Batch service moves the job to a
     * completed state, and the pool creation error is set in the job's
     * scheduling error property. The Batch service manages the lifetime (both
     * creation and, unless keepAlive is specified, deletion) of the auto pool.
     * Any user actions that affect the lifetime of the auto pool while the job
     * is active will result in unexpected behavior. You must specify either
     * the pool ID or the auto pool specification, but not both.
     */
    @JsonProperty(value = "autoPoolSpecification")
    private AutoPoolSpecification autoPoolSpecification;

    /**
     * Get you must ensure that the pool referenced by this property exists. If the pool does not exist at the time the Batch service tries to schedule a job, no tasks for the job will run until you create a pool with that id. Note that the Batch service will not reject the job request; it will simply not run tasks until the pool exists. You must specify either the pool ID or the auto pool specification, but not both.
     *
     * @return the poolId value
     */
    public String poolId() {
        return this.poolId;
    }

    /**
     * Set you must ensure that the pool referenced by this property exists. If the pool does not exist at the time the Batch service tries to schedule a job, no tasks for the job will run until you create a pool with that id. Note that the Batch service will not reject the job request; it will simply not run tasks until the pool exists. You must specify either the pool ID or the auto pool specification, but not both.
     *
     * @param poolId the poolId value to set
     * @return the PoolInformation object itself.
     */
    public PoolInformation withPoolId(String poolId) {
        this.poolId = poolId;
        return this;
    }

    /**
     * Get if auto pool creation fails, the Batch service moves the job to a completed state, and the pool creation error is set in the job's scheduling error property. The Batch service manages the lifetime (both creation and, unless keepAlive is specified, deletion) of the auto pool. Any user actions that affect the lifetime of the auto pool while the job is active will result in unexpected behavior. You must specify either the pool ID or the auto pool specification, but not both.
     *
     * @return the autoPoolSpecification value
     */
    public AutoPoolSpecification autoPoolSpecification() {
        return this.autoPoolSpecification;
    }

    /**
     * Set if auto pool creation fails, the Batch service moves the job to a completed state, and the pool creation error is set in the job's scheduling error property. The Batch service manages the lifetime (both creation and, unless keepAlive is specified, deletion) of the auto pool. Any user actions that affect the lifetime of the auto pool while the job is active will result in unexpected behavior. You must specify either the pool ID or the auto pool specification, but not both.
     *
     * @param autoPoolSpecification the autoPoolSpecification value to set
     * @return the PoolInformation object itself.
     */
    public PoolInformation withAutoPoolSpecification(AutoPoolSpecification autoPoolSpecification) {
        this.autoPoolSpecification = autoPoolSpecification;
        return this;
    }

}
