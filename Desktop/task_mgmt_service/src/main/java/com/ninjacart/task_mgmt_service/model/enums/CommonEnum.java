package com.ninjacart.task_mgmt_service.model.enums;

public class CommonEnum {


    public static final int NO_RETURNS_WARNING_VISIT = 19; //PROCESS_TASK Id
    public static final int NO_RETURNS_WARNING_DEACTIVATE = 20; //PROCESS_TASK Id
    public static final int SKU_PENETRATION = 5;
    public static final int RETURNS = 13;
    // Exotel Calls
    public static final int CALL_STATUS_INVALID = 0;
    public static final int CALL_STATUS_PENDING = 1;
    public static final int CALL_STATUS_CALL_LATER = 2;
    public static final int CALL_STATUS_NOT_REACHABLE = 3;
    public static final int CALL_STATUS_PICKEDUP = 4;
    public static final int CALL_STATUS_ACCEPTED = 5;
    public static final int CALL_STATUS_DROP_LEAD = 6;
    public static final int CALL_STATUS_CALLING = 7;
    public static final int CALL_STATUS_SYSTEM_COMPLETED = 8;
    public static final int CALL_STATUS_HANGUP = 10;
    public static final int TICKET_PRIORITY_LOW = 1;
    public static final int TICKET_PRIORITY_MEDIUM = 5;
    public static final int TICKET_PRIORITY_HIGH = 10;
    public static final int CUSTOMER_DELIVERY_DELAY_CALL = 23;
    public static final int CUSTOMER_DELIVERY_DELAY_VISIT = 24;
    public static final int CUSTOMER_DELIVERY_DELAY_ESCALATE = 25;
    public static final int HIGHLY_UNSATISFIED = 76;
    public static final int ONE_WEEK_IN_MINUTES = 10080;
    public static final int TICKET_DRIVER_MISSED_CALL = 12;
    public static final int TICKET_TRANSIT_DELAY = 14;
    public static final int TICKET_LONG_HALT = 15;
    public static final int TICKET_CUSTOMER_RETURNS = 19;
    public static final int QR_SCAN_STATUS_OVERRIDE = 2;
    public static final int SYSTEM_USER = 1;
    public static final String AUTO_ASSIGN_LOCK_KEY = "AUTO_ASSIGN";
    public static final String USER_DMT_ROLE_CACHE_KEY = "DMT";
    public static final String USER_SRM_SALES_ROLE_CACHE_KEY = "SRM_SALES";
    public static final String USER_SRM_PROCUREMENT_ROLE_CACHE_KEY = "SRM_PROCUREMENT";
    public static final String USER_SRM_SALES_APPROVAL_ROLE_CACHE_KEY = "SRM_SALES_APPROVAL";
    public static final String USER_CALL_EXECUTIVE_RETURNS_ROLE_CACHE_KEY = "CALL_EXECUTIVE_RETURNS";
    public static final String USER_CRATES_EXECUTIVE_ROLE_CACHE_KEY = "CRATE_EXECUTIVE";
    public static String SYSTEM_GENERATED_PROCESS_TASK_NAME = "[TICKET]Auto Generated Instance Task";
    public static String SYSTEM_GENERATED_PROCESS_TASK_DESCRIPTION = "Created for the ";
    public static String asgard_schema_pattern = "asgard.";
    public static String workflow_schema_pattern = "workflow.";
    public static String ESCALATE = "escalate";
    public static String FORCE_ASSIGN_CHANNEL = "tech_high_alerts";
    public static final String CONTACT_NUMBER_KEY = "CONTACT_NUMBER";
    public static final String CALL_LOCK_KEY = "CALL";

    public static final int CUSTOMER_APPROVAL_REJECTED = 0;
    public static final int CUSTOMER_APPROVAL_RAISED = 1;
    public static final int CUSTOMER_APPROVAL_ACCEPTED = 2;
    public static final int CUSTOMER_SELF_ON_BOARD_TYPE = 1;
    public static final int delayRejectIvrCallMinutes = 120;
    public static final int retriesRejectIvrCall = 2;
    public static final int PAYMENT_WITH_HOLD_REASON = 268;
    public static final int GRN_WITHHELD_STATUS = 4;
    public static final int TICKETING_FLOW_NO_ACTION = 7;
    public static final String SE_ROLE = "BUSINESS_DEVELOPMENT_EXECUTIVE";
    public static final String TSM_ROLE = "BDE_TERRITORY_SALES_MANAGER";
    public static final String FV_ROLE = "FIELD_VERIFICATION_EXECUTIVE";

    public static final int NEXT_LEVEL_ESCALATION = 1;
    public static final int IRT_ESCALATION_STATUS = 2;
    public static final String NINJA_IRT_ESCALATION_USER = "1327175";

    public static final int TAM = 0;
    public static final int TAM_MANAGER = 1;

    public static final int BANGALORE = 2;
    public static final int AHMEDABAD = 18;
    public static final int HYDERABAD = 13;
    public static final int PUNE = 12;
    public static final int CHENNAI = 3;
    public static final String AUTO_APPROVED = "AUTO_APPROVED";
    public static final int APPROVED = 1;
    public static final int REJECTED = 0;

    public static final int FARMER_ESCALATION_CALL_MINIMUM_DURATION_SECS = 5;
    public static final String CONVOX_TOKEN_KEY = "PROD_MIAGCSqGSIb3DQEHAqCAMIACAQExC";

    public static final String FARMER_TICKET_POSTPONEMENT_COUNT_VARIABLE = "POSTPONED_COUNT";
    public static final String FARMER_TICKET_POSTPONEMENT_BY_VARIABLE = "POSTPONED_BY";

    public static final String LOAN_DISBURSAL_REFERENCE = "LOAN_ID";

    public static final String ESCALATION_WEB_APP = "ESCALATION_WEB_APP";

    public static final String POC_ESCALATION_CREATION_EMAIL = "POC-ESCALATION-CREATION-EMAIL";

    public static final String CMT_ESCALATION_CREATION_EMAIL = "CMT-ESCALATION-CREATION-EMAIL";

    public static final int RETURNS_DISPUTE_PROCESS_TASK_ID = 90;
    public static final int HORECA_RETURNS_DISPUTE_PROCESS_TASK_TYPE = 5;

    public static final int AUCTIONEER_RETURNS_DISPUTE_PROCESS_TASK_TYPE = 6;

}
