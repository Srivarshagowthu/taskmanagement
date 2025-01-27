package com.ninjacart.task_mgmt_service.model.enums;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TeamProcessTaskEnum {


    //    DMT_LML(1, Arrays.asList(62,60,61,58,16,52,31,32,30,33,51,70,71,55,93,94,90,98), "70,43,71,62,60,43,61,58,16,52,31,32,30,33,51,55,93,90,98","Dispatch Monitoring Executive","DMT"),
    DMT_LML(1, ImmutableMap.<Integer, List<Integer>>builder()
            .put(62, Arrays.asList(0))
            .put(60, Arrays.asList(0))
            .put(61, Arrays.asList(0))
            .put(58, Arrays.asList(0))
            .put(16, Arrays.asList(0))
            .put(52, Arrays.asList(0))
            .put(31, Arrays.asList(0))
            .put(32, Arrays.asList(0))
            .put(30, Arrays.asList(0))
            .put(33, Arrays.asList(0))
            .put(51, Arrays.asList(0))
            .put(70, Arrays.asList(0))
            .put(71, Arrays.asList(0))
            .put(55, Arrays.asList(0))
            .put(93, Arrays.asList(0))
            .put(94, Arrays.asList(0,1))
            .put(90, Arrays.asList(0))
            .put(98, Arrays.asList(0))
            .put(115, Arrays.asList(0))
            .put(135, Arrays.asList(0))
            .build(), "70,43,71,62,60,43,61,58,16,52,31,32,30,33,51,55,93,90,98", "Dispatch Monitoring Executive, DISPATCH MONITORING LEAD", "DMT"),
    SRM_SALES(2,ImmutableMap.<Integer, List<Integer>>builder().put(46,Arrays.asList(0,2,3,4))
            .put(47,Arrays.asList(0,1,2))
            .put(56,Arrays.asList(0))
            .put(35,Arrays.asList(0))
            .put(38,Arrays.asList(0))
            .put(36,Arrays.asList(0))
            .put(37,Arrays.asList(0))
            .put(34,Arrays.asList(0))
            .put(39,Arrays.asList(0))
            .put(40,Arrays.asList(0))
            .put(72,Arrays.asList(0))
            .put(91,Arrays.asList(0))
            .put(92,Arrays.asList(0))
            .put(116,Arrays.asList(0,1))
            .put(136,Arrays.asList(0))
//            .put(134,Arrays.asList(0,1))
            .build(), "46,47,56,35,38,36,37,34,39,40","SRM EXECUTIVE,SRM LEAD","SRM_SALES"),
    SRM_PROCUREMENT(3, Arrays.asList(44, 45 , 147), "45,44","SRM PROCUREMENT EXECUTIVE","SRM_PROCUREMENT"),
    //    SRM_SALES_APPROVAL(4, Arrays.asList(), "46,47","SRM SALES EXECUTIVE","SRM_SALES_APPROVAL"),
    CALL_EXECUTIVE_RETURNS(5, Arrays.asList(58), "58","CALL EXECUTIVE RETURNS","CALL_EXECUTIVE_RETURNS"),
    KAM_SALES(6,ImmutableMap.<Integer, List<Integer>>builder().put(17,Arrays.asList(0,6,7))
            .put(29,Arrays.asList(0))
            .put(75,Arrays.asList(0))
            .put(103, Arrays.asList(0)).build(),"17,29,75,103","KAM SALES","KAM_SALES_EXECUTIVE"),

    DMT_CRATE(7, Arrays.asList(43,80), "43","CRATE EXECUTIVE","CRATE_EXECUTIVE"),
    DMT_CRATE_FOLLOWUP(8, Arrays.asList(80,43), "80","CRATE FOLLOWUP EXECUTIVE","CRATE_FOLLOWUP_EXECUTIVE"),
    AUDIT_USER(9, Arrays.asList(81,84), "81,84","AUDIT USER","AUDIT_USER"),
    CENTRAL_INVESTIGATION(10, Arrays.asList(83,89), "83,89","CENTRAL INVESTIGATION","CENTRAL_INVESTIGATION"),
    CIT_ESCALATION(11, Arrays.asList(85), "85","CRATE ESCALATION TEAM","CRATE_ESCALATION_TEAM"),
    DMT_ESCALATION(12, Arrays.asList(86), "86","DMT ESCALATION EXECUTIVE","DMT_ESCALATION_TEAM"),
    DMT_LEDGER(13,Arrays.asList(87),"87","DMT LEDGER TEAM","DMT_LEDGER_TEAM"),
    NERVE_CENTRE(14, Arrays.asList(95),"95", "OPS NC EXECUTIVE", "OPS NC EXECUTIVE"),
    EMPLOYEE_ATTENDANCE_VERIFICATION(15, Arrays.asList(97),"97","EmployeeAtdVeri_Audit_team","EmployeeAtdVeri_Audit_team"),
    CUSTOMER_ATTENDANCE_VERIFICATION(16,Arrays.asList(96),"96","CustomerVeri_Audit_team", "CustomerVeri_Audit_team"),
    PHONE_ORDER_TAKING(17, Arrays.asList(100), "100", "TELE_CALLING_TEAM", "TELE_CALLING_TEAM"),
    DP_QUALITY_CONTROL_TEAM(18, Arrays.asList(109), "109", "DP Quality Control Team", "DP Quality Control Team"),
    SRM_SELF_ONBOARDING(19, ImmutableMap.of(46,Arrays.asList(1)), "","SRM SELF ONBOARDING EXECUTIVE","SRM_SELF_ONBOARDING_EXECUTIVE"),
    CENTRAL_LML_TEAM(20, ImmutableMap.of(112,Arrays.asList(1)), "","CENTRAL LML TEAM","CENTRAL_LML_TEAM"),
    CAPACITY_TEAM(21, ImmutableMap.of(112,Arrays.asList(2)), "","CAPACITY TEAM","CAPACITY_TEAM"),
    CMT_TEAM(22, ImmutableMap.of(113,Arrays.asList(2)), "","CMT TEAM","CMT_TEAM"),
    //    DMT_LML_DRIVER_CUSTOMER_TEAM(23, ImmutableMap.of(115, Arrays.asList(0)), "", "DMT_Driver_Cux", "DMT_LML_DRIVER_CUSTOMER_TEAM"),
    ORDER_TAKING(24,ImmutableMap.<Integer, List<Integer>>builder().put(17,Arrays.asList(1)).build(),"17","KAM TELE-CALLING","KAM_TELE_CALLING"),
    NLML_TRIP_TRACKING(25, Arrays.asList(117), "", "NLML TRIP TRACKING", "NLML TRIP TRACKING"),
    DMT_SELF_ONBOARD(26, ImmutableMap.<Integer, List<Integer>>builder()
            .put(90, Arrays.asList(1))
            .put(93, Arrays.asList(1))
            .put(94, Arrays.asList(2,3))
            .put(124,Arrays.asList(0))
            .build(), "", "DMT Self Onboard", "DMT_SELF_ONBOARD"),
    NLML_VEHICLE_RFID(27, Arrays.asList(119,120,126), "", "NLML VEHICLE RFID", "NLML VEHICLE RFID"),
    CRM_ESCALATION_TEAM(28, ImmutableMap.of(17, Arrays.asList(5)), "", "Customer Escalation Feedback Agent", "CRM_ESC_AGENT"),
    CUSTOMER_BLOCKING_APPROVER(29, ImmutableMap.of(121, Arrays.asList(0)), "", "Customer Blocking Approver", "CUSTOMER_BLOCKING_APPROVER"),
    DMT_GROCERY(30, ImmutableMap.<Integer, List<Integer>>builder()
            .put(93, Arrays.asList(2))
            .put(122, Arrays.asList(0))
            .build(), "", "GroceryDMT", "Dmt_Grocery"),
    CRM_SRM(33, ImmutableMap.<Integer, List<Integer>>builder()
            .put(17,Arrays.asList(0,1,6,7))
            .put(29,Arrays.asList(0))
            .put(75,Arrays.asList(0))
            .put(103, Arrays.asList(0))
            .put(46,Arrays.asList(0,2,3,4))
            .put(47,Arrays.asList(0,1,2))
            .put(56,Arrays.asList(0))
            .put(35,Arrays.asList(0))
            .put(38,Arrays.asList(0))
            .put(36,Arrays.asList(0))
            .put(37,Arrays.asList(0))
            .put(34,Arrays.asList(0))
            .put(39,Arrays.asList(0))
            .put(40,Arrays.asList(0))
            .put(72,Arrays.asList(0))
            .put(91,Arrays.asList(0))
            .put(92,Arrays.asList(0))
            .put(116,Arrays.asList(0,1))
            .put(136,Arrays.asList(0))
            .build(), "", "KamSales_TeleCalling_SrmExec", "CRM_SRM"),
    MINIMARKET_PHONE_ORDERTAKING(31, ImmutableMap.of(17, Arrays.asList(9,10,11, 12)), "", "MINIMARKET_PHONE_ORDERTAKING", "MINIMARKET_PHONE_ORDERTAKING"),
    FARMER_CALLING(32, Arrays.asList(127, 128), "", "FARMER_CALLING_TEAM", "FARMER_CALLING"),
    FACILITY_CLOSURE_NERVE_CENTER(35,ImmutableMap.<Integer, List<Integer>>builder()
            .put(130, Arrays.asList(0))
            .put(139, Arrays.asList(5))
            .put(140, Arrays.asList(4)).build()
            , "", "FACILITY_CLOSURE_NERVE_CENTER", "FACILITY_CLOSURE_NERVE_CENTER"),
    FACILITY_CLOSURE_CENTRAL_TEAM(36, ImmutableMap.of(131, Arrays.asList(1)), "", "FACILITY_CLOSURE_CENTRAL_TEAM", "FACILITY_CLOSURE_CENTRAL_TEAM"),
    FACILITY_CLOSURE_CRATES_TEAM(37, ImmutableMap.of(132, Arrays.asList(2)), "", "FACILITY_CLOSURE_CRATES_TEAM", "FACILITY_CLOSURE_CRATES_TEAM"),
    FACILITY_CLOSURE_FINANCE_TEAM(38, ImmutableMap.of(133, Arrays.asList(3)), "", "FACILITY_CLOSURE_FINANCE_TEAM", "FACILITY_CLOSURE_FINANCE_TEAM"),
    CAMPAIGN_ORDER_TAKING(39, ImmutableMap.of(17, Arrays.asList(16)), "", "Campaign Order Taking", "CAMPAIGN_ORDER_TAKING"),
    FLCM_TELECALLING_TEAM(40, Arrays.asList(137,138, 143), "", "FLCM Telecalling Team", "FLCM_TELECALLING_TEAM"),
    FACILITY_CREATION_LEGAL_HEAD(41,ImmutableMap.of(139, Arrays.asList(2)),"","Facility_Creation_Legal_Head","FACILITY_CREATION_LEGAL_HEAD("),
    FACILITY_CREATION_CENTRAL_CAPACITY_HEAD(42,ImmutableMap.of(139, Arrays.asList(4)),"","Fac_Creation_Central_Capacity_Head","FACILITY_CREATION_CENTRAL_CAPACITY_HEAD"),
    FACILITY_CLOSURE_IA_TEAM(43,ImmutableMap.of(140, Arrays.asList(3)),"","FACILITY_CLOSURE_IA_TEAM","FACILITY_ACTIVATION_INTERNAL_AUDIT_HEAD"),
    KALEYRA_ORDER_TAKING(44, ImmutableMap.of(17, Collections.singletonList(17)), "", "IVR_Order_Taking", "IVR_ORDER_TAKING"),
    FARMER_VERIFICATION(45, Arrays.asList(146, 149), "", "Farmer Verification Ticket - FLM", "Farmer_Verification_Ticket_FLM"),
    FARM_NO_OTP_TEAM(46, Collections.singletonList(148), "", "FARM_PICK_NOOTP", "FARM_PICK_NOOTP"),
    CONVOX_C2C_ACCESS(47, Collections.emptyList(), "", "CONVOX_C2C", "CONVOX_CALL"),
    KYC_VERIFICATION(48, ImmutableMap.<Integer, List<Integer>>builder()
            .put(150, Collections.singletonList(0))
            .put(165, Collections.singletonList(0))
            .build(), "", "KYC_VERIFICATION", "KYC_VERIFICATION"),
    SRM_TRADER_ONBOARD(49, Collections.singletonList(152), "", "SRM_TRADER_ONBOARD", "SRM_TRADER_ONBOARD"),
    KYC_TRDAERAPP_VERIFICATION(50, ImmutableMap.<Integer, List<Integer>>builder()
            .put(150, Collections.singletonList(1))
            .build(), "", "KYC_TRADERAPP_VERIFICATION", "KYC_TRADERAPP_VERIFICATION"),
    KYC_COLLECT_ASSURE_VERIFICATION(51, ImmutableMap.of(150, Collections.singletonList(2)), "", "KYC_COLLECT_ASSURE_VERIFICATION", "KYC_COLLECT_ASSURE_VERIFICATION"),
    LOAN_DISBURSAL_REQUEST(52, ImmutableMap.of(154, Collections.singletonList(0)), "", "TRADER_LOAN_MAKER", "TRADER_LOAN_MAKER"),
    LOAN_DISBURSAL_VERIFICATION(53, ImmutableMap.of(154, Collections.singletonList(1)), "", "TRADER_LOAN_CHECKER", "TRADER_LOAN_CHECKER"),
    COLLECT_ASSURE_PLAN_APPROVAL(54, ImmutableMap.of(155, Collections.singletonList(0)), "", "COLLECT_ASSURE_PLAN_APPROVAL", "COLLECT_ASSURE_PLAN_APPROVAL"),
    LOAN_PERSONAL_DISCUSSION(55, ImmutableMap.of(156, Collections.singletonList(0)), "", "CREDIT_MANAGER", "CREDIT_MANAGER"),
    BUYER_VERIFICATION(56, ImmutableMap.<Integer, List<Integer>>builder()
            .put(158, Arrays.asList(0,1))
            .put(166, Collections.singletonList(0))
            .build(), "", "BUYER_VERIFICATION", "BUYER_VERIFICATION"),
    PG_ONBOARDING(57, ImmutableMap.of(159, Collections.singletonList(0)), "", "PG_ONBOARDING", "PG_ONBOARDING"),
    DMT_B2C_RETURNS(58, ImmutableMap.of(90, Collections.singletonList(3)), "", "DMT_B2C_RETURNS", "DMT_B2C_RETURNS"),
    PG_MAKER(59, ImmutableMap.of(162, Collections.singletonList(0)), "", "PG_MAKER", "PG_MAKER"),
    //    KYC_VERIFICATION_V1(60, ImmutableMap.of(165, Collections.singletonList(0)), "", "KYC_VERIFICATION", "KYC_VERIFICATION"),
    PG_MAKER_2(60, ImmutableMap.of(162, Collections.singletonList(1)), "", "PG_MAKER_2", "PG_MAKER_2"),
    KYC_UNNATI_BOB_VERIFICATION(61, ImmutableMap.of(150, Collections.singletonList(3)), "", "KYC_UNNATI_BOB_VERIFICATION", "KYC_UNNATI_BOB_VERIFICATION"),
    PG_ONBOARDING_1(62, ImmutableMap.of(159, Collections.singletonList(1)), "", "PG_ONBOARDING", "PG_ONBOARDING"),
    PG_ONBOARDING_2(63, ImmutableMap.of(159, Collections.singletonList(2)), "", "PG_ONBOARDING", "PG_ONBOARDING"),
    PG_ONBOARDING_3(64, ImmutableMap.of(159, Collections.singletonList(3)), "", "PG_ONBOARDING", "PG_ONBOARDING"),
    DISBURSAL_OPS_LOAN_DISBURSAL_REQUEST(65, ImmutableMap.of(154, Collections.singletonList(2)), "", "DISBURSAL_OPS_LOAN_MAKER", "DISBURSAL_OPS_LOAN_MAKER"),
    DISBURSAL_OPS_LOAN_DISBURSAL_VERIFICATION(66, ImmutableMap.of(154, Collections.singletonList(3)), "", "DISBURSAL_OPS_LOAN_CHECKER", "DISBURSAL_OPS_LOAN_CHECKER"),
    KYC_NINJA_GLOBAL_ONE(67, ImmutableMap.of(150, Collections.singletonList(4)), "", "NINJA_GLOBAL_ONE_LOAN", "NINJA_GLOBAL_ONE_LOAN"),
    ONDC_CATALOGUE(68, Collections.singletonList(176), "", "ONDC_CRM_CATALOGUE", "ONDC_CRM_CATALOGUE"),
    ONDC_SELLER_SUPPORT(69, Collections.singletonList(180), "", "ONDC_SELLER_SUPPORT", "ONDC_SELLER_SUPPORT"),
    ONDC_CATALOGUE_VERIFICATION(70, ImmutableMap.of(176, Collections.singletonList(1)), "", "ONDC_CATALOGUE_VERIFICATION", "ONDC_CATALOGUE_VERIFICATION"),
    BANK_ACCOUNT_VERIFIER(71, ImmutableMap.of(181, Collections.singletonList(0)), "", "BANK_ACCOUNT_VERIFIER", "BANK_ACCOUNT_VERIFIER"),
    ONDC_ORDER_MODIFICATION(72, ImmutableMap.of(186, Collections.singletonList(0)), "", "ONDC_ORDER_MODIFICATION", "ONDC_ORDER_MODIFICATION"),
    ONDC_ORDER_CANCELLATION(73, ImmutableMap.of(186, Collections.singletonList(1)), "", "ONDC_ORDER_CANCELLATION", "ONDC_ORDER_CANCELLATION"),
    ONDC_CHATBOT_TICKET(74, ImmutableMap.of(186, Collections.singletonList(2)), "", "ONDC_CHATBOT_TICKET", "ONDC_CHATBOT_TICKET"),
    ORDER_FEEDBACK_TICKET(75, ImmutableMap.of(191, Arrays.asList(0,1)), "", "ORDER_FEEDBACK_TICKET", "ORDER_FEEDBACK_TICKET"),
    NK_QNA_MODERATOR(76, ImmutableMap.of(194, Collections.singletonList(0)), "", "NK_QNA_MODERATOR", "NK_QNA_MODERATOR"),
    NK_QNA_APP_MODERATOR(77, ImmutableMap.of(195, Collections.singletonList(0)), "", "NK_QNA_APP_MODERATOR", "NK_QNA_APP_MODERATOR"),
    NK_VIDEO_MODERATOR(78, ImmutableMap.of(196, Collections.singletonList(0)), "", "NK_VIDEO_MODERATOR", "NK_VIDEO_MODERATOR"),
    NK_FEED_MODERATOR(79, ImmutableMap.of(197, Collections.singletonList(0)), "", "NK_FEED_MODERATOR", "NK_FEED_MODERATOR"),
    NK_NEWS_MODERATOR(80, ImmutableMap.of(198, Collections.singletonList(0)), "", "NK_NEWS_MODERATOR", "NK_NEWS_MODERATOR"),
    NON_INITIATED_PAYMENTS_TICKET(81, ImmutableMap.of(218, Arrays.asList(0)), "", "NON_INITIATED_PAYMENTS_TICKET", "NON_INITIATED_PAYMENTS_TICKET"),
    NON_REINITIATED_PAYMENTS_TICKET(82, ImmutableMap.of(218, Arrays.asList(1)), "", "NON_REINITIATED_PAYMENTS_TICKET", "NON_REINITIATED_PAYMENTS_TICKET"),
    WITHHELD_ESCALATION_PAYMENTS_TICKET(83, ImmutableMap.of(218, Arrays.asList(2)), "", "WITHHELD_ESCALATION_PAYMENTS_TICKET", " WITHHELD_ESCALATION_PAYMENTS_TICKET"),
    WITHHELD_ESCALATION_PAYMENTS_TICKET_L2(84, ImmutableMap.of(218, Arrays.asList(3)), "", "WITHHELD_ESCALATION_PAYMENTS_TICKET_L2", "WITHHELD_ESCALATION_PAYMENTS_TICKET_L2"),
    CREDIT_DEBIT_NOTE_WITHHELD_ESCALATION_TICKET(85, ImmutableMap.of(218, Arrays.asList(4)), "", "CREDIT_DEBIT_NOTE_WITHHELD_ESCALATION_TICKET", "CREDIT_DEBIT_NOTE_WITHHELD_ESCALATION_TICKET"),
    DMT_HORECA_B2C_RETURNS(86, ImmutableMap.of(90, Collections.singletonList(5)), "", "", ""),
    DMT_AUCTIONEER_RETURNS(87, ImmutableMap.of(90, Collections.singletonList(6)), "", "", "");

    private int type;
    private List<Integer> processTaskIds;
    private ImmutableMap<Integer, List<Integer>> processTaskTypeMap;
    private String priority;
    private String roles;
    private String cacheKey;

    TeamProcessTaskEnum(int type, List<Integer> processTaskIds, String priority, String roles, String cacheKey) {
        this.type = type;
        this.processTaskIds = processTaskIds;
        this.priority = priority;
        this.roles = roles;
        this.cacheKey = cacheKey;
    }

    TeamProcessTaskEnum(int type, ImmutableMap<Integer, List<Integer>> processTaskTypeMap, String priority, String roles, String cacheKey) {
        this.type = type;
        this.processTaskTypeMap = processTaskTypeMap;
        this.priority = priority;
        this.roles = roles;
        this.cacheKey = cacheKey;
    }

    public static TeamProcessTaskEnum getTeamProcessTaskEnumByType(int type) {
        TeamProcessTaskEnum teamProcessTaskEnum = Arrays.stream(TeamProcessTaskEnum.values()).filter(each -> each.getType() == type).findFirst().orElse(null);
        if(teamProcessTaskEnum == null){
            return DMT_LML;
        }
        return teamProcessTaskEnum;
    }

    public int getType() {
        return type;
    }

    public List<Integer> getProcessTaskIds() {
        return processTaskIds;
    }

    public ImmutableMap<Integer, List<Integer>> getProcessTaskTypeMap() {
        return processTaskTypeMap;
    }

    public String getPriority() {
        return priority;
    }

    public String getRoles() {
        return roles;
    }

    public String getCacheKey() {
        return cacheKey;
    }
}
