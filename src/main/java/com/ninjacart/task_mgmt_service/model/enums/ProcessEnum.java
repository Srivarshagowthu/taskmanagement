package com.ninjacart.task_mgmt_service.model.enums;


import java.util.Arrays;
import java.util.List;

public enum ProcessEnum {

    VENDOR_NO_SHOW(6, "Vendor No Show For PurchaseOrder",(TeamProcessTaskEnum) null),
    VENDOR_NO_RESPONSE(7, "Vendor No Response",(TeamProcessTaskEnum) null),
    VENDOR_QUALITY_CHECK(8, "Vendor Quality Check",(TeamProcessTaskEnum) null),
    VENDOR_NO_INDENT(9, "Vendor NO Indent",(TeamProcessTaskEnum)  null),
    CUSTOMER_TRIP_SHOP_CLOSED(10, "Customer Trip Shop Closed", TeamProcessTaskEnum.DMT_LML),
    CUSTOMER_MISSED_CALL(11, "Customer Missed Call", Arrays.asList(TeamProcessTaskEnum.KAM_SALES,
            TeamProcessTaskEnum.ORDER_TAKING,
            TeamProcessTaskEnum.CRM_ESCALATION_TEAM,
            TeamProcessTaskEnum.CRM_SRM,
            TeamProcessTaskEnum.MINIMARKET_PHONE_ORDERTAKING,
            TeamProcessTaskEnum.CAMPAIGN_ORDER_TAKING,
            TeamProcessTaskEnum.KALEYRA_ORDER_TAKING)),
    DRIVER_MISSED_CALL(12, "Driver Missed Call",(TeamProcessTaskEnum) null),
    TRANSIT_DELAY(14, "Transit Delay",(TeamProcessTaskEnum) null),
    LONG_HALT(15, "Long halt",(TeamProcessTaskEnum) null),
    VENDOR_NO_INDENT_PRICE(17, "Vendor NO Indent Price",(TeamProcessTaskEnum) null),
    VENDOR_NO_INDENT_PAYMENT(18, "Vendor NO Indent Payment",(TeamProcessTaskEnum) null),
    CUSTOMER_RETURNS(19, "Customer Returns", TeamProcessTaskEnum.KAM_SALES),
    DRIVER_SUPPORT(20, "Driver Support", TeamProcessTaskEnum.DMT_LML),
    CUSTOMER_SHOP_NOT_FOUND(21, "Customer Shop Not Found", TeamProcessTaskEnum.DMT_LML),
    GEO_FENCE(22, "GeoFence", TeamProcessTaskEnum.DMT_LML),
    DRIVER_SERVICE_TIME(23, "Driver Service Time", TeamProcessTaskEnum.DMT_LML),
    DRIVER_VIOLATIONS(24, "Driver Violations", TeamProcessTaskEnum.SRM_SALES),
    DRIVER_PAYMENT_ISSUE(25, "Driver Payment Issue", TeamProcessTaskEnum.SRM_SALES),
    DRIVER_OTHER_ISSUE(26, "Driver Other Issue", TeamProcessTaskEnum.SRM_SALES),
    TRIP_COST_NOT_FROZEN(27, "Trip Cost Not Frozen", TeamProcessTaskEnum.SRM_SALES),
    DRIVER_BLOCK(28, "Driver Block", TeamProcessTaskEnum.SRM_SALES),
    CRATES_MISMATCH(29, "Crates Mismatch", TeamProcessTaskEnum.DMT_CRATE),
    VENDOR_MISSED_CALL(30, "Vendor Missed Call", TeamProcessTaskEnum.SRM_PROCUREMENT),
    VENDOR_APPROVAL(31, "Vendor Approval", TeamProcessTaskEnum.SRM_PROCUREMENT),
    CUSTOMER_APPROVAL(32, "Customer Approval", Arrays.asList(TeamProcessTaskEnum.SRM_SALES,TeamProcessTaskEnum.SRM_SELF_ONBOARDING, TeamProcessTaskEnum.CRM_SRM)),
    CUSTOMER_VISIT_PLAN(33, "Customer Visit Plan",(TeamProcessTaskEnum) null),
    CUSTOMER_EDIT_APPROVAL(34, "Customer Edit Approval", Arrays.asList(TeamProcessTaskEnum.SRM_SALES, TeamProcessTaskEnum.CRM_SRM)),
    DC_SERVICE_TIME(37, "DC Service Time", TeamProcessTaskEnum.DMT_LML),
    ITEM_MISSED(38, "Item Missed", TeamProcessTaskEnum.DMT_LML),
    COLLECTION_PLAN(39, "Collection Plan",(TeamProcessTaskEnum) null),
    TRANSPORT_PARTNER_COST_FREEZE(40, "Transport Partner Cost Freeze", TeamProcessTaskEnum.SRM_SALES),
    VISIT_SUPPORT(42, "Visit Support",  TeamProcessTaskEnum.SRM_SALES),
    CUSTOMER_SHOP_CLOSED(43, "Customer Shop Closed",  TeamProcessTaskEnum.SRM_SALES),
    COLLECTION_PLAN_V2(44, "Collection Plan V2",(TeamProcessTaskEnum) null),
    CUSTOMER_PARTIAL_PAYMENT(45, "Customer Partial Payment", TeamProcessTaskEnum.CALL_EXECUTIVE_RETURNS),
    CORPORATE_GEO_FENCE(46, "Corporate Geo Fence", TeamProcessTaskEnum.DMT_LML),
    TRIP_SHOP_OTP_ASSISTANCE(47, "Trip Shop OTP Assistance", TeamProcessTaskEnum.DMT_LML),
    INVOICE_DISPUTE(48, "Invoice Dispute", TeamProcessTaskEnum.DMT_LML),
    LOYAL_FARMER_PO_TIME_VIOLATION(49,"Loyal Farmer purchase order created post cutoff time",(TeamProcessTaskEnum)null),
    LOYAL_FARMER_PO_NOT_CREATED(50,"Loyal Farmer PO not created ",(TeamProcessTaskEnum)null),
    LOYAL_FARMER_PO_CREATED_WITHOUT_INDENT(51,"Loyal farmers PO got created without having indent",(TeamProcessTaskEnum)null),
    LOYAL_FARMER_PO_QUANTITY_POST_THRESHOLD(52,"Loyal farmer PO created and greater lesser than 50% variance with planned harvest",(TeamProcessTaskEnum)null),
    LOYAL_FARMER_SUPPLY_QUANTITY_BREACH(53,"Loyal farmer supply quantity > OR < by %20 and also committed Indent but did not deliver",(TeamProcessTaskEnum)null),
    LOYAL_FARMER_EXPECTED_SHOW(54,"Loyal farmer supplied inspite of no indent or planned harvest",(TeamProcessTaskEnum)null),
    LOYAL_FARMER_GRN_QUANTITY_BREACH(55,"When supplied quantity > OR < PO by 50%",(TeamProcessTaskEnum)null),
    INVOICE_DISPUTE_V2(56, "Invoice Dispute V2", TeamProcessTaskEnum.DMT_LML),
    TRIP_SHOP_OTP_ASSISTANCE_V2(57, "Trip Shop OTP Assistance V2", TeamProcessTaskEnum.DMT_LML),
    PARTIAL_PAYMENT(58, "Partial Payment", TeamProcessTaskEnum.SRM_SALES),
    SE_VISIT_CUSTOMER_SHOP_CLOSED(59, "SE visit to customer for collection and shop is closed",(TeamProcessTaskEnum) null),
    SE_VISIT_PARTIAL_PAYMENT(60, "SE visit to customer for collection and does partial payment",(TeamProcessTaskEnum) null),
    CUSTOMER_OTP_ASSISTANCE(61,"customer did not receive otp", TeamProcessTaskEnum.KAM_SALES),
    CUSTOMER_DAILY_ORDER_CALL(65,"Customer Daily Order Call", TeamProcessTaskEnum.KAM_SALES),
    CUSTOMER_CRATE_FOLLOWUP(66,"Customer Followup Crate", TeamProcessTaskEnum.DMT_CRATE_FOLLOWUP),
    DELIVERY_AUDIT(67,"Delivery Audit", TeamProcessTaskEnum.AUDIT_USER),
    CUSTOMER_CREDIT_REQUEST(68, "Customer Credit Request", TeamProcessTaskEnum.SRM_SALES),
    CRM_ESCALATION(69,"Crm Escalation", TeamProcessTaskEnum.CENTRAL_INVESTIGATION),
    PICKUP_AUDIT(70,"Pickup Audit", TeamProcessTaskEnum.AUDIT_USER),
    CUSTOMER_CRATE_FOLLOWUP_ESCALATION(71,"CIT Escalation", TeamProcessTaskEnum.CIT_ESCALATION),
    LEDGER_VERIFICATION(72,"Ledger Verification", TeamProcessTaskEnum.DMT_LEDGER),
    LEDGER_DISPUTE_ESCALATION(73,"Ledger Dispute Escalation", TeamProcessTaskEnum.DMT_LML),
    CENTRAL_INVESTIGATION(74,"Central Investigation", TeamProcessTaskEnum.CENTRAL_INVESTIGATION),
    RETURNS_DISPUTE(75, "Returns Dispute", Arrays.asList(TeamProcessTaskEnum.DMT_LML, TeamProcessTaskEnum.DMT_SELF_ONBOARD, TeamProcessTaskEnum.DMT_B2C_RETURNS)),
    CUSTOMER_DEPOSIT_REQUEST(76, "Customer Deposit Request", TeamProcessTaskEnum.SRM_SALES),
    EMPLOYEE_SUPPORT(77,"Employee Support", TeamProcessTaskEnum.SRM_SALES),
    MDC_CUSTOMER_SHOP_CLOSED(78,"Customer Shop Closed while Delivery from MDC", Arrays.asList(TeamProcessTaskEnum.DMT_LML, TeamProcessTaskEnum.DMT_SELF_ONBOARD, TeamProcessTaskEnum.DMT_GROCERY)),
    MDC_CUSTOMER_NO_PASS_CODE(79, "Pass code not received for customer", Arrays.asList(TeamProcessTaskEnum.DMT_LML, TeamProcessTaskEnum.DMT_SELF_ONBOARD)),
    OPS_BLOCKER_SUPPORT(80, "Ops Blocker Support", TeamProcessTaskEnum.NERVE_CENTRE),
    AUDIT_CUSTOMER_VERIFICATION(81, "Customer Audit Verification", TeamProcessTaskEnum.CUSTOMER_ATTENDANCE_VERIFICATION),
    AUDIT_EMPLOYEE_VERIFICATION(82,"Employee Audit Verification",TeamProcessTaskEnum.EMPLOYEE_ATTENDANCE_VERIFICATION),
    UPSELLING_SUPPORT_HANDLER(83, "UpSelling Support", TeamProcessTaskEnum.DMT_LML),
    SALEORDER_CONFIRMATION(84,"SaleOrder Confirmation", (TeamProcessTaskEnum) null),
    CUSTOMER_PHONE_CALL_HANDLER(85, "Customer phone call ", TeamProcessTaskEnum.PHONE_ORDER_TAKING),
    ON_CALL_SUPPORT(86, "On Call Support",(TeamProcessTaskEnum) null),
    DP_DAILY_EARNING_DISPUTE(87, "Daily Earning Dispute",(TeamProcessTaskEnum) null),
    DP_CASH_DEPOSITION(88, "Cash Deposition",(TeamProcessTaskEnum) null),
    DP_GRN_RFID_NOT_WORKING(89, "RFID not working",(TeamProcessTaskEnum) null),
    DP_GRN_RECEIVING_SHORTAGE(90, "GRN Receiving Shortage",(TeamProcessTaskEnum) null),
    DP_DRIVER_ONBOARDING(91, "Driver Onboarding",(TeamProcessTaskEnum) null),
    DP_DRIVER_TRIP_ASSIGNMENT(92, "Driver Trip Assignment",(TeamProcessTaskEnum) null),
    DP_CUSTOMER_CASH_COLLECTION(93, "Customer Cash Collection",(TeamProcessTaskEnum) null),
    DP_TRIP_CLOSURE(94, "Trip Closure",(TeamProcessTaskEnum) null),
    DP_QUALITY_SUPPORT(95, "DP quality support",(TeamProcessTaskEnum) null),
    FIELD_VERIFICATION( 96, "Customer field verification", (TeamProcessTaskEnum) null),
    EBIKE_SUPPORT( 97, "E-bike Support", (TeamProcessTaskEnum) null),
    CMT_VIOLATION( 98, "CMT violation", TeamProcessTaskEnum.CMT_TEAM),
    NINJA_STAR_SUPPORT(99, "star customer missed call", (TeamProcessTaskEnum) null),
    MDC_PICKUP_ORDER_VERIFICATION(100, "MDC customer pickup order verification", TeamProcessTaskEnum.DMT_LML),
    MINIMARKET_ONBOARDING(101, "Mini market onboarding", Arrays.asList(TeamProcessTaskEnum.SRM_SALES)),
    NLML_TRIP_TRACKING(102, "To escalate trips running late", TeamProcessTaskEnum.NLML_TRIP_TRACKING),
    NLML_VEHICLE_RFID_REMAP(104, "Request for remapping of rgid tags for non lml vehicles", TeamProcessTaskEnum.NLML_VEHICLE_RFID),
    NLML_VEHICLE_RFID_NEW(105, "Request for addition of new rfid tags on non lml vehicles", TeamProcessTaskEnum.NLML_VEHICLE_RFID),
    CUSTOMER_BLOCK_REQUEST(106, "Request for customer block", (TeamProcessTaskEnum) null),
    MDC_CUSTOMER_SHOP_OPEN(107, "MDC grocery customer shop open", TeamProcessTaskEnum.DMT_GROCERY),
    SELF_ONBOARD_CUSTOMER_APPROVAL(109, "Self Onboard Customer Approval",TeamProcessTaskEnum.DMT_SELF_ONBOARD),
    NLML_CAGE_SHORTAGE(111, "To escalate cage shortage in vehicle", TeamProcessTaskEnum.NLML_VEHICLE_RFID),
    FARMER_DROPOUT(112, "On boarding dropout vendors again", TeamProcessTaskEnum.FARMER_CALLING),
    INDENT_CONFIRMATION(113, "Confirming Vendor Indent", TeamProcessTaskEnum.FARMER_CALLING),
    FACILITY_CLOSURE(115, "Ticket to close Inactive Facilities", Arrays.asList(TeamProcessTaskEnum.FACILITY_CLOSURE_NERVE_CENTER,
            TeamProcessTaskEnum.FACILITY_CLOSURE_CENTRAL_TEAM,TeamProcessTaskEnum.FACILITY_CLOSURE_CRATES_TEAM,TeamProcessTaskEnum.FACILITY_CLOSURE_FINANCE_TEAM)),
    PARTNER_AGENT_ONBOARD(116, "Partner Agent Onboard", (TeamProcessTaskEnum) null),
    MDC_SHOP_NOT_FOUND(117, "MDC shop not found", TeamProcessTaskEnum.DMT_LML),
    CUSTOMER_SHOP_NOT_FOUND_FOLLOWUP(118, "Shop not found followup", Arrays.asList(TeamProcessTaskEnum.SRM_SALES, TeamProcessTaskEnum.CRM_SRM)),
    HARVEST_CALL(119, "Harvest Call and PO creation", Arrays.asList( TeamProcessTaskEnum.FLCM_TELECALLING_TEAM)),
    FARMER_WELCOME_CALL(120, "Farmer onBoarding welcome call", TeamProcessTaskEnum.FLCM_TELECALLING_TEAM),
    FACILITY_CREATION(121,"Facility Creation",Arrays.asList(TeamProcessTaskEnum.FACILITY_CREATION_LEGAL_HEAD,
            TeamProcessTaskEnum.FACILITY_CREATION_CENTRAL_CAPACITY_HEAD,TeamProcessTaskEnum.FACILITY_CLOSURE_NERVE_CENTER)),
    FACILITY_ACTIVATION(122,"Facility Activation",Arrays.asList(TeamProcessTaskEnum.FACILITY_CLOSURE_IA_TEAM,
            TeamProcessTaskEnum.FACILITY_CLOSURE_NERVE_CENTER)),
    FARMER_FEEDBACK_CALL(123, "Farmer Feedback Call", TeamProcessTaskEnum.FLCM_TELECALLING_TEAM),
    FARMER_MANUAL_CHAT(124, "Farmer Manual chat", TeamProcessTaskEnum.FARMER_CALLING),
    FARMER_PROFILE_VERIFICATION(126,"Farmer profile verification" , TeamProcessTaskEnum.FARMER_VERIFICATION),
    FARMER_CROP_PROTECTION(127, "Farmer crop protection", TeamProcessTaskEnum.SRM_PROCUREMENT),
    FARM_PICKUP_NO_OTP(128, "No OTP flow during farm pickup in driver app",  TeamProcessTaskEnum.FARM_NO_OTP_TEAM),
    MERCHANT_WELCOME_CALL(129, "Merchant OnBoarding Welcome Call", TeamProcessTaskEnum.FARMER_VERIFICATION),
    KYC_VERIFICATION(130, "KYC verification though kyc s", Arrays.asList(TeamProcessTaskEnum.KYC_VERIFICATION,
            TeamProcessTaskEnum.KYC_TRDAERAPP_VERIFICATION, TeamProcessTaskEnum.KYC_COLLECT_ASSURE_VERIFICATION,
            TeamProcessTaskEnum.KYC_UNNATI_BOB_VERIFICATION, TeamProcessTaskEnum.KYC_NINJA_GLOBAL_ONE)),
    TRADER_APP_SUPPORT(131, "Trader App Support", (TeamProcessTaskEnum) null),
    TRADER_ONBOARD(132, "Trader Onboard", TeamProcessTaskEnum.SRM_TRADER_ONBOARD),
    TRADER_KYC(133, "Trader Kyc", (TeamProcessTaskEnum) null),
    TRADER_LOAN_DISBURSAL_REQUEST(134, "Loan Disbursal Request", TeamProcessTaskEnum.LOAN_DISBURSAL_REQUEST),
    COLLECT_ASSURE_PLAN_APPROVAL(135, "Collect Assure Plan Approval", TeamProcessTaskEnum.COLLECT_ASSURE_PLAN_APPROVAL),
    PERSONAL_DISCUSSION(136,"Personal Discussion", TeamProcessTaskEnum.LOAN_PERSONAL_DISCUSSION),
    BUYER_CPV_VERIFICATION(137, "Buyer CPV Verification", (TeamProcessTaskEnum) null),
    BUYER_VERIFICATION(138,"Buyer SRM Verification", TeamProcessTaskEnum.BUYER_VERIFICATION),
    PG_ONBOARDING(139,"PG Onboarding", TeamProcessTaskEnum.PG_ONBOARDING),
    PG_MAKER(142, "PG maker request", TeamProcessTaskEnum.PG_MAKER),
    PG_MAKER_2(144, "PG maker 2 request", TeamProcessTaskEnum.PG_MAKER),
    KYC_VERIFICATION_V1(145, "KYC verification V1", TeamProcessTaskEnum.KYC_VERIFICATION),
    BUYER_ONBOARD_VERIFICATION(146, "Buyer OnBoard Verification", TeamProcessTaskEnum.BUYER_VERIFICATION),
    DP_WEEKLY_PAYOUTS(147, "Weekly Payouts", (TeamProcessTaskEnum)null),
    DP_MML_ISSUE(148, "MML Issue", (TeamProcessTaskEnum)null),
    DP_ASSETS_ISSUE(149, "Assets Issue", (TeamProcessTaskEnum)null),
    DP_OPERATIONS_SUPPORT(150, "Operations Support", (TeamProcessTaskEnum)null),
    DP_OPERATIONS_SUPPORT_OTHERS(151, "Others", (TeamProcessTaskEnum)null),
    DP_WEIGHT_SHORTAGE(152, "Weight Shortage", (TeamProcessTaskEnum)null),
    ONDC_CATALOGUE(156, "ONDC Crm Catalogue", TeamProcessTaskEnum.ONDC_CATALOGUE),
    ONDC_SELLER_SUPPORT(160, "ONDC Seller Support", TeamProcessTaskEnum.ONDC_SELLER_SUPPORT),
    BANK_ACCOUNT_VERIFICATION(161, "Bank account verification", TeamProcessTaskEnum.BANK_ACCOUNT_VERIFIER),
    RETAILER_REFERRAL_APPROVAL(168,"Retailer Referral Approval",(TeamProcessTaskEnum) null),
    RETAILER_REFERRER_REFERRAL_APPROVAL(169,"Retailer Referrer Referral Approval",(TeamProcessTaskEnum)null),
    ONDC_ORDER_MODIFICATION(165, "ONDC seller modification", TeamProcessTaskEnum.ONDC_ORDER_MODIFICATION),
    ORDER_FEEDBACK_TICKET(171, "ORDER FEEDBACK TICKET", TeamProcessTaskEnum.ORDER_FEEDBACK_TICKET),
    QNA_BOT_TICKET(174, "QnA Bot Ticket", TeamProcessTaskEnum.NK_QNA_MODERATOR),
    QNA_TICKET(175, "QnA Ticket", TeamProcessTaskEnum.NK_QNA_APP_MODERATOR),
    VIDEO_TICKET(176, "Video Ticket", TeamProcessTaskEnum.NK_VIDEO_MODERATOR),
    FEED_TICKET(177, "Feed Ticket", TeamProcessTaskEnum.NK_FEED_MODERATOR),
    NEWS_TICKET(178, "News Ticket", TeamProcessTaskEnum.NK_NEWS_MODERATOR),
    PAYMENT_TRANSACTION_TICKET(199, "PAYMENT_TRANSACTION_TICKET", Arrays.asList(TeamProcessTaskEnum.NON_INITIATED_PAYMENTS_TICKET,TeamProcessTaskEnum.NON_REINITIATED_PAYMENTS_TICKET,TeamProcessTaskEnum.WITHHELD_ESCALATION_PAYMENTS_TICKET,
            TeamProcessTaskEnum.WITHHELD_ESCALATION_PAYMENTS_TICKET_L2,TeamProcessTaskEnum.CREDIT_DEBIT_NOTE_WITHHELD_ESCALATION_TICKET));

    private Integer code;
    private String description;
    private List<TeamProcessTaskEnum> teamProcessTaskEnum;

    ProcessEnum(Integer code, String description, TeamProcessTaskEnum teamProcessTaskEnum) {
        this.code = code;
        this.description = description;
        this.teamProcessTaskEnum = Arrays.asList(teamProcessTaskEnum);
    }

    ProcessEnum(Integer code, String description, List<TeamProcessTaskEnum> teamProcessTaskEnum) {
        this.code = code;
        this.description = description;
        this.teamProcessTaskEnum = teamProcessTaskEnum;
    }

//    public static List<ProcessEnum> getProcessEnumsByIds(Set<Integer> processInstanceIds) {
//        Map<Integer, ProcessEnum> processEnumMap = Arrays.stream(ProcessEnum.values()).collect(Collectors.toMap(ProcessEnum::getCode, each -> each, (a, b) -> b));
//        return processInstanceIds.stream().filter(processEnumMap::containsKey).map(processEnumMap::get).collect(Collectors.toList());
//    }

    public static ProcessEnum getProcessEnumsById(int processId) {
        return Arrays.stream(ProcessEnum.values()).filter(each -> each.getCode() == processId).findFirst().orElse(null);
    }

    public static List<TeamProcessTaskEnum> getTeamProcessTaskEnumByProcessId(int processId) {
        ProcessEnum processEnum = Arrays.stream(ProcessEnum.values()).filter(each -> each.getCode() == processId).findFirst().orElse(null);
        if(processEnum == null){
            return null;
        }
        return processEnum.getTeamProcessTaskEnum();
    }

    public Integer getCode() {
        return code;
    }

    public List<TeamProcessTaskEnum> getTeamProcessTaskEnum() {
        return teamProcessTaskEnum;
    }
}
