/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.consumption.v2018_01_31;

import com.microsoft.azure.arm.model.HasInner;
import com.microsoft.azure.management.consumption.v2018_01_31.implementation.BudgetInner;
import com.microsoft.azure.arm.model.Indexable;
import com.microsoft.azure.arm.model.Creatable;
import com.microsoft.azure.arm.model.Updatable;
import com.microsoft.azure.arm.model.Appliable;
import com.microsoft.azure.arm.model.Refreshable;
import com.microsoft.azure.arm.resources.models.HasManager;
import com.microsoft.azure.management.consumption.v2018_01_31.implementation.ConsumptionManager;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Type representing Budget.
 */
public interface Budget extends HasInner<BudgetInner>, Indexable, Updatable<Budget.Update>, Refreshable<Budget>, HasManager<ConsumptionManager> {
    /**
     * @return the amount value.
     */
    BigDecimal amount();

    /**
     * @return the category value.
     */
    CategoryType category();

    /**
     * @return the currentSpend value.
     */
    CurrentSpend currentSpend();

    /**
     * @return the eTag value.
     */
    String eTag();

    /**
     * @return the filters value.
     */
    Filters filters();

    /**
     * @return the id value.
     */
    String id();

    /**
     * @return the name value.
     */
    String name();

    /**
     * @return the notifications value.
     */
    Map<String, Notification> notifications();

    /**
     * @return the timeGrain value.
     */
    TimeGrainType timeGrain();

    /**
     * @return the timePeriod value.
     */
    BudgetTimePeriod timePeriod();

    /**
     * @return the type value.
     */
    String type();

    /**
     * The entirety of the Budget definition.
     */
    interface Definition extends DefinitionStages.Blank, DefinitionStages.WithResourceGroupName, DefinitionStages.WithAmount, DefinitionStages.WithCategory, DefinitionStages.WithTimeGrain, DefinitionStages.WithTimePeriod, DefinitionStages.WithCreate {
    }

    /**
     * Grouping of Budget definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a Budget definition.
         */
        interface Blank extends WithResourceGroupName {
        }

        /**
         * The stage of the budget definition allowing to specify ResourceGroupName.
         */
        interface WithResourceGroupName {
           /**
            * Specifies resourceGroupName.
            * @param resourceGroupName Azure Resource Group Name
            * @return the next definition stage
            */
            WithAmount withResourceGroupName(String resourceGroupName);
        }

        /**
         * The stage of the budget definition allowing to specify Amount.
         */
        interface WithAmount {
           /**
            * Specifies amount.
            * @param amount The total amount of cost to track with the budget
            * @return the next definition stage
            */
            WithCategory withAmount(BigDecimal amount);
        }

        /**
         * The stage of the budget definition allowing to specify Category.
         */
        interface WithCategory {
           /**
            * Specifies category.
            * @param category The category of the budget, whether the budget tracks cost or usage. Possible values include: 'Cost', 'Usage'
            * @return the next definition stage
            */
            WithTimeGrain withCategory(CategoryType category);
        }

        /**
         * The stage of the budget definition allowing to specify TimeGrain.
         */
        interface WithTimeGrain {
           /**
            * Specifies timeGrain.
            * @param timeGrain The time covered by a budget. Tracking of the amount will be reset based on the time grain. Possible values include: 'Monthly', 'Quarterly', 'Annually'
            * @return the next definition stage
            */
            WithTimePeriod withTimeGrain(TimeGrainType timeGrain);
        }

        /**
         * The stage of the budget definition allowing to specify TimePeriod.
         */
        interface WithTimePeriod {
           /**
            * Specifies timePeriod.
            * @param timePeriod Has start and end date of the budget. The start date must be first of the month and should be less than the end date. Budget start date must be on or after June 1, 2017. Future start date should not be more than three months. Past start date should  be selected within the timegrain period. There are no restrictions on the end date
            * @return the next definition stage
            */
            WithCreate withTimePeriod(BudgetTimePeriod timePeriod);
        }

        /**
         * The stage of the budget definition allowing to specify ETag.
         */
        interface WithETag {
            /**
             * Specifies eTag.
             * @param eTag eTag of the resource. To handle concurrent update scenario, this field will be used to determine whether the user is updating the latest version or not
             * @return the next definition stage
             */
            WithCreate withETag(String eTag);
        }

        /**
         * The stage of the budget definition allowing to specify Filters.
         */
        interface WithFilters {
            /**
             * Specifies filters.
             * @param filters May be used to filter budgets by resource group, resource, or meter
             * @return the next definition stage
             */
            WithCreate withFilters(Filters filters);
        }

        /**
         * The stage of the budget definition allowing to specify Notifications.
         */
        interface WithNotifications {
            /**
             * Specifies notifications.
             * @param notifications Dictionary of notifications associated with the budget. Budget can have up to five notifications
             * @return the next definition stage
             */
            WithCreate withNotifications(Map<String, Notification> notifications);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allows
         * for any other optional settings to be specified.
         */
        interface WithCreate extends Creatable<Budget>, DefinitionStages.WithETag, DefinitionStages.WithFilters, DefinitionStages.WithNotifications {
        }
    }
    /**
     * The template for a Budget update operation, containing all the settings that can be modified.
     */
    interface Update extends Appliable<Budget>, UpdateStages.WithETag, UpdateStages.WithFilters, UpdateStages.WithNotifications {
    }

    /**
     * Grouping of Budget update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the budget update allowing to specify ETag.
         */
        interface WithETag {
            /**
             * Specifies eTag.
             * @param eTag eTag of the resource. To handle concurrent update scenario, this field will be used to determine whether the user is updating the latest version or not
             * @return the next update stage
             */
            Update withETag(String eTag);
        }

        /**
         * The stage of the budget update allowing to specify Filters.
         */
        interface WithFilters {
            /**
             * Specifies filters.
             * @param filters May be used to filter budgets by resource group, resource, or meter
             * @return the next update stage
             */
            Update withFilters(Filters filters);
        }

        /**
         * The stage of the budget update allowing to specify Notifications.
         */
        interface WithNotifications {
            /**
             * Specifies notifications.
             * @param notifications Dictionary of notifications associated with the budget. Budget can have up to five notifications
             * @return the next update stage
             */
            Update withNotifications(Map<String, Notification> notifications);
        }

    }
}